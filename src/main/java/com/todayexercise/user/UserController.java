package com.todayexercise.user;

import com.todayexercise.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //단순 테스트용
    @GetMapping("/users/auth-test")
    public String test(){
        return "인증 성공";
    }

    @PostMapping("/users/register")
    public String registerUser(@RequestBody User user){
        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "회원가입 완료";
    }

}
