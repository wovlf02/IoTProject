import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { Image } from 'react-native';

// 스크린 파일들 가져오기
import MapMainScreen from './src/screens/map/MapMainScreen';
import CommunityMainScreen from './src/screens/community/CommunityMainScreen';
import MyPageMainScreen from './src/screens/mypage/MyPageMainScreen';
import SearchMainScreen from './src/screens/search/SearchMainScreen';
import IntroScreen from './src/screens/IntroScreen';
import LoginScreen from './src/screens/auth/LoginScreen';
import RegisterScreen from './src/screens/auth/RegisterScreen';
import FindAccountScreen from "./src/screens/auth/FindAccountScreen";
import ResetPasswordScreen from "./src/screens/auth/ResetPasswordScreen";

// 네비게이터 생성
const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

// 하단 탭 네비게이션 아이콘 설정
const screenOptions = ({ route }) => ({
    tabBarIcon: ({ focused, size }) => {
        let iconPath;
        switch (route.name) {
            case '길찾기':
                iconPath = require('./src/assets/map.png');
                break;
            case '건물 검색':
                iconPath = require('./src/assets/search.png');
                break;
            case '커뮤니티':
                iconPath = require('./src/assets/community.png');
                break;
            case '마이페이지':
                iconPath = require('./src/assets/mypage.png');
                break;
        }
        return <Image source={iconPath} style={{ width: size, height: size, tintColor: focused ? '#007AFF' : '#8E8E93' }} />;
    },
    tabBarShowLabel: true,
    tabBarActiveTintColor: '#007AFF',
    tabBarInactiveTintColor: '#8E8E93',
});

// 탭 네비게이션 구성
const MainTabNavigator = ({ route }) => (
    <Tab.Navigator screenOptions={screenOptions}>
        <Tab.Screen
            name="길찾기"
            component={MapMainScreen}
            initialParams={route.params} // 로그인 후 전달받은 데이터
        />
        <Tab.Screen name="건물 검색" component={SearchMainScreen} />
        <Tab.Screen name="커뮤니티" component={CommunityMainScreen} />
        <Tab.Screen name="마이페이지" component={MyPageMainScreen} />
    </Tab.Navigator>
);

// 앱 네비게이션 설정
const App = () => (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="Intro" screenOptions={{ headerShown: false }}>
            <Stack.Screen name="Intro" component={IntroScreen} />
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
            <Stack.Screen name="FindAccount" component={FindAccountScreen} />
            <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />
            <Stack.Screen
                name="Main"
                component={MainTabNavigator}
                options={{ headerShown: false }}
            />
        </Stack.Navigator>
    </NavigationContainer>
);

export default App;
