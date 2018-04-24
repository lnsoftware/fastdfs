package leimingtech.fileupload.entity.image;


import leimingtech.fileupload.entity.base.BasePo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 图片表
 *
 * @author panda
 * @date 2018-03-26 11:25:14
 */
@Data
@Table(name = "fileupload_image")
@Document(collection = "fileupload_image")
public class ImageInfoPo extends BasePo {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 图片描述
     */
    private String imageDesc;

    /**
     * 图片原始名称
     */
    private String imageOriginalName;

    /**
     * 图片大小
     */
    private String imageAmount;


}
