package com.todayexercise.user.dto;

import com.todayexercise.user.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class UpdateDTO {

    private String password;
    private String email;

    private String address1;
    private String address2;

    public User toEntity(){
        return User.builder()

                .password(password)
                .email(email)
                .address1(address1)
                .address2(address2)
                .build();


    }

}
