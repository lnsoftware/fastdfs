package leimingtech.fileupload.framework.utils.converter;


import leimingtech.fileupload.framework.exception.BeanCopyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 类的转换工具
 *
 * @author panda
 */
@Slf4j
public class BeanConverterProvider {

    /**
     * 私有化构造
     */
    private BeanConverterProvider() {
    }


    /**
     * 单个对象的转换
     *
     * @param o
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T doConvert(Object o, Class<T> tClass) {
        if (o == null) {
            return null;
        }
        return (T) copyProperties(getObjectInstance(tClass), o, true,  null);
    }

    public static <T> T doConvert(Object o, Class<T> tClass, Map<String, String> mapping) {
        if (o == null) {
            return null;
        }
        return (T) copyProperties(getObjectInstance(tClass), o, true, mapping);
    }

    /**
     * 集合的转换
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> doCollectionConvert(List list, Class<T> tClass) {
        ArrayList<T> result = new ArrayList();
        for (Object o : list) {
            result.add(doConvert(o, tClass));
        }
        return result;
    }

    private static <T> T getObjectInstance(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 复制属性，只复制值不为空的
     * @param o
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T doCopy(Object o, T t){
        if (o == null) {
            return null;
        }
        return (T)copyProperties(t, o, false, null);
    }

    /**
     * 属性的复制
     *
     * @param dest
     * @param orig
     * @return
     */
    private static Object copyProperties(Object dest, Object orig, boolean copyAll, Map<String, String> mapping) {
        if (dest == null || orig == null) {
            return dest;
        }

        PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(dest);
        try {
            for (int i = 0; i < destDesc.length; i++) {
                Class destType = destDesc[i].getPropertyType();
                String destPropertyName = destDesc[i].getName();
                String origPropertyName = destPropertyName;
                Class origType = PropertyUtils.getPropertyType(orig, destPropertyName);
                //如果有属性映射，则从属性映射中获取
                if(origType == null && mapping != null){
                    origPropertyName = mapping.get(destPropertyName);
                    if(origPropertyName != null){
                        origType = PropertyUtils.getPropertyType(orig, origPropertyName);
                    }
                }
                boolean isFlag = origType != null && destType != null
                        && (destType.equals(origType)
                        || Converter.class.isAssignableFrom(origType)
                        || Converter.class.isAssignableFrom(destType))
                        && !destType.equals(Class.class);
                if (isFlag) {
                    if (!Collection.class.isAssignableFrom(origType)) {
                        try {
                            Object value = PropertyUtils.getProperty(orig, origPropertyName);
                            if (value != null) {
                                if (Converter.class.isAssignableFrom(origType) || Converter.class.isAssignableFrom(destType)) {
                                    value = copyProperties(value, destType, copyAll, mapping);
                                }
                            }

                            if(copyAll == false ){
                                if(value != null){
                                    PropertyUtils.setProperty(dest, destPropertyName, value);
                                }
                            }else{
                                PropertyUtils.setProperty(dest, destPropertyName, value);
                            }
                        } catch (Exception ex) {

                        }
                    }
                }
            }

            return dest;
        } catch (Exception ex) {
            throw new BeanCopyException(ex);
        }
    }

}
