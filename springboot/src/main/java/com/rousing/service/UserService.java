package com.rousing.service;

import com.rousing.mapper.UserMapper;
import com.rousing.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    public User getOne(User user) {
        List<User> users = userMapper.selectUser(user);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public List<User> getMore(User user) {
        return userMapper.selectUser(user);
    }

    public boolean userExist(String username) {
        User user = new User();
        user.setUsername(username);
        return this.getOne(user) != null;
    }

    public boolean addUser(User user) {
        int i = userMapper.insertUser(user);
        return i >= 1;
    }

    public boolean updateUser(User user) {
        int i = userMapper.updateUser(user);
        return i >= 1;
    }
}
