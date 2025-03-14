import React, { useEffect, useState } from 'react';
import { View, ActivityIndicator, PermissionsAndroid, Platform } from 'react-native';
import WebView from 'react-native-webview';
import Geolocation from 'react-native-geolocation-service';
import api from '../../api/api'; // API 요청을 위한 axios 인스턴스

const KAKAO_MAP_KEY = "e84461afa8078822e18c5b6af6752df6";

const MapMainScreen = () => {
    const [location, setLocation] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const requestLocationPermission = async () => {
            if (Platform.OS === 'android') {
                const granted = await PermissionsAndroid.request(
                    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
                );
                return granted === PermissionsAndroid.RESULTS.GRANTED;
            }
            return true; // iOS는 자동 허용됨
        };

        const fetchUserLocation = async () => {
            try {
                console.log("🔵 위치 권한 요청 중...");
                const hasPermission = await requestLocationPermission();
                if (!hasPermission) {
                    console.warn("❌ 위치 권한이 거부됨");
                    setLoading(false);
                    return;
                }

                console.log("📍 위치 정보 가져오는 중...");
                Geolocation.getCurrentPosition(
                    async (position) => {
                        const { latitude, longitude } = position.coords;
                        console.log(`✅ 현재 위치: 위도(${latitude}), 경도(${longitude})`);

                        console.log("📡 백엔드 요청 보내는 중...");
                        const response = await api.post("/location/current", {
                            params: { latitude, longitude },
                        });

                        console.log("🟢 백엔드 응답:", response.data);
                        setLocation(response.data);
                        setLoading(false);
                    },
                    (error) => {
                        console.error("❌ Geolocation 오류:", error);
                        setLoading(false);
                    },
                    { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
                );
            } catch (error) {
                console.error("❌ 위치 데이터 가져오기 실패:", error);
                setLoading(false);
            }
        };


        fetchUserLocation();
    }, []);

    if (loading) {
        return <ActivityIndicator size="large" color="#0000ff" />;
    }

    if (!location) {
        return <View><Text>위치를 가져올 수 없습니다.</Text></View>;
    }

    const htmlContent = `
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_MAP_KEY}&libraries=services"></script>
        </head>
        <body>
            <div id="map" style="width:100%;height:100vh;"></div>
            <script>
                var mapContainer = document.getElementById('map');
                var mapOption = { 
                    center: new kakao.maps.LatLng(${location.latitude}, ${location.longitude}),
                    level: 3
                };
                var map = new kakao.maps.Map(mapContainer, mapOption);
                var marker = new kakao.maps.Marker({ position: map.getCenter() });
                marker.setMap(map);
            </script>
        </body>
        </html>
    `;

    return <WebView originWhitelist={['*']} source={{ html: htmlContent }} />;
};

export default MapMainScreen;
