package leimingtech.fileupload.framework.utils;

import leimingtech.fileupload.framework.constant.ErrorCodes;
import leimingtech.fileupload.framework.constant.LinkType;
import leimingtech.fileupload.framework.exception.FastdfsException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 公共工具类
 * 
 * @author mr_smile
 * 
 */
public class CommonUtils {
	/** 日志 */
	private final static Logger log = Logger.getLogger(CommonUtils.class);

	/**
	 * 根据存储类型获取组名
	 * @param linkType 存储类型
	 * @return
	 */
	public static String getFastdfsGroup(LinkType linkType, String logId)throws FastdfsException {
		String groupName = "";
		if (linkType == null || linkType.equals(LinkType.OUTERNET)) {
			groupName ="group1";
		} else {
			groupName = "group1";
		}
		if (StringUtils.isBlank(groupName)) {
			log.error("[根据存储类型获取组名(getFastdfsGroup)][" + logId + "][错误:未找到对应组（groupNamme=" + groupName + "）]");
			throw ErrorCodes.NOT_EXIST_GROUP.ERROR();
		}
		return groupName;
	}

	/**
	 * 
	 * @Description:根据连接类型，获取对应端口和地址
	 * @param linkType 连接类型
	 * @return
	 * @throws FastdfsException
	 */
	public static String getFileLinkPath(LinkType linkType, String logId)
			throws FastdfsException {
		String port = "";
		if (linkType == null || linkType.equals(LinkType.OUTERNET)) {
			port = "8888";
		} else {

			port = "8888";
		}
		String url = "http://172.16.55.137";
		if (StringUtils.isBlank(port) || StringUtils.isBlank(url)) {
			log.error("[获取文件连接地址(getFileLinkPath)][" + logId + "][错误:未找到对应连接地址和端口（url=" + url + ",port=" + port + "）]");
			throw ErrorCodes.NOT_EXIST_PORTURL.ERROR();
		}
		return url + ":" + port;
	}

	/**
	 * 根据文件路径解析出端口和文件名
	 * @param filePath 文件路径
	 * @return [0]： 组名 [1]： 文件名
	 */
	public static String[] parseFilePath(String filePath, String logId)
			throws FastdfsException {
		String group_name = "";
		String remote_filename = "";
		if (StringUtils.isNotBlank(filePath)) {
			String[] strArray = filePath.split("/");
			if (strArray != null) {
				/** 包含ip地址或域名 */
				if (filePath.contains("//") && strArray.length > 5) {
					group_name = strArray[3];
				}

				/** 不包含IP地址和域名 */
				else if (strArray.length >= 2) {
					group_name = StringUtils.isNotEmpty(strArray[0]) ? strArray[0] : strArray[1];
				}
				// 远程文件名
				remote_filename = filePath.split(group_name + "/")[1];
			}
		}
		
		/** 如果组名或远程文件名为空，说明传入的地址不合法 */
		if (StringUtils.isBlank(group_name) || StringUtils.isBlank(remote_filename)) {
			log.error("[解析文件访问地址(parseFilePath)][" + logId
					+ "][错误:文件地址格式不对（filePath=" + filePath + ",group_name="
					+ group_name + ",remote_filename=" + remote_filename + "）]");
			throw ErrorCodes.FILE_PATH_ERROR.ERROR();
		}

		String[] results = new String[2];
		results[0] = group_name;
		results[1] = remote_filename;

		return results;
	}
}
