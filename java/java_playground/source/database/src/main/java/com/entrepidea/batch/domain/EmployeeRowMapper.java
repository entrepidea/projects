package com.entrepidea.batch.domain;

import com.entrepidea.batch.domain.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
        employee.setFirstName(rs.getString("FIRST_NAME"));
        employee.setLastName(rs.getString("LAST_NAME"));
        employee.setEmail(rs.getString("EMAIL"));
        employee.setPhoneNumber(rs.getString("PHONE_NUMBER"));
        employee.setHireDate(rs.getDate("HIRE_DATE"));
        employee.setJobId(rs.getString("JOB_ID"));
        employee.setSalary(rs.getDouble("SALARY"));
        employee.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
        employee.setManagerId(rs.getInt("MANAGER_ID"));
        employee.setDepartmentId(rs.getInt("DEPARTMENT_ID"));
        return employee;
    }
}
