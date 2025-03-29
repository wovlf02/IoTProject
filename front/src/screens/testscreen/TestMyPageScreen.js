import React, {useEffect} from 'react';
import {View, Text, StyleSheet, Image} from 'react-native';
import iconPath from "../../assets/home.png";


const TestMyPageScreen = ({ navigation }) => {
    useEffect(() => {
        // `navigation.setOptions`를 사용하여 탭 아이콘 설정
        navigation.setOptions({
            tabBarIcon: ({ focused, size }) => {
                const iconPath = require('../../assets/mypage.png'); // 원하는 아이콘 설정

                return <Image source={iconPath} style={{ width: size, height: size }} />;
            },
            tabBarLabel: '마이페이지', // 탭 라벨
            tabBarActiveTintColor: '#007AFF', // 활성화된 탭 색상
            tabBarInactiveTintColor: '#8E8E93', // 비활성화된 탭 색상
        });
    }, [navigation]);

    return (
        <View style={styles.container}>
            <Text style={styles.text}>메인 페이지입니다</Text>
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

    text: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#007BFF',
    },
    title: {
        fontSize: 28,
        fontWeight: 'bold',
        color: '#007BFF',
        marginBottom: 30,
    },
    input: {
        width: '80%',
        height: 50,
        backgroundColor: '#FFFFFF',
        borderRadius: 25,
        paddingHorizontal: 20,
        marginBottom: 15,
    },
    passwordContainer: {
        width: '80%',
        height: 50,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#FFFFFF',
        borderRadius: 25,
        paddingHorizontal: 20,
        marginBottom: 15,
    },
    passwordInput: {
        flex: 1,
    },
    eyeIcon: {
        width: 24,
        height: 24,
    },
    loginButton: {
        width: '80%',
        height: 50,
        backgroundColor: '#007BFF',
        borderRadius: 25,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 15,
    },
    loginButtonText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    socialLoginContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        marginTop: 30,
        marginBottom: 20,
    },
    socialIcon: {
        width: 50,
        height: 50,
        marginHorizontal: 10,
    },
    footer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '80%',
        marginTop: 20,
    },
    footerText: {
        fontSize: 14,
        color: '#007BFF',
        textDecorationLine: 'underline',
    },
});
export default TestMyPageScreen;
