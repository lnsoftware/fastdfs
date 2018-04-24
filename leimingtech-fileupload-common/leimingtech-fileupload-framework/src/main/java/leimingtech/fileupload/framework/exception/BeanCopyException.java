package leimingtech.fileupload.framework.exception;

public class BeanCopyException extends RuntimeException {

    public BeanCopyException(){
        super();
    }

    public BeanCopyException(Exception e){
        super(e);
    }
}
