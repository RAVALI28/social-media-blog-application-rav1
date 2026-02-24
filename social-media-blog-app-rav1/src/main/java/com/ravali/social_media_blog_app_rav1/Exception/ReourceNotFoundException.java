package com.ravali.social_media_blog_app_rav1.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReourceNotFoundException extends RuntimeException{

    //resource
    //fieldName
    //fieldValue

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ReourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s :: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}
