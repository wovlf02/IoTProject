import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image } from 'react-native';

const IntroScreen = ({ navigation }) => {
    return (
        <View style={styles.container}>
            {/* 로고 및 앱 이름 */}
            <View style={styles.logoContainer}>
                <Image source={require('../assets/mianlogo.png')} style={styles.logo} />
                <Text style={styles.appName}>SmartCampus</Text>
            </View>

            {/* 간략한 설명 */}
            <Text style={styles.mainDescription}>IOT프로젝트 메인화면</Text>

            {/* 주요 기능 아이콘 */}
            <View style={styles.iconSection}>
                <View style={styles.iconCard}>
                    <Image source={require('../assets/locat.png')} style={styles.icon} />
                    <Text style={styles.iconLabel}>길안내</Text>
                </View>
                <View style={styles.iconCard}>
                    <Image source={require('../assets/personal.png')} style={styles.icon} />
                    <Text style={styles.iconLabel}>시간표</Text>
                </View>
                <View style={styles.iconCard}>
                    <Image source={require('../assets/group.png')} style={styles.icon} />
                    <Text style={styles.iconLabel}>챗봇</Text>
                </View>
            </View>


            {/* 버튼 섹션 */}
            <View style={styles.buttonContainer}>
                <TouchableOpacity
                    style={styles.loginButton}
                    onPress={() => navigation.navigate('Login')}
                >
                    <Text style={styles.buttonText}>로그인</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={styles.signUpButton}
                    onPress={() => navigation.navigate('Register')}
                >
                    <Text style={styles.buttonText}>회원가입</Text>
                </TouchableOpacity>
                {/*테스트용*/}
                <TouchableOpacity
                    style={styles.testButton}
                    onPress={() => navigation.navigate('Location')}
                >
                    <Text style={styles.buttonText}>테스트</Text>
                </TouchableOpacity>
                {/*테스트용*/}
            </View>
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
    logoContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    logo: {
        width: 80,
        height: 80,
    },
    appName: {
        fontSize: 32,
        fontWeight: 'bold',
        color: '#cd5c5c',
        marginTop: 10,
    },
    mainDescription: {
        fontSize: 22,
        fontWeight: 'bold',
        color: '#D51',
        textAlign: 'center',
        marginBottom: 30,
    },
    subDescription: {
        fontSize: 16,
        color: '#666',
        textAlign: 'center',
        marginBottom: 30,
    },
    iconSection: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '90%',
        marginBottom: 20,
    },
    iconCard: {
        alignItems: 'center',
    },
    icon: {
        width: 60,
        height: 60,
        marginBottom: 10,
    },
    iconLabel: {
        fontSize: 14,
        color: '#555',
    },
    buttonContainer: {
        flexDirection: 'row',
        width: '80%',
        justifyContent: 'space-between',
        marginTop: 30,
    },
    loginButton: {
        backgroundColor: '#66cdaa',
        paddingVertical: 15,
        flex: 1,
        borderRadius: 25,
        marginRight: 10,
    },
    signUpButton: {
        backgroundColor: '#00C853',
        paddingVertical: 15,
        flex: 1,
        borderRadius: 25,
    },

    testButton: {
        backgroundColor: '#007BFF',
        paddingVertical: 15,
        flex: 1,
        borderRadius: 25,
        marginLeft: 10
    },


    buttonText: {
        color: '#FFF',
        fontSize: 16,
        textAlign: 'center',
        fontWeight: 'bold',
    },
});

export default IntroScreen;
