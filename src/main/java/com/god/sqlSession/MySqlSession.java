package com.god.sqlSession;

import java.lang.reflect.Proxy;

/**
 * <p>
 * 实现我们的MySqlSession,首先的成员变量里得有Excutor和MyConfiguration，代码的精髓就在getMapper的方法里。
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 10:22
 **/
public class MySqlSession {

    private Excutor excutor = new MyExcutor();
    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement,Object parameter) {
        return excutor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clas) {
        //动态代理调用
        return (T)Proxy.newProxyInstance(clas.getClassLoader(), new Class[]{clas}, new MyMapperProxy(myConfiguration, this));

    }
}