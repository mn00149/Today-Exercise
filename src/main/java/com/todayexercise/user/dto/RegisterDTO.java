package com.todayexercise.user.dto;

import com.todayexercise.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String address1;
    private String address2;

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .email(email)
                .address1(address1)
                .address2(address2)
                .build();


    }

}
