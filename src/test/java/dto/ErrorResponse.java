package dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    Integer code;
    String type;
    String message;
}
