package com.hongik.joinhere.user;

import com.hongik.joinhere.user.entity.User;
import com.hongik.joinhere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if (users.isEmpty())
            throw new UsernameNotFoundException(username + " 데이터베이스에서 찾을 수 없습니다.");
        return new CustomUserDetails(users.get(0));
    }
}
