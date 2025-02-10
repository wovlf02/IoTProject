import React from 'react';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import ChatScreen from './ChatScreen';
import BoardScreen from './BoardScreen';
import FriendsScreen from './FriendsScreen';
import { View, StyleSheet } from 'react-native';

const Tab = createMaterialTopTabNavigator();

const CommunityMainScreen = () => {
    return (
        <View style={styles.container}>
            <Tab.Navigator
                screenOptions={{
                    tabBarActiveTintColor: '#007AFF',
                    tabBarInactiveTintColor: '#8E8E93',
                    tabBarIndicatorStyle: { backgroundColor: '#007AFF' },
                    tabBarLabelStyle: { fontSize: 14, fontWeight: 'bold' },
                }}
            >
                <Tab.Screen name="채팅" component={ChatScreen} />
                <Tab.Screen name="게시판" component={BoardScreen} />
                <Tab.Screen name="친구 관리" component={FriendsScreen} />
            </Tab.Navigator>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'white',
    },
});

export default CommunityMainScreen;
