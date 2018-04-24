package leimingtech.fileupload.framework.constant;


import leimingtech.fileupload.framework.exception.FastdfsException;

/**
 * 类 名: ErrorCodes
 * 描 述: 错误枚举类
 * 作 者: panda
 * 创 建：2018/3/29 上午10:36
 * 版 本：v1.0.0
 *
 * 历 史: (版本) 作者 时间 注释
 */
public enum ErrorCodes {

	PARAMETER_IS_NULL("21001", "必填参数为空", "必填参数为空"),

	FASTDFS_CONNECTION_FAIL("21002", "连接fastdfs服务器失败", "文件上传异常，请重试"),

	WAIT_IDLECONNECTION_TIMEOUT("21003", "等待空闲连接超时", "连接超时，请重试"),

	NOT_EXIST_GROUP("21004", "文件组不存在", "文件组不存在"),

	UPLOAD_RESULT_ERROR("21005", "fastdfs文件系统上传返回结果错误", "文件上传异常，请重试"),

	NOT_EXIST_PORTURL("21006", "未找到对应的端口和访问地址", "文件上传异常，请重试"),

	SYS_ERROR("21007", "系统错误", "系统错误"),

	FILE_PATH_ERROR("2108", "文件访问地址格式不对","文件访问地址格式不对"),

	DELETE_RESULT_ERROR("21009", "fastdfs文件系统删除文件返回结果错误", "文件删除异常，请重试"),

	NOT_EXIST_FILE("21010", "文件不存在", "文件不存在");

	/** 错误码 */
	String code;
	/** 错误信息，用于日志输出，便于问题定位 */
	String message;
	/** 错误提示，用于客户端提示 */
	String descreption;

	ErrorCodes(String code, String message) {
		this.message = message;
		this.code = code;
	}

	ErrorCodes(String code, String message, String descreption) {
		this.message = message;
		this.code = code;
		this.descreption = descreption;
	}

	public FastdfsException ERROR() {
		return new FastdfsException(this.code, this.message,
				this.descreption);
	}

	public FastdfsException ERROR(String descreption) {
		return new FastdfsException(this.code, this.message,
				descreption);
	}
}