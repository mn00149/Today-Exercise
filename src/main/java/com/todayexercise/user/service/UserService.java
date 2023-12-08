package com.todayexercise.user.service;

import com.todayexercise.category.Category;
import com.todayexercise.user.dto.ResponseCategoryDTO;
import com.todayexercise.user.repository.UserRepository;
import com.todayexercise.user.dto.UpdateDTO;
import com.todayexercise.user.model.User;

import com.todayexercise.userTocategory.UserToCategory;
import com.todayexercise.userTocategory.UserToCategoryRepository;
import com.todayexercise.util.CustomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserToCategoryRepository userToCategoryRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    CustomUtil customUtil;
    public void registerUser(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }


    public boolean userIdDupliCheck(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) return true;
        return false;
    }

    public boolean emailDupliCheck(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) return true;
        return false;
    }
    @Transactional
    public User updateUser(HttpServletRequest request, UpdateDTO updateDTO) {
        String userId = new CustomUtil().getUserIdByJWT(request);
        // 1. 영속화
        // 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익섹션 발동시킬께 orElseThrow()
        //User userEntity = userRepository.findByUserId(userId).orElseThrow(() -> { return new CustomValidationApiException("찾을 수 없는 id입니다.");});
        User userEntity = userRepository.findByUserId(userId);

        String updatedEmail = updateDTO.getEmail();
        String updatedAddress1 = updateDTO.getAddress1();
        String updatedAddress2 = updateDTO.getAddress2();
        String rawPassword = updateDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setEmail(updatedEmail);
        userEntity.setPassword(encPassword);
        userEntity.setAddress1(updatedAddress1);
        userEntity.setAddress2(updatedAddress2);

        return userEntity;
    }

//    public void registerCategories(HttpServletRequest request, ArrayList<Long> categories) {
//    }
    @Transactional
    public void registerCategories(HttpServletRequest request, ArrayList<Long> categories) {
        String userId = new CustomUtil().getUserIdByJWT(request);
        Long user_index = userRepository.findByUserId(userId).getId();
        for(Long category:categories){
            userToCategoryRepository.registCategory(user_index, category);
        }
    }

    @Transactional
    public void registerCategories2(HttpServletRequest request, ArrayList<Long> categories) {
        String userId = new CustomUtil().getUserIdByJWT(request);
        Long user_index = userRepository.findByUserId(userId).getId();

    }

    //수정 필요 카테고리 전체 조회 말고 특정조건에 맞는거 다 조회 하는 걸로
//    @Transactional
//    public ArrayList<Category> getCategories(HttpServletRequest request) {
//        String userId = new CustomUtil().getUserIdByJWT(request);
//        Long user_index = userRepository.findByUserId(userId).getId();
//        List<UserToCategory> userToCategories = userToCategoryRepository.findAll();
//        ArrayList<Category> categories2 = new ArrayList<Category>();
//        ResponseCategoryDTO categories = new ResponseCategoryDTO();
//        for(UserToCategory userToCategory: userToCategories){
//            if(userToCategory.getUser().getId()==user_index){
//                categories.setCategories(userToCategory.getCategory().getCategory());
//            };
//        }
//
//
//        return categories2;
//
//    }
}
