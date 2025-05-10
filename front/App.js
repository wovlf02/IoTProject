import React from 'react';
import {
    Image, TouchableOpacity, View
} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createDrawerNavigator } from '@react-navigation/drawer';

// 📄 스크린 임포트
import MapMainScreen from './src/screens/map/MapMainScreen';
import PostListScreen from './src/screens/community/PostListScreen';
import CreatePostScreen from './src/screens/community/CreatePostScreen';
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

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();
const Drawer = createDrawerNavigator();

/** 🔽 커뮤니티 탭 내부 Drawer */
const CommunityDrawer = () => (
    <Drawer.Navigator
        screenOptions={{
            drawerPosition: 'right',
            headerShown: false,
        }}
    >
        <Drawer.Screen name="게시판" component={PostListScreen} />
        <Drawer.Screen name="채팅" component={ChatScreen} />
        <Drawer.Screen name="친구관리" component={FriendsScreen} />
    </Drawer.Navigator>
);

/** 🔽 메인 탭 */
const MainTabNavigator = () => (
    <Tab.Navigator
        screenOptions={({ route }) => ({
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
        })}
    >
        <Tab.Screen name="길찾기" component={MapMainScreen} />
        <Tab.Screen name="건물 검색" component={SearchMainScreen} />
        <Tab.Screen name="커뮤니티" component={CommunityDrawer} options={{ headerShown: false }} />
        <Tab.Screen name="마이페이지" component={MyPageMainScreen} />
    </Tab.Navigator>
);

/** 🔽 앱 루트 */
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
            <Stack.Screen name="CreatePost" component={CreatePostScreen} />
        </Stack.Navigator>
    </NavigationContainer>
);

export default App;
