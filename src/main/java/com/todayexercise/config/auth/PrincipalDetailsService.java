package com.todayexercise.config.auth;

import com.todayexercise.excepton.customExceptionClass.UserNotFoundException;
import com.todayexercise.user.model.User;
import com.todayexercise.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        User user = userRepository.findByUserId(userId).orElseThrow(()
                -> { return new UserNotFoundException("user id:"+userId);});


        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(user);
    }
}
