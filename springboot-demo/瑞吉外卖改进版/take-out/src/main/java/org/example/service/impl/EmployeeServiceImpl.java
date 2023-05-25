package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.service.EmployeeService;
import org.example.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public R<EmployeeDto> login(HttpServletRequest request, Employee employee) {
        // 密码 md5 处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 数据库查询
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        // 检查查询结果
        if(emp == null){
            return R.error("登录失败！");
        }
        // 比对密码
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败！");
        }
        // 查看员工状态
        if(emp.getStatus() == 0){
            return R.error("账号已禁用！");
        }
        // 将员工id存放在Session并返回登录结果

        Map<String,Object> map = new HashMap<>();
        map.put("id",emp.getId().toString());
        map.put("username",emp.getUsername());
        String jwt = JWTUtil.setJWT(7,map);
//        redisTemplate.opsForValue().set(emp.getId().toString(),jwt,7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set("token1","token1");
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(new EmployeeDto(emp,jwt));
    }
}
