import React from 'react';
import { View, FlatList, StyleSheet } from 'react-native';
import { Card, Text, Button } from 'react-native-paper';

const popularPosts = [
    { id: '1', title: 'React Native 꿀팁 모음', comments: 15 },
    { id: '2', title: '백엔드 API 설계 가이드', comments: 20 },
];

const latestPosts = [
    { id: '3', title: '스터디 그룹 모집합니다', comments: 5, time: '10분 전' },
    { id: '4', title: 'Spring Boot 질문있어요', comments: 8, time: '1시간 전' },
    { id: '5', title: 'React 초보자 질문 받습니다', comments: 2, time: '3시간 전' },
];

const BoardScreen = () => {
    return (
        <View style={styles.container}>
            <Text style={styles.sectionTitle}>🔥 인기 게시글</Text>
            <FlatList
                data={popularPosts}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <Card style={styles.card}>
                        <Card.Title title={item.title} subtitle={`💬 ${item.comments} 댓글`} />
                    </Card>
                )}
            />
            <Text style={styles.sectionTitle}>📢 최신 게시글</Text>
            <FlatList
                data={latestPosts}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <Card style={styles.card}>
                        <Card.Title title={item.title} subtitle={`💬 ${item.comments} 댓글 · ${item.time}`} />
                    </Card>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 10, backgroundColor: 'white' },
    sectionTitle: { fontSize: 18, fontWeight: 'bold', marginVertical: 10 },
    card: { marginBottom: 10 },
});

export default BoardScreen;
