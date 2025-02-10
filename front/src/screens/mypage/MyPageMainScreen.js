import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { Avatar, List, Button, Switch, Text } from 'react-native-paper';

const MyPageMainScreen = () => {
    const [darkMode, setDarkMode] = useState(false);

    return (
        <View style={styles.container}>
            {/* 🔵 프로필 섹션 */}
            <View style={styles.profileSection}>
                <Avatar.Image size={80} source={{ uri: 'https://via.placeholder.com/80' }} />
                <Text style={styles.userName}>사용자 닉네임</Text>
                <Text style={styles.userEmail}>user@email.com</Text>
                <Button mode="outlined" onPress={() => alert('프로필 편집')}>
                    프로필 편집
                </Button>
            </View>

            {/* ⚙️ 계정 관리 */}
            <List.Section>
                <List.Subheader>⚙️ 계정 관리</List.Subheader>
                <List.Item title="비밀번호 변경" left={() => <List.Icon icon="key" />} onPress={() => alert('비밀번호 변경')} />
                <List.Item title="전화번호 변경" left={() => <List.Icon icon="phone" />} onPress={() => alert('전화번호 변경')} />
                <List.Item title="이메일 변경" left={() => <List.Icon icon="email" />} onPress={() => alert('이메일 변경')} />
            </List.Section>

            {/* 🎨 개인화 설정 */}
            <List.Section>
                <List.Subheader>🎨 개인화 설정</List.Subheader>
                <List.Item
                    title="다크 모드"
                    left={() => <List.Icon icon="theme-light-dark" />}
                    right={() => <Switch value={darkMode} onValueChange={() => setDarkMode(!darkMode)} />}
                />
                <List.Item title="알림 설정" left={() => <List.Icon icon="bell" />} onPress={() => alert('알림 설정')} />
            </List.Section>

            {/* 🔒 보안 설정 */}
            <List.Section>
                <List.Subheader>🔒 보안 설정</List.Subheader>
                <List.Item title="로그인 관리" left={() => <List.Icon icon="desktop-mac" />} onPress={() => alert('로그인 관리')} />
                <List.Item title="2단계 인증 설정" left={() => <List.Icon icon="shield-lock" />} onPress={() => alert('2단계 인증')} />
                <List.Item title="개인정보 보호" left={() => <List.Icon icon="account-lock" />} onPress={() => alert('개인정보 보호')} />
            </List.Section>

            {/* 🚪 로그아웃 / 회원 탈퇴 */}
            <View style={styles.footer}>
                <Button mode="contained" onPress={() => alert('로그아웃')} style={styles.logoutButton}>
                    로그아웃
                </Button>
                <Button mode="text" color="red" onPress={() => alert('회원 탈퇴')}>
                    회원 탈퇴
                </Button>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 10,
        backgroundColor: 'white',
    },
    profileSection: {
        alignItems: 'center',
        marginBottom: 20,
    },
    userName: {
        fontSize: 20,
        fontWeight: 'bold',
        marginVertical: 5,
    },
    userEmail: {
        fontSize: 14,
        color: 'gray',
        marginBottom: 10,
    },
    footer: {
        marginTop: 20,
        alignItems: 'center',
    },
    logoutButton: {
        width: '100%',
        marginBottom: 10,
    },
});

export default MyPageMainScreen;
