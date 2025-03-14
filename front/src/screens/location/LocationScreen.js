import React, { useState, useEffect, useRef } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import Geolocation from '@react-native-community/geolocation';
import { WebView } from 'react-native-webview';

const LocationScreen = () => {
    const [latitude, setLatitude] = useState(37.5665); // 기본값: 서울
    const [longitude, setLongitude] = useState(126.9780); // 기본값: 서울
    const webViewRef = useRef(null);

    useEffect(() => {
        Geolocation.getCurrentPosition(
            position => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;

                setLatitude(lat);
                setLongitude(lon);

                // WebView가 로드된 후 좌표 전송 (0.5초 지연)
                setTimeout(() => {
                    if (webViewRef.current) {
                        webViewRef.current.postMessage(JSON.stringify({ latitude: lat, longitude: lon }));
                    }
                }, 500);
            },
            error => {
                console.log("위치 정보를 가져올 수 없음:", error);
            },
            { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
        );
    }, []);

    // Kakao Maps HTML 코드
    const kakaoMapHtml = `
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1" />
            <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=e84461afa8078822e18c5b6af6752df6"></script>
            <style>
                * { margin: 0; padding: 0; }
                #map { width: 100%; height: 100vh; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                var map;
                var marker;

                function initMap(lat, lon) {
                    var container = document.getElementById('map');
                    var options = {
                        center: new kakao.maps.LatLng(lat, lon),
                        level: 3
                    };
                    map = new kakao.maps.Map(container, options);

                    marker = new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(lat, lon),
                        map: map
                    });
                }

                // React Native에서 좌표를 받으면 지도 업데이트
                window.document.addEventListener("message", function(event) {
                    var location = JSON.parse(event.data);
                    var lat = location.latitude;
                    var lon = location.longitude;
                    
                    if (!map) {
                        initMap(lat, lon);
                    } else {
                        var moveLatLon = new kakao.maps.LatLng(lat, lon);
                        map.setCenter(moveLatLon);
                        marker.setPosition(moveLatLon);
                    }
                });

                // 기본 위치로 지도 초기화 (서울)
                initMap(37.5665, 126.9780);
            </script>
        </body>
        </html>
    `;

    return (
        <View style={styles.container}>
            <Text style={styles.text}>위도: {latitude}</Text>
            <Text style={styles.text}>경도: {longitude}</Text>

            <WebView
                ref={webViewRef}
                originWhitelist={['*']}
                source={{ html: kakaoMapHtml }}
                javaScriptEnabled
                domStorageEnabled
                style={styles.webview}
                onMessage={(event) => {
                    console.log("WebView에서 받은 메시지:", event.nativeEvent.data);
                }}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    text: {
        fontSize: 16,
        marginBottom: 10,
    },
    webview: {
        width: '100%',
        height: 500,
    },
});

export default LocationScreen;
