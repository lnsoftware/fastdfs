//package leimingtech.fileupload.framework.utils.page;
//
//import com.esgcc.product.framework.util.converter.BeanConverterProvider;
//import com.github.pagehelper.PageInfo;
//
//import java.util.List;
//
//public class PageUtils {
//    /**
//     * 依据PageInfo返回简单的额PageBean
//     *
//     * @return
//     */
//    public static PageBean simplePage(PageInfo pageInfo) {
//        return PageBean.getInstance(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
//    }
//
//    /**
//     * pageData的类型转换
//     */
//    public static PageBean simplePage(PageInfo pageInfo, Class clazz) {
//        //是否需要转换
//        boolean convert = pageInfo.getList() == null;
//        //返回类型转换后的pagebean
//        return PageBean.getInstance(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), convert ? null : BeanConverterProvider.doCollectionConvert(pageInfo.getList(), clazz));
//    }
//
//    /**
//     * 获取pageInfo实体
//     */
//    public static PageInfo pageInstance(List list) {
//        return new PageInfo(list);
//    }
//
//
//}
