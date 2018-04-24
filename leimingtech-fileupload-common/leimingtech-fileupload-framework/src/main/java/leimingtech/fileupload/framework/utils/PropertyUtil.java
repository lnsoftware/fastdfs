package leimingtech.fileupload.framework.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 配置文件读取工具类
 * 
 * @author mr_smile
 * @version premas.1.0
 * @see
 * @since 2014年7月11日
 */
public final class PropertyUtil {
	private static final Logger LOGGER = Logger.getLogger(PropertyUtil.class);

	private PropertyUtil() {

	}

	/** 属性集 */
	private static Properties props = new Properties();
	/** 外放对象 property.properties 对应的对象 */
	private static PropertyUtil propertyFile = null;

	/** 构造私有 "property.properties" */
	private PropertyUtil(String fileName) {
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
			props.load(in);
		} catch (Exception e) {
			LOGGER.error("读取配置文件异常", e);
		}
	}

	/** 公共方法获取实例 */
	public static PropertyUtil getPropertyInstans() {
		if (propertyFile == null) {
			propertyFile = new PropertyUtil("property.properties");
		}
		return propertyFile;
	}

	/**
	 * 获取字符串对应值
	 * 
	 * @throws IOException
	 */
	public String getValue(String key) {
		// 取出相关值
		try {
			return props.getProperty(key);
		} catch (Exception e) {
			LOGGER.error("读取配置文件属性异常", e);
			return "";
		}
	}
}
