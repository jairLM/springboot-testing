package net.javaguides.springboot.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String name, Throwable cause){
        super(name, cause);
    }


}
