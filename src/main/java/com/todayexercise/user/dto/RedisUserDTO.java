package com.todayexercise.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;



@RedisHash(value = "user", timeToLive = 60) // options: timeToLive = 10
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RedisUserDTO {
    private static final long serialVersionUID = -214490344996507077L;

    @Id
    private String id;

    private String name;
}
