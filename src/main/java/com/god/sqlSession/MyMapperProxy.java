package com.god.sqlSession;

import com.god.config.Function;
import com.god.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * MyMapperProxy代理类完成xml方法和真实方法对应，执行查询
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 10:46
 **/
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;

    private MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration,MySqlSession mySqlSession) {
        this.myConfiguration = myConfiguration;
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = myConfiguration.readMapper("com/god/Mapper/xml/UserMapper.xml");
        //是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
            return null;
        }
        List<Function> list = mapperBean.getList();
        if (list != null || 0 != list.size()) {
            for (Function fc : list) {
                //id是否和接口方法名一样
                if (method.getName().equals(fc.getFuncName())) {
                    return mySqlSession.selectOne(fc.getSql(),String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}