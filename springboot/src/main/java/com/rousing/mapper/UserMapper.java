package com.rousing.mapper;

import com.rousing.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectUser(User user);

    int insertUser(User user);

    int updateUser(User user);
}
