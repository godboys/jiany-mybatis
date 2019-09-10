package com.god.sqlSession;

import com.god.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *  MyExecutor中封装了JDBC的操作
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 10:23
 **/
public class MyExcutor implements Excutor {

    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1,parameter.toString());
            set = pre.executeQuery();
            User u = new User();
            //遍历结果集
            while (set.next()) {
                u.setId(set.getString(1));
                u.setUserName(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T)u;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;
    }

    private Connection getConnection() {
        try {
            Connection connection = xmlConfiguration.build("config/datasource.xml");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}