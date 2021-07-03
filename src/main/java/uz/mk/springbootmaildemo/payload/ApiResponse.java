package uz.mk.springbootmaildemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;


//    public ApiResponse(String message, boolean success) {
//        this.message = message;
//        this.success = success;
//    }
}
