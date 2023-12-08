package com.todayexercise.user.service;

import com.todayexercise.user.dto.RedisUserDTO;
import com.todayexercise.user.repository.UserRedisRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UserRedisService {
   @Autowired
   UserRedisRepositroy repository;

    @Transactional
    public RedisUserDTO addUser(RedisUserDTO user) {
        // save
        RedisUserDTO save = repository.save(user);

        // find
        Optional<RedisUserDTO> result = repository.findById(save.getId());

        // Handling
        // 해당 data 존재시 return.
        if(result.isPresent()) {
            return result.get();
        }else {throw new RuntimeException("Database has no Data");}
    }//save

}
