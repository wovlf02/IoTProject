import React from 'react';
import { View, FlatList, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { Avatar, List, Button } from 'react-native-paper';

const friendsList = [
    { id: '1', name: '사용자1', status: '온라인' },
    { id: '2', name: '사용자2', status: '오프라인' },
];

const friendRequests = [
    { id: '3', name: '사용자3' },
    { id: '4', name: '사용자4' },
];

const FriendsScreen = () => {
    return (
        <View style={styles.container}>
            <Text style={styles.sectionTitle}>👥 친구 목록</Text>
            <FlatList
                data={friendsList}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <List.Item
                        title={item.name}
                        description={item.status}
                        left={() => <Avatar.Text size={40} label={item.name[0]} />}
                    />
                )}
            />
            <Text style={styles.sectionTitle}>📩 친구 요청</Text>
            <FlatList
                data={friendRequests}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <List.Item
                        title={item.name}
                        right={() => (
                            <View style={styles.btnGroup}>
                                <Button mode="contained">수락</Button>
                                <Button mode="outlined">거절</Button>
                            </View>
                        )}
                    />
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 10, backgroundColor: 'white' },
    sectionTitle: { fontSize: 18, fontWeight: 'bold', marginVertical: 10 },
    btnGroup: { flexDirection: 'row', gap: 10 },
});

export default FriendsScreen;
