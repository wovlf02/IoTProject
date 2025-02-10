import React from 'react';
import { View, FlatList, StyleSheet, TouchableOpacity, Text } from 'react-native';
import { Avatar, List } from 'react-native-paper';

const chatData = [
    { id: '1', name: '스터디 그룹', lastMessage: '오늘 미팅할까요?', unread: 2 },
    { id: '2', name: '프로젝트 팀', lastMessage: '코드 리뷰 부탁드립니다.', unread: 0 },
    { id: '3', name: '친구1', lastMessage: '오랜만이야!', unread: 1 },
];

const ChatScreen = () => {
    return (
        <View style={styles.container}>
            <FlatList
                data={chatData}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <TouchableOpacity>
                        <List.Item
                            title={item.name}
                            description={item.lastMessage}
                            left={() => <Avatar.Text size={40} label={item.name[0]} />}
                            right={() =>
                                item.unread > 0 ? (
                                    <View style={styles.unreadBadge}>
                                        <Text style={styles.unreadText}>{item.unread}</Text>
                                    </View>
                                ) : null
                            }
                        />
                    </TouchableOpacity>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: 'white' },
    unreadBadge: {
        backgroundColor: 'red',
        borderRadius: 10,
        width: 20,
        height: 20,
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 10,
    },
    unreadText: {
        color: 'white',
        fontSize: 12,
        fontWeight: 'bold',
    },
});

export default ChatScreen;
