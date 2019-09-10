package com.god.sqlSession;

/**
 * <p>
 *
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 10:20
 **/
public interface Excutor {

    public <T> T query(String statement,Object parameter);
}