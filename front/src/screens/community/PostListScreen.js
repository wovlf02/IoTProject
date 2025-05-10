import React, { useState, useEffect } from 'react';
import {
    View, Text, StyleSheet, TextInput, FlatList, Image,
    TouchableOpacity, Pressable, Dimensions, ActivityIndicator, Alert
} from 'react-native';
import { useNavigation, useIsFocused } from '@react-navigation/native';
import EncryptedStorage from 'react-native-encrypted-storage';
import { jwtDecode } from 'jwt-decode';
import moment from 'moment';
import api from '../../api/api';

const { width } = Dimensions.get('window');

const PostListScreen = () => {
    const navigation = useNavigation();
    const isFocused = useIsFocused();

    const [searchQuery, setSearchQuery] = useState('');
    const [popupVisible, setPopupVisible] = useState(null);
    const [postsData, setPostsData] = useState([]);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const [searchMode, setSearchMode] = useState(false);
    const [writerId, setWriterId] = useState(null);

    useEffect(() => {
        const fetchUserId = async () => {
            try {
                const token = await EncryptedStorage.getItem('accessToken');
                if (token) {
                    const decoded = jwtDecode(token);
                    setWriterId(Number(decoded.sub));
                }
            } catch (err) {
                console.warn('토큰 디코딩 실패:', err);
                setWriterId(null);
            }
        };
        fetchUserId();
    }, [isFocused]);

    useEffect(() => {
        if (isFocused) {
            fetchPosts(0, true);
        }
    }, [isFocused]);

    const fetchPosts = async (pageToLoad = 0, reset = false) => {
        try {
            setLoading(true);
            const response = await api.get('/community/posts', {
                params: { page: pageToLoad, size: 20 },
            });

            const newPosts = Array.isArray(response.data.posts) ? response.data.posts : [];
            const currentPage = response.data.currentPage ?? 0;
            const totalPages = response.data.totalPages ?? 0;

            setPostsData(prev => reset ? newPosts : [...prev, ...newPosts]);
            setHasMore(currentPage + 1 < totalPages);
            setPage(currentPage);
            setSearchMode(false);
        } catch (err) {
            console.error('게시글 목록 불러오기 실패:', err);
            setPostsData([]);
            setHasMore(false);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async () => {
        if (!searchQuery.trim()) {
            fetchPosts(0, true);
            return;
        }

        try {
            setLoading(true);
            const response = await api.get('/community/posts/search', {
                params: { keyword: searchQuery }
            });

            const newPosts = Array.isArray(response.data.posts) ? response.data.posts : [];
            setPostsData(newPosts);
            setHasMore(false);
            setSearchMode(true);
        } catch (error) {
            console.error('검색 실패:', error);
            setPostsData([]);
            setHasMore(false);
        } finally {
            setLoading(false);
        }
    };

    const handleLoadMore = () => {
        if (!searchMode && hasMore && !loading) {
            fetchPosts(page + 1);
        }
    };

    const handleReport = (postId) => {
        console.log(`신고: ${postId}`);
        setPopupVisible(null);
    };

    const renderPost = ({ item }) => (
        <TouchableOpacity
            activeOpacity={1}
            style={styles.postCard}
            onPress={() => navigation.navigate('PostDetail', { postId: item.postId })}
        >
            <View style={styles.postHeader}>
                <Text style={styles.postTitle} numberOfLines={1}>{item.title}</Text>
                <TouchableOpacity onPress={() => setPopupVisible(popupVisible === item.postId ? null : item.postId)}>
                    <Image source={require('../../assets/more.png')} style={styles.moreIcon} />
                </TouchableOpacity>
            </View>

            <Text style={styles.postMeta}>
                {item.writerNickname} • {moment(item.createdAt).format('YYYY-MM-DD')}
            </Text>

            {item.content && (
                <Text style={styles.postContent} numberOfLines={2}>
                    {item.content.length > 50 ? item.content.slice(0, 50) + '...' : item.content}
                </Text>
            )}

            <View style={styles.infoRow}>
                <View style={styles.leftInfo}>
                    {item.imageCount > 0 && (
                        <Text style={styles.infoText}>📎 {item.imageCount}개</Text>
                    )}
                </View>
                <View style={styles.rightInfo}>
                    <Text style={styles.infoText}>❤️ {item.likeCount}</Text>
                    <Text style={styles.infoText}>💬 {item.commentCount}</Text>
                    {item.viewCount !== undefined && (
                        <Text style={styles.infoText}>👁️ {item.viewCount}</Text>
                    )}
                </View>
            </View>

            {popupVisible === item.postId && (
                <Pressable style={styles.popup} onPress={() => handleReport(item.postId)}>
                    <Text style={styles.popupText}>신고</Text>
                </Pressable>
            )}
        </TouchableOpacity>
    );

    return (
        <View style={styles.container}>
            <View style={styles.topHeader}>
                <Text style={styles.topHeaderTitle}>게시판</Text>
                <TouchableOpacity onPress={() => navigation.openDrawer()}>
                    <Image source={require('../../assets/menu.png')} style={styles.menuIcon} />
                </TouchableOpacity>
            </View>

            <View style={styles.searchBar}>
                <TextInput
                    placeholder="게시글 검색"
                    placeholderTextColor="#888"
                    value={searchQuery}
                    onChangeText={setSearchQuery}
                    style={styles.searchInput}
                />
                <TouchableOpacity onPress={handleSearch}>
                    <Image source={require('../../assets/board_search.png')} style={styles.searchButtonIcon} />
                </TouchableOpacity>
            </View>

            {loading && postsData.length === 0 ? (
                <ActivityIndicator size="large" color="#007BFF" style={{ marginTop: 20 }} />
            ) : (
                <FlatList
                    data={postsData || []}
                    keyExtractor={(item) => item.postId?.toString() ?? 'unknown'}
                    renderItem={renderPost}
                    onEndReached={handleLoadMore}
                    onEndReachedThreshold={0.5}
                    contentContainerStyle={styles.postList}
                    ListEmptyComponent={
                        <Text style={{ textAlign: 'center', marginTop: 30, color: '#888' }}>
                            검색 결과가 없습니다.
                        </Text>
                    }
                />
            )}

            <TouchableOpacity
                style={styles.floatingButton}
                onPress={() => {
                    if (!writerId) {
                        Alert.alert('오류', '로그인이 필요합니다.');
                        return;
                    }
                    navigation.navigate('CreatePost', { writerId });
                }}
            >
                <Image source={require('../../assets/add.png')} style={styles.addIcon} />
            </TouchableOpacity>
        </View>
    );
};

export default PostListScreen;

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#F8F9FA' },
    topHeader: {
        flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center',
        paddingHorizontal: 20, paddingVertical: 15, backgroundColor: '#FFF', elevation: 3,
    },
    topHeaderTitle: {
        fontSize: 20, fontWeight: 'bold', textAlign: 'center', flex: 1, marginLeft: 28,
    },
    menuIcon: { width: 24, height: 24 },
    searchBar: {
        flexDirection: 'row', backgroundColor: '#FFF', padding: 10, margin: 15,
        borderRadius: 10, alignItems: 'center', elevation: 3,
    },
    searchInput: { flex: 1, marginLeft: 8 },
    searchButtonIcon: { width: 20, height: 20, marginLeft: 8, tintColor: '#007BFF' },
    postList: { paddingBottom: 100 },
    postCard: {
        backgroundColor: '#FFF', padding: 15, marginHorizontal: 15, marginVertical: 10,
        borderRadius: 12, elevation: 3, position: 'relative'
    },
    postHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
    postTitle: { fontWeight: 'bold', fontSize: 16, flex: 1 },
    moreIcon: { width: 20, height: 20, marginLeft: 10 },
    postMeta: { fontSize: 12, color: '#666', marginTop: 2 },
    postContent: { marginTop: 8, fontSize: 14, color: '#333' },
    infoRow: {
        flexDirection: 'row', justifyContent: 'space-between', marginTop: 10, alignItems: 'center',
    },
    leftInfo: { flexDirection: 'row' },
    rightInfo: { flexDirection: 'row', gap: 12 },
    infoText: { fontSize: 12, color: '#555' },
    floatingButton: {
        position: 'absolute', right: 20, bottom: 20, backgroundColor: '#007BFF',
        width: 60, height: 60, borderRadius: 30, alignItems: 'center', justifyContent: 'center', elevation: 5,
    },
    addIcon: { width: 30, height: 30, tintColor: '#FFF' },
    popup: {
        position: 'absolute', top: 30, right: 8, backgroundColor: '#FFF', borderRadius: 6,
        paddingHorizontal: 12, paddingVertical: 8, elevation: 6, zIndex: 999
    },
    popupText: { color: '#007BFF', fontWeight: '500' },
});
