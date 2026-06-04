package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * insert employee data
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user, status)" +
            "values " +
            "(#{name},#{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")

    void insert(Employee employee);

    /**
     * pageQuery
     * @param employeePageQueryDTO
     * @return
     */

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Dynamically modify properties based on primary key
     * @param employee
     */
    void update(Employee employee);
}
