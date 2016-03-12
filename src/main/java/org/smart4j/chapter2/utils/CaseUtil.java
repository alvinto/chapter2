package org.smart4j.chapter2.utils;

/**
 * 类型转换工具类
 * Created by alvin on 2016/3/12.
 */
public final class CaseUtil {
    /**
     * 转换为Sting类型（默认返回“”）
     * @param o
     * @return
     */
    public static String castString(Object o){
        return castString(o,"");
    }

    /**
     * 转换为String类型（可指定默认字符串）
     * @param o
     * @param defaultValue
     * @return
     */
    public static String castString(Object o,String defaultValue){
        return o == null ? defaultValue:String.valueOf(o);
    }


    /**
     * 转换为double类型（默认返回0）
     * @param o
     * @return
     */
    public static double castDouble(Object o){
        return castDouble(o, 0);
    }

    /**
     * 转换为double类型（可指定默认数值）
     * @param o
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object o,double defaultValue){
        double doubleValue = defaultValue;
        if(o != null){
            String strValue = castString(o);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue = defaultValue;
                }

            }
        }
        return doubleValue;
    }

    /**
     * 转换为int类型（默认返回0）
     * @param o
     * @return
     */
    public static int castInt(Object o){
        return castInt(o, 0);
    }

    /**
     * 转换为int类型（可指定默认数值）
     * @param o
     * @param defaultValue
     * @return
     */
    public static int castInt(Object o,int defaultValue){
        int intValue = defaultValue;
        if(o != null){
            String strValue = castString(o);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    intValue = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    intValue = defaultValue;
                }

            }
        }
        return intValue;
    }

    /**
     * 转换为long类型（默认返回0）
     * @param o
     * @return
     */
    public static long castLong(Object o){
        return castLong(o, 0);
    }

    /**
     * 转换为long类型（可指定默认数值）
     * @param o
     * @param defaultValue
     * @return
     */
    public static long castLong(Object o,long defaultValue){
        long longValue = defaultValue;
        if(o != null){
            String strValue = castString(o);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue = defaultValue;
                }

            }
        }
        return longValue;
    }

    /**
     * 转换为boolean类型（默认返回false）
     * @param o
     * @return
     */
    public static boolean castBoolean(Object o){
        return castBoolean(o, false);
    }

    /**
     * 转换为布尔类型（可指定默认值）
     * @param o
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object o,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(o != null){
            booleanValue = Boolean.parseBoolean(castString(o));
        }
        return booleanValue;
    }

}
