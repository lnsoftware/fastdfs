import com.leimingtech.fileupload.core.module.fastDfs.FasfDfsService;
import com.leimingtech.fileupload.service.bootstrap.FileUploadApplication;
import leimingtech.fileupload.entity.file.FileInfoPo;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileUploadApplication.class)
public class JunitTest {

    /**
     * fastdfs
     */
    @Autowired
    private FasfDfsService fastDfsService;

    @Test
    public void test() {

        try {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setFileExtName(".jpg");
            fileInfoPo.setFileBytes(FileUtils.readFileToByteArray(new File("/Users/panda/Desktop/panda.jpg")));
            //调用工具类上传
            FileInfoPo result = fastDfsService.upload(fileInfoPo);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
