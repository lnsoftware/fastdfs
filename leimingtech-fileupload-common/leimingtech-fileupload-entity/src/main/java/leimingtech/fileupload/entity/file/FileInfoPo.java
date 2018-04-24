package leimingtech.fileupload.entity.file;

import leimingtech.fileupload.entity.base.BasePo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 图片表
 *
 * @author panda
 * @date 2018-03-26 11:25:14
 */
@Data
@Document(collection = "file_info")
public class FileInfoPo extends BasePo implements Serializable{
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 文件扩展名
     */
    private String fileExtName;

    /**
     * 文件字节形式
     */
    private byte[] fileBytes;

    /**
     * 文件访问路径
     */
    private String filePath;
    /**
     * 文件所在组名
     */
    private String groupName;
    /**
     * 文件远程文件名
     */
    private String remoteFilename;


}
