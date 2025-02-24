import React, { useEffect, useState, useRef } from 'react';
import { View, StyleSheet, Text, ActivityIndicator, Alert, PermissionsAndroid, Platform } from 'react-native';
import { WebView } from 'react-native-webview';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Geolocation from 'react-native-geolocation-service';

// ✅ 위치 가져오기 & API 호출하는 커스텀 훅
const useFetchLocationAndData = () => {
    const [location, setLocation] = useState(null);

    useEffect(() => {
        const requestLocationPermission = async () => {
            if (Platform.OS === 'android') {
                const granted = await PermissionsAndroid.request(
                    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
                );
                return granted === PermissionsAndroid.RESULTS.GRANTED;
            }
            return true;
        };

        const fetchLocationAndData = async () => {
            const hasPermission = await requestLocationPermission();
            if (!hasPermission) {
                Alert.alert('위치 권한이 필요합니다.');
                return;
            }

            Geolocation.getCurrentPosition(
                async (position) => {
                    const { latitude, longitude } = position.coords;
                    setLocation({ latitude, longitude });

                    try {
                        const token = await AsyncStorage.getItem('authToken');
                        await axios.post(
                            'http://10.0.2.2:8080/api/location/current',
                            { latitude, longitude },
                            { headers: { Authorization: `Bearer ${token}` } }
                        );
                    } catch (error) {
                        console.error('❌ 위치 데이터 전송 실패:', error);
                    }
                },
                (error) => {
                    console.error('❌ 위치 정보 가져오기 실패:', error);
                },
                { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
            );
        };

        fetchLocationAndData();
    }, []);

    return location;
};

const LocationScreen = () => {
    const location = useFetchLocationAndData();
    const webViewRef = useRef(null);

    useEffect(() => {
        if (location && webViewRef.current) {
            webViewRef.current.postMessage(JSON.stringify(location));
        }
    }, [location]);

    const html = `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1" />
      <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey="></script>
      <style> * { margin: 0; padding: 0; } #map { width: 100%; height: 100vh; } </style>
    </head>
    <body>
      <div id="map"></div>
      <script>
        var map, marker;
        function initMap(lat, lng) {
          var mapContainer = document.getElementById('map');
          var mapOption = { center: new kakao.maps.LatLng(lat, lng), level: 3 };
          map = new kakao.maps.Map(mapContainer, mapOption);
          marker = new kakao.maps.Marker({ position: new kakao.maps.LatLng(lat, lng), map: map });
        }
        function updateLocation(lat, lng) {
          if (!map || !marker) return;
          var newCenter = new kakao.maps.LatLng(lat, lng);
          map.setCenter(newCenter);
          marker.setPosition(newCenter);
        }
        window.addEventListener('message', function(event) {
          var data = JSON.parse(event.data);
          if (data.latitude && data.longitude) {
            if (!map) initMap(data.latitude, data.longitude);
            else updateLocation(data.latitude, data.longitude);
          }
        });
      </script>
    </body>
    </html>
  `;

    return (
        <View style={styles.container}>
            {location ? (
                <WebView
                    ref={webViewRef}
                    originWhitelist={['*']}
                    source={{ html }}
                    javaScriptEnabled
                    domStorageEnabled
                    onLoadEnd={() => {
                        if (location) {
                            webViewRef.current?.postMessage(JSON.stringify(location));
                        }
                    }}
                />
            ) : (
                <View style={styles.loading}>
                    <Text>위치 정보를 불러오는 중...</Text>
                    <ActivityIndicator size="large" color="#0000ff" />
                </View>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1 },
    loading: { flex: 1, justifyContent: 'center', alignItems: 'center' },
});

export default LocationScreen;
