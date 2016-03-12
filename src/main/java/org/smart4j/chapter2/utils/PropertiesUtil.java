package org.smart4j.chapter2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by alvin on 2016/3/12.
 * 操作properties文件工具类
 */
public final class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 加载属性文件
     * @param fileName 文件名称
     * @return
     */
    public static Properties loadProperties(String fileName){
        Properties properties = null;
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(inputStream == null){
                throw new FileNotFoundException(fileName+"file is not found");
            }
            properties = new Properties();
            properties.load(inputStream);
        }catch (IOException e){
            logger.error("load properties file failure",e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch(IOException e){
                    logger.error("close input stream failure");
                }

            }
        }
        return properties;
    }

    /**
     * 读取字符串属性（默认空字符串）
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties,String key){
        return getString(properties,key,"");
    }

    /**
     * 读取字符串属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties,String key,String defaultValue){
        String value = defaultValue;
        if(properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * 读取数值属性（默认值0）
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }
    /**
     * 读取数值属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultInt
     * @return
     */
    public static int getInt(Properties properties,String key,int defaultInt){
        int value = defaultInt;
        if(properties.containsKey(key)){
            value = CaseUtil.castInt(properties.getProperty(key));
        }
        return value;
    }
    /**
     * 读取布尔属性（默认值false）
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties, key,false);
    }
    /**
     * 读取布尔属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultInt
     * @return
     */
    public static boolean getBoolean(Properties properties,String key,boolean defaultInt){
        boolean value = defaultInt;
        if(properties.containsKey(key)){
            value = CaseUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }
}
