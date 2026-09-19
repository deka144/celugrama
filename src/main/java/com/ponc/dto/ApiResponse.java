package com.ponc.dto;

public class ApiResponse <T>{

    private T data;
    private boolean success;
    private String errorMessage;

    public ApiResponse(){}

    public static <T> ApiResponse<T> ok (T data){
        ApiResponse<T> response = new ApiResponse<>();
        response.data=data;
        response.success=true;
        return response;
    }

    public static <T> ApiResponse<T> error(String errorMessage){
        ApiResponse<T> response = new ApiResponse<>();
        response.success=false;
        response.errorMessage=errorMessage;
        return response;
    }

    public T getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
