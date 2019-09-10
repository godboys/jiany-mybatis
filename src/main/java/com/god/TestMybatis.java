package com.god;

import com.god.Mapper.UserMapper;
import com.god.bean.User;
import com.god.sqlSession.MySqlSession;

/**
 * <p>
 * 测试mybatis
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 10:57
 **/
public class TestMybatis {

    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}