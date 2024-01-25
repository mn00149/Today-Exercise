package com.todayexercise.challenge.dto;

import com.todayexercise.challenge.model.Challenge;
import lombok.Data;

@Data
public class GetChallengeDTO  {
    private Long id;
    private String challengeName;
//
//    public GetChallengeDTO(Challenge challenge){
//        id = challenge.getId();
//        challengeName = challenge.getChallengeName();
//    }
}
