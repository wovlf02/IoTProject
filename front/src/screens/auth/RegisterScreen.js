import React, { useState, useEffect } from 'react';
import {
    View, Text, StyleSheet, TextInput, TouchableOpacity, Alert,
    Dimensions, ScrollView, Image
} from 'react-native';
import { Picker } from '@react-native-picker/picker';
import { useNavigation, useRoute } from '@react-navigation/native';
import api from '../../api/api';
import {launchImageLibrary} from "react-native-image-picker";
import axios from "axios";

const { width, height } = Dimensions.get('window');

const RegisterScreen = () => {
    const [username, setUsername] = useState('');
    const [isUsernameValid, setIsUsernameValid] = useState(null);
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [isPasswordMatch, setIsPasswordMatch] = useState(null);
    const [nickname, setNickname] = useState('');
    const [profileImage, setProfileImage] = useState(null);
    const [selectedUniversity, setSelectedUniversity] = useState(null);
    const [email, setEmail] = useState('');
    const [emailDomain, setEmailDomain] = useState('');
    const [isCustomDomain, setIsCustomDomain] = useState(false);
    const [authInput, setAuthInput] = useState('');
    const [isAuthSent, setIsAuthSent] = useState(false);
    const [timeLeft, setTimeLeft] = useState(0);
    const [isFormValid, setIsFormValid] = useState(false);

    const navigation = useNavigation();
    const route = useRoute();

    const handleUserIdCheck = async () => {
        try {
            const response = await api.post('/auth/check-username', { username });
            const isAvailable = response.data.data; // data: true → 사용 가능, false → 중복

            if (isAvailable) {
                setIsUsernameValid(true);
                Alert.alert('사용 가능', '사용 가능한 아이디입니다.');
            } else {
                setIsUsernameValid(false);
                Alert.alert('사용 불가', '이미 사용 중인 아이디입니다.');
            }
        } catch (error) {
            Alert.alert('오류', '아이디 확인 중 문제가 발생했습니다.');
        }
    };


    const handlePasswordChange = (text) => {
        setPassword(text);
        setIsPasswordMatch(text === passwordConfirm);
    };

    const handlePasswordConfirmChange = (text) => {
        setPasswordConfirm(text);
        setIsPasswordMatch(password === text);
    };

    const handleSelectProfileImage = async () => {
        const result = await launchImageLibrary({ mediaType: 'photo' }, (res) => {
            if (!res.didCancel && !res.errorCode && res.assets?.length > 0) {
                setProfileImage(res.assets[0]);
            }
        });
    };

    const handleDomainChange = (value) => {
        if (value === 'custom') {
            setIsCustomDomain(true);
            setEmailDomain('');
        } else {
            setIsCustomDomain(false);
            setEmailDomain(value);
        }
    };

    const sendEmailVerificationCode = async () => {
        const fullEmail = `${email}@${emailDomain}`;
        try {
            const response = await api.post('/auth/send-code', {
                email: fullEmail,
                type: 'register' // ✅ 필수: 인증 요청 목적 명시
            });

            if (response.data.success) {
                setIsAuthSent(true);
                setTimeLeft(180);
                Alert.alert('인증번호 발송', response.data.message);
            } else {
                Alert.alert('실패', response.data.message);
            }
        } catch (error) {
            console.error('이메일 인증 오류:', error);
            Alert.alert('오류', '인증번호 발송 중 문제가 발생했습니다.');
        }
    };


    const checkEmailVerificationCode = async () => {
        const fullEmail = `${email}@${emailDomain}`;
        try {
            const response = await api.post('/auth/verify-code', {
                email: fullEmail,
                code: authInput,
            });

            if (response.data.success && response.data.data === true) {
                Alert.alert('인증 완료', '이메일 인증이 완료되었습니다.');
            } else {
                Alert.alert('인증 실패', '인증번호가 올바르지 않습니다.');
            }
        } catch {
            Alert.alert('오류', '인증번호 확인 중 문제가 발생했습니다.');
        }
    };


    useEffect(() => {
        if (route.params?.selectedUniversity) {
            setSelectedUniversity(route.params.selectedUniversity);
        }
    }, [route.params?.selectedUniversity]);

    useEffect(() => {
        if (timeLeft > 0) {
            const timer = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
            return () => clearTimeout(timer);
        }
    }, [timeLeft]);

    useEffect(() => {
        setIsFormValid(
            username &&
            isUsernameValid &&
            password &&
            passwordConfirm &&
            isPasswordMatch &&
            nickname &&
            selectedUniversity &&
            email &&
            emailDomain &&
            authInput
        );
    }, [username, isUsernameValid, password, passwordConfirm, isPasswordMatch, nickname, selectedUniversity, email, emailDomain, authInput]);


    const handleRegister = async () => {
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        formData.append('email', `${email}@${emailDomain}`);
        formData.append('nickname', nickname);
        formData.append('universityName', selectedUniversity.name);

        if (profileImage) {
            formData.append('profileImage', {
                uri: profileImage.uri,
                type: profileImage.type || 'image/jpeg',
                name: profileImage.fileName || 'profile.jpg',
            });
        }

        try {
            const response = await axios.post('http://192.168.0.2:8080/api/auth/register', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            const { success, message, data } = response.data;
            console.log('📦 회원가입 응답:', response.data);

            if (success) {
                Alert.alert('회원가입 성공', data || message, [
                    { text: '확인', onPress: () => navigation.navigate('Login') }
                ]);
            } else {
                Alert.alert('회원가입 실패', message || '알 수 없는 오류가 발생했습니다.');
            }
        } catch (error) {
            console.error('❌ 회원가입 중 오류:', error);
            Alert.alert('오류', '회원가입 중 문제가 발생했습니다.');
        }
    };




    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>회원가입</Text>

            <View style={styles.universityRow}>
                <Text style={styles.selectedUniversityText}>
                    {selectedUniversity?.name || '대학교를 선택해주세요'}
                </Text>
                <TouchableOpacity
                    style={styles.searchButton}
                    onPress={() => navigation.navigate('UniversitySearch', {
                        onSelect: (univ) => {
                            setSelectedUniversity(univ);
                            navigation.goBack();
                        }
                    })}
                >
                    <Text style={styles.buttonText}>검색</Text>
                </TouchableOpacity>
            </View>

            {/* 아이디 + 중복확인 */}
            <View style={styles.inputWithButton}>
                <TextInput
                    style={styles.flexInput}
                    placeholder="아이디"
                    value={username}
                    onChangeText={setUsername}
                />
                <TouchableOpacity style={styles.inlineButton} onPress={handleUserIdCheck}>
                    <Text style={styles.buttonText}>중복확인</Text>
                </TouchableOpacity>
            </View>


            {/* 비밀번호 */}
            <TextInput
                style={styles.input}
                placeholder="비밀번호"
                secureTextEntry
                value={password}
                onChangeText={handlePasswordChange}
            />
            <TextInput
                style={[styles.input, isPasswordMatch === false && styles.inputError]}
                placeholder="비밀번호 확인"
                secureTextEntry
                value={passwordConfirm}
                onChangeText={handlePasswordConfirmChange}
            />

            {/* 닉네임 */}
            <TextInput
                style={styles.input}
                placeholder="닉네임"
                value={nickname}
                onChangeText={setNickname}
            />

            {/* 프로필 이미지 선택 영역 */}
            <View style={styles.profileContainer}>
                {profileImage ? (
                    <Image source={{ uri: profileImage.uri }} style={styles.profilePreview} />
                ) : (
                    <Text style={styles.profilePlaceholder}>선택된 이미지 없음</Text>
                )}
                <TouchableOpacity style={styles.profileButton} onPress={handleSelectProfileImage}>
                    <Text style={styles.buttonText}>프로필 선택</Text>
                </TouchableOpacity>
            </View>



            {/* 이메일 입력 */}
            <View style={styles.emailContainer}>
                <TextInput
                    style={styles.emailInput}
                    placeholder="이메일"
                    value={email}
                    onChangeText={setEmail}
                />
                <Text style={styles.atSymbol}>@</Text>
                {isCustomDomain ? (
                    <TextInput
                        style={styles.emailInput}
                        placeholder="직접 입력"
                        value={emailDomain}
                        onChangeText={setEmailDomain}
                    />
                ) : (
                    <View style={styles.pickerContainer}>
                        <Picker
                            selectedValue={emailDomain}
                            onValueChange={handleDomainChange}
                            style={styles.picker}
                        >
                            <Picker.Item label="도메인 선택" value="" />
                            <Picker.Item label="gmail.com" value="gmail.com" />
                            <Picker.Item label="naver.com" value="naver.com" />
                            <Picker.Item label="daum.net" value="daum.net" />
                            <Picker.Item label="직접 입력" value="custom" />
                        </Picker>
                    </View>
                )}
            </View>

            {/* 인증번호 전송 */}
            <TouchableOpacity style={styles.authButton} onPress={sendEmailVerificationCode}>
                <Text style={styles.buttonText}>인증번호 발송</Text>
            </TouchableOpacity>

            {/* 인증번호 입력 */}
            {isAuthSent && (
                <View style={styles.inputGroupRow}>
                    <TextInput
                        style={styles.inputSmall}
                        placeholder="인증번호"
                        value={authInput}
                        onChangeText={setAuthInput}
                        keyboardType="number-pad"
                    />
                    <Text style={styles.timerInline}>
                        {Math.floor(timeLeft / 60)}:{String(timeLeft % 60).padStart(2, '0')}
                    </Text>
                    <TouchableOpacity style={styles.checkButtonInline} onPress={checkEmailVerificationCode}>
                        <Text style={styles.buttonText}>확인</Text>
                    </TouchableOpacity>
                </View>
            )}

            {/* 회원가입 버튼 */}
            <TouchableOpacity
                style={[styles.submitButton, isFormValid ? styles.activeButton : styles.inactiveButton]}
                disabled={!isFormValid}
                onPress={handleRegister}
            >
                <Text style={styles.buttonText}>회원가입</Text>
            </TouchableOpacity>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        backgroundColor: '#E3F2FD',
        alignItems: 'center',
        padding: width * 0.05,
    },
    title: {
        fontSize: height * 0.035,
        fontWeight: 'bold',
        color: '#007BFF',
        marginBottom: height * 0.03,
    },
    input: {
        width: '100%',
        height: height * 0.06,
        backgroundColor: '#FFFFFF',
        borderRadius: height * 0.015,
        paddingHorizontal: width * 0.04,
        marginBottom: height * 0.02,
    },
    inputError: {
        borderColor: 'red',
        borderWidth: 2,
    },
    inputSuccess: {
        borderColor: 'green',
        borderWidth: 2,
    },
    inputGroup: {
        width: '100%',
        marginBottom: height * 0.03,
    },
    emailContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: height * 0.02,
        width: '100%',
    },
    idValidationIcon: {
        position: 'absolute',
        right: 118,
        top: 16,
        width: 24,
        height: 24,
    },
    passwordValidationIcon: {
        position: 'absolute',
        right: 17,
        top: 16,
        width: 24,
        height: 24,
    },
    emailInput: {
        flex: 3,
        height: height * 0.06,
        backgroundColor: '#FFFFFF',
        borderRadius: height * 0.015,
        paddingHorizontal: width * 0.04,
        marginRight: width * 0.02,
    },
    atSymbol: {
        fontSize: height * 0.03,
        marginHorizontal: width * 0.02,
    },
    pickerContainer: {
        flex: 4,
        height: height * 0.06,
        backgroundColor: '#FFFFFF',
        borderRadius: height * 0.015,
        justifyContent: 'center',
    },
    picker: {
        width: '100%',
        height: '100%',
    },
    authButton: {
        width: '100%',
        height: height * 0.06,
        backgroundColor: '#007BFF',
        borderRadius: height * 0.015,
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: height * 0.03,
    },
    buttonText: {
        color: '#FFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    timerInline: {
        position: 'absolute',
        right: width * 0.28, // 타이머가 입력 칸 바로 오른쪽에 위치
        top: height * 0.015, // 입력 칸 높이에 맞게 위치 조정
        fontSize: height * 0.02,
        color: '#FF0000', // 빨간색 타이머
    },
    checkButton: {
        marginTop: height * 0.02,
        backgroundColor: '#007BFF',
        paddingVertical: height * 0.015,
        paddingHorizontal: width * 0.05,
        borderRadius: height * 0.015,
        alignSelf: 'center',
    },
    submitButton: {
        width: '100%',
        height: height * 0.06,
        borderRadius: height * 0.015,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: height * 0.03,
    },
    activeButton: {
        backgroundColor: '#007BFF',
    },
    inactiveButton: {
        backgroundColor: '#CCC',
    },
    // 검증 아이콘의 기본 스타일
    validationIconId: {
        position: 'absolute', // 아이콘을 입력 칸 오른쪽에 고정
        right: width * 0.28, // 입력 칸 오른쪽 여백
        top: height * 0.013, // 입력 칸 위쪽 여백
        fontSize: height * 0.02, // 아이콘 크기
    },
    validationIconPassword: {
        position: 'absolute', // 아이콘을 입력 칸 오른쪽에 고정
        right: width * 0.03, // 입력 칸 오른쪽 여백
        top: height * 0.013, // 입력 칸 위쪽 여백
        fontSize: height * 0.02, // 아이콘 크기
    },
    validationIconEmailVerificationCode: {
        position: 'absolute', // 아이콘을 입력 칸 오른쪽에 고정
        right: width * 0.28, // 입력 칸 오른쪽 여백
        top: height * 0.013, // 입력 칸 위쪽 여백
        fontSize: height * 0.02, // 아이콘 크기
    },
    // 초록색 체크 아이콘 스타일
    iconSuccess: {
        color: 'green', // 초록색
    },
    // 빨간색 X 아이콘 스타일
    iconError: {
        color: 'red', // 빨간색
    },
    inputGroupRow: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: height * 0.03,
        width: '100%',
    },

    inputSmall: {
        flex: 3,
        height: height * 0.06,
        backgroundColor: '#FFFFFF',
        borderRadius: height * 0.015,
        paddingHorizontal: width * 0.04,
        marginRight: width * 0.02,
    },

    checkButtonInline: {
        flex: 1,
        height: height * 0.06,
        backgroundColor: '#007BFF',
        borderRadius: height * 0.015,
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: width * 0.02,
    },
    universityRow: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        width: '100%',
        marginBottom: 16,
        backgroundColor: '#fff',
        borderRadius: 10,
        paddingHorizontal: 12,
        height: 50,
    },
    searchButton: {
        backgroundColor: '#007BFF',
        paddingHorizontal: 14,
        paddingVertical: 8,
        borderRadius: 6,
        marginLeft: 10,
    },
    inputWithButton: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#FFFFFF',
        borderRadius: height * 0.015,
        height: height * 0.06,
        width: '100%',
        marginBottom: height * 0.02,
        paddingHorizontal: width * 0.03,
    },
    flexInput: {
        flex: 1,
        fontSize: 14,
        color: '#000',
    },
    inlineButton: {
        backgroundColor: '#007BFF',
        paddingHorizontal: 10,
        paddingVertical: 6,
        borderRadius: 6,
        marginLeft: 10,
        alignItems: 'center',
        justifyContent: 'center',
        height: height * 0.045, // 작게 조정
        minWidth: 70,
    },
    selectedUniversityText: {
        flex: 1,
        fontSize: 14,
        color: '#333',
        paddingLeft: width * 0.01,
    },
    profileContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        width: '100%',
        marginBottom: height * 0.02,
    },
    profilePlaceholder: {
        fontSize: 14,
        color: '#888',
        flex: 1,
    },
    profileButton: {
        backgroundColor: '#007BFF',
        paddingHorizontal: 12,
        paddingVertical: 8,
        borderRadius: 6,
        marginLeft: 10,
        alignItems: 'center',
        justifyContent: 'center',
    },
    profilePreview: {
        width: 60,
        height: 60,
        borderRadius: 30,
        backgroundColor: '#EEE',
    },

});

export default RegisterScreen;