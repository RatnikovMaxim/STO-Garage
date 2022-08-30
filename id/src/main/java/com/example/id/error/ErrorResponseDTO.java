package com.example.id.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ErrorResponseDTO {

    public static final String SERVER_ERROR = "server_error";
    public static final String ARGUMENT_NOT_VALID = "argument_not_valid";
    public static final String EXECUTE_ACCESS_FORBIDDEN = "Execute access forbidden";
    public static final String THE_SERVER_CANNOT_FIND_THE_DATA = "the server cannot find the data";
    public static final String THE_SERVER_CANNOT_FIND_USER_DATA = "the server cannot find user data";

    private  String  message;
}
