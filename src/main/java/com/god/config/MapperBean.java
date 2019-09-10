package com.god.config;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 9:48
 **/
public class MapperBean {

    private String interfaceName;
    private List<Function> list;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}