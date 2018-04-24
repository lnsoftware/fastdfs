package leimingtech.fileupload.framework.exception;


/**
 * 类 名: FastdfsException
 * 描 述: fastdfs 异常
 * 作 者: 张桐
 * 创 建：2018/3/29 上午10:36
 * 版 本：v1.0.0
 *
 * 历 史: (版本) 作者 时间 注释
 */
public class FastdfsException extends Exception {
	private static final long serialVersionUID = -1848618491499044704L;

	private String code;
	private String description;


	public FastdfsException(String code, String message) {
		super(message);
		this.code = code;
	}

	public FastdfsException(String code, String message, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}

	/**
	 * 错误码
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 用户可读描述信息
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append(": [");
		sb.append("FAST-DFS");
		sb.append("] - ");
		sb.append(code);
		sb.append(" - ");
		sb.append(getMessage());
		if (getDescription() != null) {
			sb.append(" - ");
			sb.append(getDescription());
		}
		return sb.toString();
	}
}
