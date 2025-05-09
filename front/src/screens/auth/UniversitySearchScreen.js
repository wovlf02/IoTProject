import React, { useState } from 'react';
import {
    View,
    TextInput,
    Text,
    TouchableOpacity,
    StyleSheet,
    FlatList, Alert,
} from 'react-native';
import api from '../../api/api';

const UniversitySearchScreen = ({ route, navigation }) => {
    const [keyword, setKeyword] = useState('');
    const [results, setResults] = useState([]);
    const onSelect = route.params?.onSelect;

    const handleSearch = async () => {
        if (keyword.length < 2) {
            return Alert.alert('2글자 이상 입력하세요.');
        }

        try {
            const res = await api.get('/universities/search', {
                params: { keyword },
            });
            console.log(res.data);
            setResults(res.data);
        } catch (err) {
            console.error('검색 오류:', err);
            Alert.alert('학교 검색 중 오류가 발생했습니다.');
        }
    };

    const renderItem = ({ item }) => (
        <View style={styles.card}>
            <View style={{ flex: 1 }}>
                <Text style={styles.univName}>{item.name}</Text>
                <Text style={styles.univAddr}>{item.address}</Text>
            </View>
            <TouchableOpacity
                style={styles.selectBtn}
                onPress={() => {
                    navigation.navigate('Register', { selectedUniversity: item });
                }}
            >
                <Text style={styles.selectText}>선택</Text>
            </TouchableOpacity>
        </View>
    );

    return (
        <View style={styles.container}>
            {/* 검색 입력과 버튼 */}
            <View style={styles.searchRow}>
                <TextInput
                    placeholder="대학교 이름 검색"
                    value={keyword}
                    onChangeText={setKeyword}
                    style={styles.input}
                />
                <TouchableOpacity style={styles.searchBtn} onPress={handleSearch}>
                    <Text style={styles.searchText}>검색</Text>
                </TouchableOpacity>
            </View>

            {/* 검색 결과 리스트 */}
            <FlatList
                data={results}
                keyExtractor={(item) => item.id.toString()}
                renderItem={renderItem}
                contentContainerStyle={{ paddingBottom: 20 }}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 16, backgroundColor: '#f4f4f4' },
    searchRow: { flexDirection: 'row', marginBottom: 12 },
    input: {
        flex: 1,
        height: 48,
        borderColor: '#ccc',
        borderWidth: 1,
        paddingHorizontal: 12,
        borderRadius: 8,
        backgroundColor: '#fff',
    },
    searchBtn: {
        backgroundColor: '#007BFF',
        marginLeft: 10,
        paddingHorizontal: 16,
        justifyContent: 'center',
        borderRadius: 8,
    },
    searchText: { color: '#fff', fontWeight: 'bold' },
    card: {
        flexDirection: 'row',
        backgroundColor: '#fff',
        borderRadius: 10,
        padding: 16,
        marginBottom: 12,
        alignItems: 'center',
    },
    univName: { fontSize: 16, fontWeight: 'bold', marginBottom: 4 },
    univAddr: { fontSize: 14, color: '#666' },
    selectBtn: {
        backgroundColor: '#007BFF',
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 6,
    },
    selectText: { color: '#fff', fontWeight: 'bold' },
});

export default UniversitySearchScreen;
