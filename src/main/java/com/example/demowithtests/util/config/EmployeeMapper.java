package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toEmployeeDto(Employee employee);

    Employee dtoToEmployee(EmployeeDto dto);

    EmployeeReadDto employeeToReadDto(Employee employee);

    List<EmployeeReadDto> toListReadDto(List<Employee> employees);

    default Page<EmployeeDto> toPageDto(Page<Employee> employees) {
        return employees.map(this::toEmployeeDto);
    }

    default Page<EmployeeReadDto> toPageReadDto(Page<Employee> employees) {
        return employees.map(this::employeeToReadDto);
    }
}
