package com.example.demowithtests.webTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.employee.EmployeeService;
import com.example.demowithtests.service.photo.PhotoService;
import com.example.demowithtests.util.config.mapper.EmployeeMapper;
import com.example.demowithtests.web.employeeController.EmployeeControllerBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeControllerBean.class)
public class EmployeeControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeMapper employeeMapper;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    PhotoService photoService;

    @MockBean
    EmployeeRepository employeeRepository;

    Employee employeeTest;

    @BeforeEach
    void setUp() {
        employeeTest = Employee
                .builder()
                .id(1)
                .name("Fedir")
                .country("UA")
                .email("fedir@gmail.com")
                .dateOfBirthday(LocalDate.parse("1999-02-21"))
                .isFired(Boolean.FALSE)
                .gender(Gender.M)
                .passport(new Passport())
                .addresses(new HashSet<>())
                .photos(new HashSet<>())
                .employeesCabinets(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("POST /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createTest() throws Exception {
        var response = new EmployeeDto();
        response.id = 1;

        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(response);
        when(employeeMapper.dtoToEmployee(any(EmployeeDto.class))).thenReturn(employeeTest);
        when(employeeService.create(any(Employee.class))).thenReturn(employeeTest);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeTest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

        verify(employeeService).create(any(Employee.class));
    }

    @Test
    @DisplayName("GET /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getByIdTest() throws Exception {
        var response = new EmployeeReadDto();
        response.name = "Fedir";

        when(employeeMapper.employeeToReadDto(any(Employee.class))).thenReturn(response);
        when(employeeService.getById(1)).thenReturn(employeeTest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Fedir")));

        verify(employeeService).getById(anyInt());
    }

    @Test
    @DisplayName("PUT /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updateByIdTest() throws Exception {
        var response = new EmployeeDto();
        response.id = 1;

        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(response);
        when(employeeMapper.dtoToEmployee(any(EmployeeDto.class))).thenReturn(employeeTest);
        when(employeeService.updateById(eq(1), any(Employee.class))).thenReturn(employeeTest);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeTest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(employeeService).updateById(eq(1), any(Employee.class));
    }

    @Test
    @DisplayName("DELETE /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deleteTest() throws Exception {
        doNothing().when(employeeService).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(employeeService).removeById(1);
    }
}
