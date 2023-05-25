package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
