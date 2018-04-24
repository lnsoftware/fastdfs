package leimingtech.fileupload.framework.utils;

import java.util.UUID;

public class IDGenerator {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
