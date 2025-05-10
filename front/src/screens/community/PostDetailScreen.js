import React, { useEffect, useState } from 'react';
import {
    View, Text, StyleSheet, ScrollView, Image,
    TouchableOpacity, TextInput, Alert, FlatList, Pressable
} from 'react-native';
import moment from 'moment';
import api from '../../api/api';
import EncryptedStorage from 'react-native-encrypted-storage';
import { jwtDecode } from 'jwt-decode';
import { useRoute } from '@react-navigation/native';

const BASE_URL = 'http://192.168.0.2:8080'; // 실제 서버 주소로 교체

const PostDetailScreen = () => {
    const { postId } = useRoute().params;
    const [post, setPost] = useState(null);
    const [writerProfileImageUrl, setWriterProfileImageUrl] = useState('');
    const [popupVisible, setPopupVisible] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchPost();
    }, []);

    const fetchPost = async () => {
        try {
            const token = await EncryptedStorage.getItem('accessToken');
            const decoded = jwtDecode(token);

            const res = await api.get(`/community/posts/${postId}`);
            setPost(res.data);

            // 프로필 이미지 URL 처리
            const userRes = await api.get(`/users/${res.data.writerId}`);
            const profilePath = userRes.data.profileImageUrl?.startsWith('/')
                ? userRes.data.profileImageUrl
                : '/profile/' + userRes.data.profileImageUrl;

            const imageUrl = `${BASE_URL}${profilePath}`;
            setWriterProfileImageUrl(imageUrl);

        } catch (err) {
            Alert.alert('오류', '게시글 정보 로딩 실패');
        } finally {
            setLoading(false);
        }
    };




    const handleLikePost = async () => {
        try {
            await api.post(`/community/posts/${postId}/like`);
            fetchPost();
        } catch (err) {
            Alert.alert('오류', '좋아요 실패');
        }
    };

    const handleReport = () => {
        Alert.alert('신고 완료', '신고가 접수되었습니다.');
        setPopupVisible(false);
    };

    if (loading || !post) return <Text style={{ padding: 20 }}>로딩 중...</Text>;

    return (
        <ScrollView style={styles.container}>
            <View style={styles.headerRow}>
                <Text style={styles.title}>{post.title}</Text>
                <View style={{ alignItems: 'flex-end' }}>
                    <TouchableOpacity onPress={() => setPopupVisible(!popupVisible)}>
                        <Image source={require('../../assets/more.png')} style={styles.moreIcon} />
                    </TouchableOpacity>

                    {popupVisible && (
                        <Pressable style={styles.popupBox} onPress={handleReport}>
                            <Text style={styles.popupText}>신고</Text>
                        </Pressable>
                    )}
                </View>
            </View>

            <View style={styles.metaRow}>
                <Image
                    source={{ uri: writerProfileImageUrl }}
                    style={styles.profileImage}
                />
                <View style={styles.nicknameTimeWrapper}>
                    <Text style={styles.metaText}>{post.writerNickname}</Text>
                    <Text style={styles.timeText}>{moment(post.createdAt).format('YYYY-MM-DD HH:mm')}</Text>
                </View>
            </View>

            <Text style={styles.content}>{post.content}</Text>

            {post.attachmentUrls && post.attachmentUrls.length > 0 && (
                <FlatList
                    data={post.attachmentUrls}
                    keyExtractor={(item, idx) => idx.toString()}
                    horizontal
                    renderItem={({ item }) => {
                        const cleaned = item.replace(/^null\//, 'community/');
                        const uri = `${BASE_URL}/${cleaned}`;
                        return (
                            <Image
                                source={{ uri }}
                                style={styles.image}
                            />
                        );
                    }}
                    style={{ marginTop: 10 }}
                    showsHorizontalScrollIndicator={false}
                />
            )}


            <View style={styles.infoRow}>
                <TouchableOpacity onPress={handleLikePost}>
                    <Text style={[styles.infoText, post.liked && styles.liked]}>
                        ❤️ {post.likeCount}
                    </Text>
                </TouchableOpacity>
                <Text style={styles.infoText}>👁️ {post.viewCount}</Text>
                <Text style={styles.infoText}>💬 {post.commentCount}</Text>
            </View>

            <View style={styles.commentInputBox}>
                <TextInput
                    placeholder="댓글을 입력하세요"
                    style={styles.input}
                    placeholderTextColor="#aaa"
                />
                <TouchableOpacity>
                    <Text style={styles.sendButton}>등록</Text>
                </TouchableOpacity>
            </View>
        </ScrollView>
    );
};

export default PostDetailScreen;
const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#fff', padding: 16 },
    headerRow: {
        flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-start'
    },
    title: { fontSize: 20, fontWeight: 'bold', flex: 1, marginRight: 8 },
    moreIcon: { width: 20, height: 20, tintColor: '#888' },
    popupBox: {
        position: 'absolute', top: 30, right: 0,
        backgroundColor: '#FFF', borderRadius: 8,
        paddingHorizontal: 12, paddingVertical: 6,
        elevation: 5, zIndex: 10,
    },
    popupText: { color: '#d00', fontWeight: '500' },

    metaRow: {
        flexDirection: 'row', alignItems: 'center', marginTop: 16,
    },
    profileImage: {
        width: 36, height: 36, borderRadius: 18, backgroundColor: '#ddd'
    },
    nicknameTimeWrapper: {
        flex: 1, marginLeft: 10, flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center'
    },
    metaText: { fontSize: 15, color: '#222', fontWeight: '500' },
    timeText: { fontSize: 13, color: '#777' },

    content: { marginTop: 16, fontSize: 15, color: '#222', lineHeight: 22 },

    image: {
        width: 160, height: 160, marginRight: 10,
        marginTop: 16, borderRadius: 8, backgroundColor: '#eee'
    },

    infoRow: {
        flexDirection: 'row', gap: 16,
        marginTop: 20, alignItems: 'center',
    },
    infoText: { fontSize: 14, color: '#555' },
    liked: { fontWeight: 'bold', color: 'red' },

    commentInputBox: {
        flexDirection: 'row', alignItems: 'center',
        marginTop: 30, marginBottom: 40,
    },
    input: {
        flex: 1, borderWidth: 1, borderColor: '#ccc', borderRadius: 10,
        padding: 12, marginRight: 10
    },
    sendButton: {
        backgroundColor: '#007BFF', color: '#fff',
        paddingVertical: 10, paddingHorizontal: 16, borderRadius: 8
    }
});
