package leimingtech.fileupload.entity.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品属性表
 *
 * @author kviuff
 * @date 2018-03-23 15:04:11
 */
@Data
public abstract class BasePo  {


    /**
     * 是否删除0:未删除；1:已删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;








}
