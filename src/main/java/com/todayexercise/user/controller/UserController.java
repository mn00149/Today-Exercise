package com.todayexercise.user.controller;

import com.todayexercise.category.Category;
import com.todayexercise.dto.CMRespDTO;
import com.todayexercise.user.dto.CategoryDTO;
import com.todayexercise.user.repository.UserRepository;
import com.todayexercise.user.service.UserService;
import com.todayexercise.user.dto.RegisterDTO;
import com.todayexercise.user.dto.UpdateDTO;
import com.todayexercise.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //단순 테스트용
    @GetMapping("/users/auth-test")
    public String test(){
        return "인증 성공";
    }

    //회원가입 api
    @PostMapping("/users/register")
    public CMRespDTO<?> registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        User user = registerDTO.toEntity();
        userService.registerUser(user);
        user.setPassword(null);
        return new CMRespDTO<>(1,"회원가입 성공", user);
    }

    //유저 아이디 중복 체크
    @GetMapping("/users/dupulicate-check/id/{userId}")
    public CMRespDTO<?> idDupulicateCheck(@PathVariable String userId){
        boolean isUserExist = userService.userIdDupliCheck(userId);
        if(isUserExist) return new CMRespDTO<>(1,"가입 가능한 아이디입니다", userId);
        return new CMRespDTO<>(2,"이미 존제하는 아이디 입니다", userId);
    }

    //유저 email 중복 체크
    @GetMapping("/users/dupulicate-check/email/{email}")
    public CMRespDTO<?> emailDupulicateCheck(@PathVariable String email){
        boolean isEmailExist = userService.emailDupliCheck(email);
        if(isEmailExist) return new CMRespDTO<>(1,"가입 가능한 email입니다", email);
        return new CMRespDTO<>(2,"이미 존제하는 email 입니다", email);
    }

    //유저정보 수정
    @PutMapping("/users/{userId}")
    public CMRespDTO<?> updateUser(@RequestBody UpdateDTO updateDTO, HttpServletRequest request ){
        User updatedUser = userService.updateUser(request, updateDTO);
        return null;
    }



    //회원 관심 카테고리추가
    @PostMapping("/users/category")
    public CMRespDTO<?> registerCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request){
        ArrayList<Long> categories = categoryDTO.getCategories();
        userService.registerCategories(request, categories);
        return new CMRespDTO<>(1,"성공", null);
    }

//    @GetMapping("/users/category")
//    public CMRespDTO<?> getCategory(HttpServletRequest request){
//
//        List<Category> categories = userService.getCategories(request);
//        return new CMRespDTO<>(1,"성공", null);
//    }
}
