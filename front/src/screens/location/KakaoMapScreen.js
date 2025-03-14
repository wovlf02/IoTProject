import React, { useEffect, useRef, useState } from "react";
import { View, TextInput, Button, StyleSheet, ActivityIndicator, Alert } from "react-native";
import { WebView } from "react-native-webview";
import Geolocation from "@react-native-community/geolocation";
import api from '../../api/api';

const KakaoMapScreen = () => {
    const webViewRef = useRef(null);
    const [latitude, setLatitude] = useState(null);
    const [longitude, setLongitude] = useState(null);
    const [destination, setDestination] = useState("");
    const [loading, setLoading] = useState(true);

    // 현재 위치 가져오기
    useEffect(() => {
        Geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;

                setLatitude(lat);
                setLongitude(lon);
                setLoading(false);

                // WebView에 현재 위치 전달
                setTimeout(() => {
                    if (webViewRef.current) {
                        webViewRef.current.postMessage(
                            JSON.stringify({ type: "CURRENT_LOCATION", latitude: lat, longitude: lon })
                        );
                    }
                }, 1000);
            },
            (error) => {
                console.log("위치 정보를 가져올 수 없음:", error);
                setLoading(false);
            },
            { enableHighAccuracy: true, timeout: 15000, maximumAge: 10000 }
        );
    }, []);

    // 목적지 검색 요청을 WebView로 보냄
    const fetchDestinations = async () => {
        if (!destination.trim()) {
            Alert.alert("🚨 목적지를 입력하세요!");
            return;
        }
        try {
            const response = await api.get(`/api/destinations/search`, { params: { name: destination } });

            const data = response.data; // Axios는 response.data로 데이터 접근
            console.log("📥 목적지 데이터:", data);

            if (data.length > 0) {
                const firstDestination = data[0];
                if (webViewRef.current) {
                    webViewRef.current.postMessage(
                        JSON.stringify({
                            type: "DESTINATION_SEARCH",
                            latitude: firstDestination.latitude,
                            longitude: firstDestination.longitude,
                        })
                    );
                }
            } else {
                Alert.alert("❌ 검색 결과 없음", "해당 목적지를 찾을 수 없습니다.");
            }
        } catch (error) {
            console.error("❌ API 요청 오류:", error); // 콘솔에 오류 로그 출력

            if (error.response) {
                Alert.alert("❌ 서버 오류", `상태 코드: ${error.response.status}\n메시지: ${error.response.data}`);
            } else if (error.request) {
                Alert.alert("❌ 네트워크 오류", "서버와 연결할 수 없습니다.");
            } else {
                Alert.alert("❌ 요청 오류", error.message);
            }
        }
    };

    const kakaoMapHtml = `<!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1" />
            <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=e84461afa8078822e18c5b6af6752df6&libraries=services"></script>
            <style>
                * { margin: 0; padding: 0; }
                html, body { width: 100%; height: 100%; overflow: hidden; }
                #map { width: 100%; height: 100%; }
                #info { position: absolute; top: 10px; left: 10px; background: white; padding: 5px; z-index: 100; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <div id="info">거리: -, 예상 소요 시간: -</div>
            <script>
                var map;
                var marker = new kakao.maps.Marker();
                var destMarker = new kakao.maps.Marker();
                var polyline = new kakao.maps.Polyline({
                    strokeWeight: 5,
                    strokeColor: "#FF0000",
                    strokeOpacity: 0.7,
                    strokeStyle: "solid"
                });
                var places = new kakao.maps.services.Places();
                var geocoder = new kakao.maps.services.Geocoder();

                function initMap() {
                    var mapContainer = document.getElementById('map');
                    var mapOption = { center: new kakao.maps.LatLng(37.5665, 126.9780), level: 3 };
                    map = new kakao.maps.Map(mapContainer, mapOption);
                    marker.setMap(map);
                    destMarker.setMap(map);
                }
                initMap();
                
             document.addEventListener("message", function(event) {
                var data = JSON.parse(event.data);
                
                if (data.type === "CURRENT_LOCATION") {
                    var currentPosition = new kakao.maps.LatLng(data.latitude, data.longitude);
                    marker.setPosition(currentPosition);
                    map.setCenter(currentPosition);
                }
            
                if (data.type === "DESTINATION_SEARCH") {
                    processRoute(data.latitude, data.longitude);
                }
            });

                function searchDestination(destination) {
                    places.keywordSearch(destination, function(result, status) {
                    console.log("📍 Kakao API 검색 결과:", result, "상태:", status); // 🔥 추가
                    if (status === kakao.maps.services.Status.OK && result.length > 0) {
                        processLocation(result[0].y, result[0].x);
                    } else {
                        geocoder.addressSearch(destination, function(result, status) {
                            console.log("📍 주소 검색 결과:", result, "상태:", status); // 🔥 추가
                            if (status === kakao.maps.services.Status.OK && result.length > 0) {
                                processLocation(result[0].y, result[0].x);
                            } else {
                                alert("목적지를 찾을 수 없습니다.");
                            }
                        });
                    }
                    });
                }

                function processRoute(destLat, destLon) {
                    var currentLat = marker.getPosition().getLat();
                    var currentLon = marker.getPosition().getLng();
                    
                    if (currentLat === destLat && currentLon === destLon) {
                        window.ReactNativeWebView.postMessage(JSON.stringify({
                            type: "ERROR",
                            message: "목적지와 현재 위치가 동일합니다."
                        }));
                        return;
                    }

                    var destPosition = new kakao.maps.LatLng(destLat, destLon);
                    destMarker.setPosition(destPosition);
                    destMarker.setMap(map);

                    polyline.setPath([marker.getPosition(), destPosition]);
                    polyline.setMap(map);

                    var linePath = new kakao.maps.Polyline({ path: [marker.getPosition(), destPosition] });
                    var distance = Math.round(linePath.getLength() / 1000);
                    var estimatedTime = Math.round((distance / 50) * 60);

                    document.getElementById('info').innerText = "거리: " + distance + " km, 예상 소요 시간: " + estimatedTime + " 분";
                    map.setCenter(destPosition);
                }
            </script>
        </body>
        </html>`;

    return (
        <View style={styles.container}>
            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    placeholder="목적지를 입력하세요"
                    value={destination}
                    onChangeText={setDestination}
                />
                <Button title="경로 찾기" onPress={fetchDestinations} />
            </View>
            {loading ? (
                <ActivityIndicator size="large" color="#0000ff" />
            ) : (
                <WebView
                    ref={webViewRef}
                    originWhitelist={["*"]}
                    source={{ html: kakaoMapHtml }}
                    javaScriptEnabled
                    domStorageEnabled
                    style={styles.webview}
                    onMessage={(event) => {
                        console.log("📩 WebView에서 받은 메시지:", event.nativeEvent.data); // 🔥 확인용 로그
                    }}
                />
            )}
        </View>
    );
};

const styles = StyleSheet.create({ container: { flex: 1 }, inputContainer: { flexDirection: "row", padding: 10 }, input: { flex: 1, borderWidth: 1, borderColor: "#ccc", padding: 8 }, webview: { flex: 1 } });

export default KakaoMapScreen;
