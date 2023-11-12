package com.todayexercise.user;

import com.todayexercise.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;


    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for(User user: users){
            if(user.getId()==id)return user;
        }
        return null;
    }
}
