package org.example.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.example.entity.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmployeeDto implements Serializable {

    private Employee employee;
    private String token;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee, String token) {
        this.employee = employee;
        this.token = token;
    }
}
