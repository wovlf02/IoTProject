import React from 'react';
import { View, ScrollView, StyleSheet, TouchableOpacity } from 'react-native';
import { Avatar, Card, Text, List, Button, Divider } from 'react-native-paper';

const projects = [
    { id: '1', title: 'AI 기반 일정 추천', progress: 0.7, deadline: '2025-03-15' },
    { id: '2', title: 'React Native 앱 개발', progress: 0.4, deadline: '2025-04-01' },
];

const recentActivities = [
    { id: '1', type: '게시판', text: '새로운 글이 등록되었습니다' },
    { id: '2', type: '채팅', text: '스터디 그룹에서 새 메시지 도착' },
    { id: '3', type: '프로젝트', text: '태스크가 업데이트됨' },
];

const todos = [
    { id: '1', task: '오늘 해야 할 작업 1' },
    { id: '2', task: '오늘 해야 할 작업 2' },
    { id: '3', task: '코드 리뷰 진행' },
];

const HomeScreen = () => {
    return (
        <ScrollView style={styles.container}>
            {/* 🔵 사용자 프로필 섹션 */}
            <View style={styles.profileSection}>
                <Avatar.Image size={60} source={{ uri: 'https://via.placeholder.com/80' }} />
                <View style={styles.userInfo}>
                    <Text style={styles.userName}>안녕하세요, 사용자 닉네임 님!</Text>
                    <Text style={styles.userDate}>📆 오늘 날짜: 2025-02-10</Text>
                </View>
                <TouchableOpacity>
                    <Avatar.Icon size={40} icon="bell" />
                </TouchableOpacity>
            </View>

            {/* 📂 진행 중인 프로젝트 */}
            <Text style={styles.sectionTitle}>📂 진행 중인 프로젝트</Text>
            {projects.map((project) => (
                <Card key={project.id} style={styles.card}>
                    <Card.Title title={project.title} subtitle={`📅 마감일: ${project.deadline}`} />
                    <Card.Content>
                        <Text>{Math.round(project.progress * 100)}% 진행 중</Text>
                    </Card.Content>
                </Card>
            ))}

            {/* 📝 최근 활동 기록 */}
            <Text style={styles.sectionTitle}>📝 최근 활동 기록</Text>
            {recentActivities.map((activity) => (
                <List.Item key={activity.id} title={activity.text} left={() => <List.Icon icon="information" />} />
            ))}

            {/* ✅ 할 일 목록 */}
            <Text style={styles.sectionTitle}>✅ 할 일 목록</Text>
            {todos.map((todo) => (
                <List.Item key={todo.id} title={todo.task} left={() => <List.Icon icon="checkbox-blank-outline" />} />
            ))}

            {/* ✨ 빠른 이동 버튼 */}
            <View style={styles.buttonGroup}>
                <Button mode="contained" icon="plus" style={styles.actionButton} onPress={() => alert('프로젝트 생성')}>
                    프로젝트 생성
                </Button>
                <Button mode="contained" icon="note-edit" style={styles.actionButton} onPress={() => alert('게시글 작성')}>
                    새 글 작성
                </Button>
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 10, backgroundColor: 'white' },
    profileSection: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: 15,
        backgroundColor: '#f8f9fa',
        borderRadius: 10,
        marginBottom: 10,
    },
    userInfo: { flex: 1, marginLeft: 10 },
    userName: { fontSize: 18, fontWeight: 'bold' },
    userDate: { fontSize: 14, color: 'gray' },
    sectionTitle: { fontSize: 18, fontWeight: 'bold', marginVertical: 10 },
    card: { marginBottom: 10, padding: 10 },
    buttonGroup: { flexDirection: 'row', justifyContent: 'space-between', marginTop: 20 },
    actionButton: { flex: 1, marginHorizontal: 5 },
});

export default HomeScreen;
