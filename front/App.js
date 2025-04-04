import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { useNavigation } from '@react-navigation/native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { TouchableOpacity, Image } from 'react-native';
import homeIcon from './src/assets/home.png';
import searchIcon from './src/assets/search.png';
import communityIcon from './src/assets/community.png';
import mypageIcon from './src/assets/mypage.png';
import backIcon from './src/assets/back.png'; // 뒤로가기 아이콘
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
const screenOptions = ({ route }) => {
    const navigation = useNavigation(); // 네비게이션 객체 가져오기

    let iconPath;
    switch (route.name) {
        case '내 현재 위치': // ✅ Tab.Screen의 name과 맞춰야 함
            iconPath = homeIcon;
            break;
        case '건물 찾기':
            iconPath = searchIcon;
            break;
        case '자유게시판':
            iconPath = communityIcon;
            break;
        case '마이페이지':
            iconPath = mypageIcon;
            break;
    }

    return {
        tabBarIcon: ({ focused, size }) => (
            <Image
                source={iconPath}
                style={{
                    width: size,
                    height: size,
                }}
            />
        ),
        tabBarActiveTintColor: '#007AFF',
        tabBarInactiveTintColor: '#A9A9A9',

        // ⬇️ 뒤로 가기 버튼 추가
        headerLeft: () => (
            <TouchableOpacity onPress={() => navigation.goBack()} style={{ marginLeft: 15 }}>
                <Image source={backIcon} style={{ width: 24, height: 24 }} />
            </TouchableOpacity>
        ),
    };
};

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
