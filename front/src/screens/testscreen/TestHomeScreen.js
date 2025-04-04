import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Image, Modal, TouchableOpacity } from 'react-native';

const TestHomeScreen = ({ navigation }) => {
    const [currentTime, setCurrentTime] = useState('');
    const [currentLocation, setCurrentLocation] = useState('서울, 대한민국');
    const [weather, setWeather] = useState('맑음');
    const [date, setDate] = useState('');
    const [isChatbotVisible, setIsChatbotVisible] = useState(false); // ✅ 챗봇 모달 상태 추가

    useEffect(() => {
        navigation.setOptions({
            tabBarIcon: ({ focused, size }) => {
                const iconPath = require('../../assets/home.png');
                return <Image source={iconPath} style={{ width: size, height: size }} />;
            },
            tabBarLabel: '홈 키',
            tabBarActiveTintColor: '#007AFF',
            tabBarInactiveTintColor: '#8E8E93',
        });

        const interval = setInterval(() => {
            const now = new Date();
            const timeFormatter = new Intl.DateTimeFormat('ko-KR', {
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit',
                timeZone: 'Asia/Seoul',
            });

            const dateFormatter = new Intl.DateTimeFormat('ko-KR', {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                timeZone: 'Asia/Seoul',
            });

            setCurrentTime(timeFormatter.format(now));
            setDate(dateFormatter.format(now));
        }, 1000);

        return () => clearInterval(interval);
    }, [navigation]);

    return (
        <View style={styles.container}>
            <View style={styles.mapContainer}>
                {/* 여기에 지도 컴포넌트 추가 가능 */}
            </View>

            <View style={styles.textContainer}>
                <View style={styles.row}>
                    <Text style={styles.text}>오늘 날짜: {date}</Text>
                    <Text style={styles.text}>현재 위치: {currentLocation}</Text>
                </View>
                <View style={styles.row}>
                    <Text style={styles.text}>현재 시각: {currentTime}</Text>
                    <View style={styles.weatherContainer}>
                        <Text style={styles.text}>날씨: {weather}</Text>
                    </View>
                    <Image source={require('../../assets/sun.png')} style={styles.logo} />
                </View>
            </View>

            {/* ✅ 챗봇 버튼 추가 */}
            <TouchableOpacity style={styles.chatbotButton} onPress={() => setIsChatbotVisible(true)}>
                <Image source={require('../../assets/chatbot.png')} style={styles.chatbotIcon} />
            </TouchableOpacity>

            {/* ✅ 챗봇 모달 */}
            <Modal
                animationType="slide"
                transparent={true}
                visible={isChatbotVisible}
                onRequestClose={() => setIsChatbotVisible(false)}
            >
                <View style={styles.modalBackground}>
                    <View style={styles.modalContainer}>
                        <Text style={styles.modalTitle}>챗봇</Text>
                        <Text style={styles.modalText}>무엇을 도와드릴까요?</Text>

                        {/* 닫기 버튼 */}
                        <TouchableOpacity style={styles.closeButton} onPress={() => setIsChatbotVisible(false)}>
                            <Text style={styles.closeButtonText}>닫기</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fffacd',
        alignItems: 'center',
        justifyContent: 'center',
    },
    mapContainer: {
        position: 'absolute',
        top: 100,
        left: 0,
        right: 0,
        bottom: 0,
        borderWidth: 2,
        borderColor: 'blue',
    },
    textContainer: {
        position: 'absolute',
        top: 10,
        left: 10,
        right: 10,
        backgroundColor: 'rgba(255, 255, 255, 0.7)',
        padding: 10,
        borderRadius: 10,
        zIndex: 1,
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginBottom: 6,
    },
    text: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#000000',
        marginBottom: 5,
    },
    logo: {
        width: 30,
        height: 30,
        marginRight: 15,
    },
    weatherContainer: {
        alignSelf: 'flex-start',
        marginLeft: 45,
    },

    // ✅ 챗봇 버튼 스타일
    chatbotButton: {
        position: 'absolute',
        bottom: 30, // 하단 여백
        right: 20, // 오른쪽 여백
        backgroundColor: 'skyblue',
        borderRadius: 50,
        padding: 10,
        elevation: 5,
    },
    chatbotIcon: {
        width: 50,
        height: 50,

    },

    // ✅ 모달 스타일
    modalBackground: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)', // 반투명 배경
    },
    modalContainer: {
        width: 300,
        backgroundColor: 'white',
        padding: 20,
        borderRadius: 10,
        alignItems: 'center',
    },
    modalTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 10,
    },
    modalText: {
        fontSize: 16,
        marginBottom: 20,
    },
    closeButton: {
        backgroundColor: '#007AFF',
        padding: 10,
        borderRadius: 5,
    },
    closeButtonText: {
        color: 'white',
        fontWeight: 'bold',
    },
});

export default TestHomeScreen;
