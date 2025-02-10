import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';
import { Image } from 'react-native';

// 스크린 가져오기
import HomeScreen from './src/screens/home/HomeScreen';
import CommunityScreen from './src/screens/community/CommunityMainScreen';
import MyPageMainScreen from './src/screens/mypage/MyPageMainScreen';
import ProjectMainScreen from './src/screens/project/ProjectMainScreen';
import BoardScreen from './src/screens/community/BoardScreen';
import ChatScreen from './src/screens/community/ChatScreen';
import FriendsScreen from './src/screens/community/FriendsScreen';
import IntroScreen from './src/screens/IntroScreen';
import LoginScreen from './src/screens/auth/LoginScreen';
import RegisterScreen from './src/screens/auth/RegisterScreen';
import FindAccountScreen from './src/screens/auth/FindAccountScreen';
import ResetPasswordScreen from './src/screens/auth/ResetPasswordScreen';

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const App = () => (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="Intro" screenOptions={{ headerShown: false }}>
            <Stack.Screen name="Intro" component={IntroScreen} />
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
            <Stack.Screen name="FindAccount" component={FindAccountScreen} />
            <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />

            {/* 🔽 하단 탭 네비게이션을 직접 포함 */}
            <Stack.Screen name="Main" component={() => (
                <Tab.Navigator
                    screenOptions={({ route }) => ({
                        tabBarIcon: ({ focused, size }) => {
                            let iconPath;
                            switch (route.name) {
                                case 'Home':
                                    iconPath = require('./src/assets/home.png');
                                    break;
                                case 'Community':
                                    iconPath = require('./src/assets/community.png');
                                    break;
                                case 'Project':
                                    iconPath = require('./src/assets/project.png');
                                    break;
                                case 'My Page':
                                    iconPath = require('./src/assets/mypage.png');
                                    break;
                            }
                            return <Image source={iconPath} style={{ width: size, height: size, tintColor: focused ? '#007AFF' : '#8E8E93' }} />;
                        },
                        tabBarShowLabel: true,
                        tabBarActiveTintColor: '#007AFF',
                        tabBarInactiveTintColor: '#8E8E93',
                        headerShown: false, // 상단 네비게이션 숨김
                    })}
                >
                    <Tab.Screen name="Home" component={HomeScreen} />
                    <Tab.Screen name="Community" component={CommunityScreen} />
                    <Tab.Screen name="Project" component={ProjectMainScreen} />
                    <Tab.Screen name="My Page" component={MyPageMainScreen} />
                </Tab.Navigator>
            )} />
        </Stack.Navigator>
    </NavigationContainer>
);

export default App;
