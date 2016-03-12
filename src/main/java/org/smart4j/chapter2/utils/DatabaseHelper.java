package org.smart4j.chapter2.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库工具类
 * Created by alvin on 2016/3/12.
 */
public final class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    /**
     * 使用ThreadLocal来存放本地线程变量 确保线程安全
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
    /**
     * 使用Apache DBCP2 数据库连接池
     */
    private static final BasicDataSource DATA_SOURCE;

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties =PropertiesUtil.loadProperties("config.properties");
        DRIVER = properties.getProperty(DatabaseConfig.MYSQL.getDriver());
        URL = properties.getProperty(DatabaseConfig.MYSQL.getUrl());
        USERNAME = properties.getProperty(DatabaseConfig.MYSQL.getUsername());
        PASSWORD = properties.getProperty(DatabaseConfig.MYSQL.getPassword());

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
//        try{
//            Class.forName(DRIVER);
//        }catch(ClassNotFoundException e){
//            logger.error("can not load jdbc driver",e);
//        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection == null){
            try {
//                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                logger.error("get connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }

        return connection;
    }

    /**
     * 关闭数据库连接
     */
//    public static void closeConnection(){
//        Connection connection = CONNECTION_HOLDER.get();
//        if(connection != null){
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                logger.error("close connection failure",e);
//                throw new RuntimeException(e);
//            }finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    /**
     * 使用apache commons DbUtils QueryRunner 查询实体列表 不带过滤条件
     * @param entityClass
     * @param sql
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql){
        Connection connection = getConnection();
        List<T> entityList = null;
        try {
            entityList = QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass));
        } catch (SQLException e) {
            logger.error("query entity list failure",e);
        }
//        finally {
//            closeConnection();
//        }
        return entityList;
    }

    /**
     * 根据id查询实体
     * 使用apache commons DbUtils
     * @param entityClass
     * @param sql
     * @param params 需要查询的id
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;
        Connection connection = getConnection();
        try {
            entity = QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            logger.error("query entity failure",e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entity;
    }

    /**
     * 执行查询语句
     * 可执行多表查询
     * @param sql
     * @param params 传入sql过滤参数
     * @return List<Map<String,Object>>
     */
    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> result = null;
        Connection connection = getConnection();
        try {
            result = QUERY_RUNNER.query(connection,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            logger.error("execute query failure",e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return result;
    }

    /**
     * 执行更新语句
     * @param sql
     * @param params sql过滤需要的参数
     * @return
     */
    public static int executeUpdate(String sql,Object... params){
        Connection connection = getConnection();
        int result;

        try {
            result = QUERY_RUNNER.update(connection,sql,params);
        } catch (SQLException e) {
            logger.error("execute update failure", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return result;
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap Map<"字段名称","字段值">
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            logger.error("can not insert entity:fieldMap is empty");
            return false;
        }

        String sql ="INSERT INTO "+getTableName(entityClass);
        StringBuffer columns = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");

        for(String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(", "),values.length(),")");
        sql += columns + "VALUES" + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 获取entityClass的名称 当做表名
     * @param entityClass
     * @param <T>
     * @return
     */
    private static <T> String getTableName(Class<T> entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param fieldMap Map<"字段名称","字段值">
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            logger.error("can not update entity:fieldMap is empty");
            return false;
        }

        String sql = "UPDATE "+getTableName(entityClass) +" SET ";
        StringBuffer columns = new StringBuffer();
        for(String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(" = ?, ");
        }
        sql += columns.substring(0,columns.lastIndexOf(", ")) + " WHERE id = ?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql,params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql = "DELETE FROM "+getTableName(entityClass) +" WHERE id = ?";
        return executeUpdate(sql,id) == 1;
    }

    public static void executeSqlFile(String filePath){
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String sql;
            while ((sql = reader.readLine()) != null){
                executeUpdate(sql);
            }
        } catch (IOException e) {
            logger.error("execute sql file failure",e);
        }
    }
}
