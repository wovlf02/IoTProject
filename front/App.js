import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { Image, TouchableOpacity, View } from 'react-native';

import MapMainScreen from './src/screens/map/MapMainScreen';
import BoardScreen from './src/screens/community/BoardScreen';
import MyPageMainScreen from './src/screens/mypage/MyPageMainScreen';
import SearchMainScreen from './src/screens/search/SearchMainScreen';
import IntroScreen from './src/screens/IntroScreen';
import LoginScreen from './src/screens/auth/LoginScreen';
import RegisterScreen from './src/screens/auth/RegisterScreen';
import FindAccountScreen from "./src/screens/auth/FindAccountScreen";
import ResetPasswordScreen from "./src/screens/auth/ResetPasswordScreen";
import ChatScreen from "./src/screens/community/ChatScreen";
import FriendsScreen from "./src/screens/community/FriendsScreen";
import UniversitySearchScreen from "./src/screens/auth/UniversitySearchScreen";

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();
const Drawer = createDrawerNavigator();

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
        return (
            <Image
                source={iconPath}
                style={{ width: size, height: size, resizeMode: 'contain' }}
            />
        );
    },
    tabBarShowLabel: true,
    tabBarActiveTintColor: '#007AFF',
    tabBarInactiveTintColor: '#C0C0C0',
});

/** 🧭 Drawer 내부의 게시판/채팅/친구관리 묶음 */
const DrawerNavigator = ({ navigation }) => (
    <Drawer.Navigator
        screenOptions={{
            drawerPosition: 'right',
            headerShown: false,
            headerTitle: '', // 상단 "커뮤니티" 텍스트 제거
            headerRight: () => (
                <TouchableOpacity onPress={() => navigation.openDrawer()} style={{ padding: 10 }}>
                    <Image
                        source={require('./src/assets/menu.png')}
                        style={{ width: 24, height: 24 }}
                    />
                </TouchableOpacity>
            )
        }}
    >
        <Drawer.Screen name="게시판" component={BoardScreen} />
        <Drawer.Screen name="채팅" component={ChatScreen} />
        <Drawer.Screen name="친구관리" component={FriendsScreen} />
    </Drawer.Navigator>
);

const MainTabNavigator = () => (
    <Tab.Navigator screenOptions={screenOptions}>
        <Tab.Screen name="길찾기" component={MapMainScreen} />
        <Tab.Screen name="건물 검색" component={SearchMainScreen} />
        <Tab.Screen name="커뮤니티" component={DrawerNavigator} options={{ headerShown: false }} />
        <Tab.Screen name="마이페이지" component={MyPageMainScreen} />
    </Tab.Navigator>
);

const App = () => (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="Intro" screenOptions={{ headerShown: false }}>
            <Stack.Screen name="Intro" component={IntroScreen} />
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
            <Stack.Screen name="FindAccount" component={FindAccountScreen} />
            <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />
            <Stack.Screen name="UniversitySearch" component={UniversitySearchScreen} />
            <Stack.Screen name="Main" component={MainTabNavigator} />
        </Stack.Navigator>
    </NavigationContainer>
);

export default App;
