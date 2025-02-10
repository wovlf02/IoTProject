import React from 'react';
import { View, FlatList, StyleSheet } from 'react-native';
import { Card, Text, ProgressBar, Button, Badge } from 'react-native-paper';

const projects = [
    {
        id: '1',
        title: 'AI 기반 일정 추천 시스템',
        progress: 0.75,
        deadline: '2025-03-15',
        members: 5,
        status: '진행 중',
    },
    {
        id: '2',
        title: 'React Native 앱 개발',
        progress: 0.5,
        deadline: '2025-04-01',
        members: 3,
        status: '대기 중',
    },
    {
        id: '3',
        title: 'Spring Boot 백엔드 개발',
        progress: 0.2,
        deadline: '2025-05-10',
        members: 4,
        status: '진행 중',
    },
    {
        id: '4',
        title: 'MySQL 데이터베이스 최적화',
        progress: 1,
        deadline: '2025-02-28',
        members: 2,
        status: '완료',
    },
];

const ProjectMainScreen = () => {
    return (
        <View style={styles.container}>
            <FlatList
                data={projects}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <Card style={styles.card}>
                        <Card.Title
                            title={item.title}
                            subtitle={`마감일: ${item.deadline}`}
                        />
                        <Card.Content>
                            <View style={styles.row}>
                                <Text style={styles.text}>팀원 수: {item.members}명</Text>
                                <Badge style={styles.statusBadge(item.status)}>{item.status}</Badge>
                            </View>
                            <ProgressBar progress={item.progress} color="#007bff" style={styles.progress} />
                            <Text style={styles.progressText}>{Math.round(item.progress * 100)}% 완료</Text>
                        </Card.Content>
                        <Card.Actions>
                            <Button mode="contained" onPress={() => alert(`${item.title} 상세 보기`)}>상세 보기</Button>
                        </Card.Actions>
                    </Card>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 10,
        backgroundColor: '#f8f9fa',
    },
    card: {
        marginBottom: 10,
        backgroundColor: 'white',
        borderRadius: 8,
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 5,
    },
    text: {
        fontSize: 14,
        color: '#333',
    },
    statusBadge: (status) => ({
        backgroundColor: status === '진행 중' ? '#28a745' : status === '완료' ? '#007bff' : '#ffc107',
        color: 'white',
        paddingHorizontal: 8,
        paddingVertical: 4,
        borderRadius: 8,
        fontSize: 12,
    }),
    progress: {
        height: 8,
        borderRadius: 4,
        marginTop: 5,
    },
    progressText: {
        marginTop: 5,
        fontWeight: 'bold',
        textAlign: 'right',
        fontSize: 12,
    },
});

export default ProjectMainScreen;
