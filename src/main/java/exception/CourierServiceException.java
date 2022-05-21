package exception;

public class CourierServiceException extends Exception{
    private String message;
    public CourierServiceException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
