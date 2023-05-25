package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.common.R;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request 设置session
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<EmployeeDto> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.login(request,employee);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 清理Session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        // 设置员工初始密码123456（使用md5加密）
        employee.setPassword((DigestUtils.md5DigestAsHex("123456".getBytes())));
        // 设置员工创建和更新时间
         employee.setCreateTime(LocalDateTime.now());
         employee.setUpdateTime(LocalDateTime.now());
        // 设置当前登录用户和最后更新用户的id
        log.info("最后更新用户的id:{}",request.getSession().getAttribute("employee"));
         Long enpId = (Long)request.getSession().getAttribute("employee");
//         employee.setCreateUser(enpId);
//         employee.setUpdateUser(enpId);
        employee.setCreateUser(1L);
        employee.setUpdateUser(1L);
        // 保存员工信息
        employeeService.save(employee);
        return R.success("新增员工成功！");
    }

    /**
     * 员工信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        // 构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        // 添加过滤条件
        queryWrapper.like(StringUtils.hasText(name),Employee::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info("修改员工信息");
        // 修改更新时间
         employee.setUpdateTime(LocalDateTime.now());
        // 修改用户
         employee.setUpdateUser(1L);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功！");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应的员工信息！");
    }
}
