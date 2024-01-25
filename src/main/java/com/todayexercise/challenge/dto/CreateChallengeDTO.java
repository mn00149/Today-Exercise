package com.todayexercise.challenge.dto;

import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.user.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CreateChallengeDTO {
    private Long id;

    private Long userId;
    private String challengeName;
    private String isOwner;
    private String address1;
    private String address2;
    private int personnel;
    //카테고리 연동후 쓸것
    private List<Long> categoryIdList;


    public Challenge toEntity(){
        return Challenge.builder()
                .id(id)
                .challengeName(challengeName)
                .address1(address1)
                .address2(address2)
                .personnel(personnel)
                .build();
    }
}
