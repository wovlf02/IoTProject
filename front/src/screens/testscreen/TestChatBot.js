import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, FlatList, StyleSheet } from 'react-native';

const TestChatBot = ({ onClose }) => {
    const [messages, setMessages] = useState([
        { id: '1', text: '안녕하세요! 무엇을 도와드릴까요?', sender: 'bot' },
    ]);
    const [inputText, setInputText] = useState('');

    // 간단한 챗봇 응답
    const getBotResponse = (userMessage) => {
        if (userMessage.includes('날씨')) return '현재 날씨는 맑음입니다. ☀️';
        if (userMessage.includes('시간')) return '현재 시간을 확인해 주세요. ⏰';
        return '죄송해요, 이해하지 못했어요. 😢';
    };

    // 메시지 전송 핸들러
    const sendMessage = () => {
        if (inputText.trim().length === 0) return;

        const newUserMessage = { id: Date.now().toString(), text: inputText, sender: 'user' };
        setMessages((prevMessages) => [...prevMessages, newUserMessage]);

        // 챗봇 응답 추가
        setTimeout(() => {
            const botMessage = { id: (Date.now() + 1).toString(), text: getBotResponse(inputText), sender: 'bot' };
            setMessages((prevMessages) => [...prevMessages, botMessage]);
        }, 1000);

        setInputText('');
    };

    return (
        <View style={styles.container}>
            {/* 채팅 메시지 리스트 */}
            <FlatList
                data={messages}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <View style={[styles.messageBubble, item.sender === 'user' ? styles.userBubble : styles.botBubble]}>
                        <Text style={styles.messageText}>{item.text}</Text>
                    </View>
                )}
                contentContainerStyle={styles.messageContainer}
            />

            {/* 입력창 & 버튼 */}
            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    placeholder="메시지를 입력하세요..."
                    value={inputText}
                    onChangeText={setInputText}
                />
                <TouchableOpacity style={styles.sendButton} onPress={sendMessage}>
                    <Text style={styles.sendButtonText}>전송</Text>
                </TouchableOpacity>
            </View>

            {/* 닫기 버튼 */}
            <TouchableOpacity style={styles.closeButton} onPress={onClose}>
                <Text style={styles.closeButtonText}>닫기</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f0f0f0',
        padding: 10,
    },
    messageContainer: {
        flexGrow: 1,
        justifyContent: 'flex-end',
    },
    messageBubble: {
        maxWidth: '70%',
        padding: 10,
        borderRadius: 10,
        marginBottom: 5,
    },
    userBubble: {
        alignSelf: 'flex-end',
        backgroundColor: '#007AFF',
    },
    botBubble: {
        alignSelf: 'flex-start',
        backgroundColor: '#e0e0e0',
    },
    messageText: {
        fontSize: 16,
        color: '#fff',
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        padding: 10,
        borderTopWidth: 1,
        borderColor: '#ccc',
        backgroundColor: '#fff',
    },
    input: {
        flex: 1,
        padding: 10,
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 5,
        marginRight: 10,
    },
    sendButton: {
        backgroundColor: '#007AFF',
        padding: 10,
        borderRadius: 5,
    },
    sendButtonText: {
        color: 'white',
        fontWeight: 'bold',
    },
    closeButton: {
        backgroundColor: 'red',
        padding: 10,
        marginTop: 10,
        borderRadius: 5,
        alignItems: 'center',
    },
    closeButtonText: {
        color: 'white',
        fontWeight: 'bold',
    },
});

export default TestChatBot;
