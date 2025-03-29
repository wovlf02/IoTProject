import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { Image } from 'react-native';


// 스크린들 임포트
import CommunityScreen from './src/screens/community/CommunityMainScreen';
import PersonalStudyMainScreen from './src/screens/personal/PersonalStudyMainScreen';
import GroupStudyMainScreen from './src/screens/group/GroupStudyMainScreen';
import MyPageMainScreen from './src/screens/mypage/MyPageMainScreen';
import IntroScreen from './src/screens/IntroScreen';
import LoginScreen from './src/screens/auth/LoginScreen';
import RegisterScreen from './src/screens/auth/RegisterScreen';
import FindAccountScreen from "./src/screens/auth/FindAccountScreen";
import ResetPasswordScreen from "./src/screens/auth/ResetPasswordScreen";
import KakaoMapScreen from "./src/screens/location/KakaoMapScreen";

import TestHomeScreen from "./src/screens/testscreen/TestHomeScreen";
import TestMyPageScreen from "./src/screens/testscreen/TestMyPageScreen";
import TestCommunityScreen from "./src/screens/testscreen/TestCommunityScreen";
import TestSearchScreen from "./src/screens/testscreen/TestSearchScreen";
import TestLoginScreen from "./src/screens/testscreen/TestLoginScreen";

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

// Tab Navigator의 화면 옵션
const screenOptions = ({ route }) => ({
    tabBarIcon: ({ focused, size }) => {
        let iconPath;
        switch (route.name) {
            case 'TestHomeScreen':
                iconPath = require('./src/assets/home.png');
                break;
            case 'TestSearchScreen':
                iconPath = require('./src/assets/search.png');
                break;
            case 'TestCommunityScreen':
                iconPath = require('./src/assets/community.png');
                break;
            case 'TestMyPageScreen':
                iconPath = require('./src/assets/mypage.png');
                break;
        }

        // focused 상태에 따라 아이콘 색상을 변경
        const iconColor = focused ? '#007AFF' : '#A9A9A9'; // 활성화 시 파란색, 비활성화 시 회색

        // 항상 아이콘은 보이고 색상만 변경
        return (
            <Image
                source={iconPath}
                style={{
                    width: size,
                    height: size,
                    // tintColor: iconColor // 색상만 변경
                }}
            />
        );
    },
    // 활성화된 탭과 비활성화된 탭의 색상 설정 (글씨 색상)
    tabBarActiveTintColor: '#007AFF',
    tabBarInactiveTintColor: '#A9A9A9', // 비활성화된 탭의 색상은 회색
});


// MainTabNavigator (탭 네비게이터)
const MainTabNavigator = () => (
    <Tab.Navigator screenOptions={screenOptions}>
        <Tab.Screen name="내 현재 위치" component={TestHomeScreen} />
        <Tab.Screen name="건물 찾기" component={TestSearchScreen} />
        <Tab.Screen name="자유게시판" component={TestCommunityScreen} />
        <Tab.Screen name="마이페이지" component={TestMyPageScreen} />
    </Tab.Navigator>
);

// 앱 컴포넌트 (Stack Navigator와 Tab Navigator 결합)
const App = () => (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="Intro" screenOptions={{ headerShown: false }}>
            <Stack.Screen name="Intro" component={IntroScreen} />
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
            <Stack.Screen name="FindAccount" component={FindAccountScreen} />
            <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />
            <Stack.Screen name="KakaoMap" component={KakaoMapScreen} />

            {/* 테스트 화면들 */}
            <Stack.Screen name="TestLoginScreen" component={TestLoginScreen} />
            <Stack.Screen name="TestHomeScreen" component={TestHomeScreen} />
            <Stack.Screen name="TestCommunityScreen" component={TestCommunityScreen} />
            <Stack.Screen name="TestMyPageScreen" component={TestMyPageScreen} />
            <Stack.Screen name="TestSearchScreen" component={TestSearchScreen} />

            {/* MainTabNavigator는 StackNavigator 내에 포함 */}
            <Stack.Screen
                name="Main"
                component={MainTabNavigator}
                options={{ headerShown: false }}
            />
        </Stack.Navigator>
    </NavigationContainer>
);

export default App;
