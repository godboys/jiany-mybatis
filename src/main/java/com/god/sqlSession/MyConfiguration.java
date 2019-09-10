package com.god.sqlSession;



import com.god.config.Function;
import com.god.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  读取与解析配置信息
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-2 18:11
 **/
public class MyConfiguration {

    private ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取与解析配置信息，并返回处理后的Enviroment
     * @param resource
     * @return
     */
    public Connection build(String resource){
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if(!"database".equals(node.getName())) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for(Object item : node.elements("property")) {
            Element element = (Element) item;
            String value = getValue(element);
            String name = element.attributeValue("name");
            if(name == null || value == null){
                throw new RuntimeException("[database]:<property> should contain name and value");
            }
            //赋值
            switch (name) {
                case "url" : url = value;
                break;
                case "username" : username = value;
                break;
                case "password" : password = value;
                break;
                case "driverClassName" : driverClassName = value;
                break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }
        Connection connection = null;
        //建立数据库连接
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取property属性的值，如果有value的值，则读取 没有设置value，则读取内容
    private String getValue(Element node){
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    @SuppressWarnings("rawtypes")
    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        InputStream stream = loader.getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(stream);
            Element root = document.getRootElement();
            // 把mapper节点的nameSpace值存为接口名
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
            // 用来存储方法的List
            List<Function> list = new ArrayList<Function>();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Function function = new Function();
                Element element = (Element) iterator.next();
                String sqlType = element.getName().trim();
                String funcName = element.attributeValue("id").trim();
                String sql = element.getText().trim();
                String resultType = element.attributeValue("resultType").trim();
                function.setFuncName(funcName);
                function.setSqlType(funcName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                function.setResultType(newInstance);
                function.setSql(sql);
                list.add(function);
            }
            mapper.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return  mapper;
    }




















}