import React from 'react';
import { View, Button, Linking, Alert } from 'react-native';

const KakaoNavigation = () => {
    const startX = 126.9780;  // 출발지 경도 (예: 서울 시청)
    const startY = 37.5665;   // 출발지 위도
    const endX = 127.0276;    // 도착지 경도 (예: 강남역)
    const endY = 37.4979;     // 도착지 위도
    const appKey = 'e84461afa8078822e18c5b6af6752df6';

    const openKakaoNavigation = () => {
        const url = `https://map.kakao.com/link/to/목적지,${endY},${endX}`;

        Linking.openURL(url).catch((err) => Alert.alert('오류', '카카오맵을 열 수 없습니다.'));
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Button title="카카오 길찾기" onPress={openKakaoNavigation} />
        </View>
    );
};

export default KakaoNavigation;