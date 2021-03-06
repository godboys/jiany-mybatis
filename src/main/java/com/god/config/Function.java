package com.god.config;

/**
 * <p>
 * Function对象包括sql的类型，方法名，sql语句，返回类型和参数类型
 * </p>
 *
 * @Author yangjian
 * @Create 2019-9-3 9:50
 **/
public class Function {

    private String sqlType;
    private String funcName;
    private String sql;
    private Object resultType;
    private String parameterType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}