package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.R;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    R<EmployeeDto> login(HttpServletRequest request, Employee employee);
}
