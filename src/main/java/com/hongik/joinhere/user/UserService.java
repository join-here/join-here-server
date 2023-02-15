package com.hongik.joinhere.user;

import com.hongik.joinhere.exception.DuplicateMemberException;
import com.hongik.joinhere.user.dto.CreateUserRequest;
import com.hongik.joinhere.user.entity.User;
import com.hongik.joinhere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(CreateUserRequest request) {
        if (!userRepository.findByUsername(request.getUsername()).isEmpty())
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .birthday(request.getBirthday())
                .phone(request.getPhone())
                .build();

        userRepository.save(user);
    }


}
