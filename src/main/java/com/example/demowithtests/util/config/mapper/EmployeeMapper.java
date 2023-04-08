package com.example.demowithtests.util.config.mapper;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.domain.EmployeesCabinetsKey;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

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

    default Integer cabinetId(EmployeesCabinetsKey value) {
        return value != null ? value.getCabinetId() : null;
    }
    default Integer cabinetCapacity(EmployeesCabinets cabinets) {
        return cabinets != null ? cabinets.getCabinet().getCapacity() : null;
    }

    default CabinetResponse employeesCabinetsToCabinetResponse(EmployeesCabinets employeesCabinets) {
        if ( employeesCabinets == null ) {
            return null;
        }

        CabinetResponse cabinetResponse = new CabinetResponse();

        cabinetResponse.id = cabinetId(employeesCabinets.getId());
        cabinetResponse.capacity = cabinetCapacity(employeesCabinets);

        return cabinetResponse;
    }
}
