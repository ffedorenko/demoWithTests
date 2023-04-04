package com.example.demowithtests.web.employeeController;

import com.example.demowithtests.domain.PassportStatus;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeControllerSwagger extends EmployeeController {
    @Override
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeDto saveEmployee(EmployeeDto requestForSave);

    @Override
    @Operation(summary = "This is endpoint gets all employees.", description = "Shows all employees", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsers();

    @Override
    @Operation(summary = "This is endpoint gets employees with pagination.", description = "Shows all employees in DB  with pagination", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Page<EmployeeReadDto> getPage(int page, int size);

    @Override
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeReadDto getEmployeeById(Integer id);

    @Override
    @Operation(summary = "This is endpoint replace employee with another.", description = "PUT for employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeDto refreshEmployee(Integer id, EmployeeDto dto);

    @Override
    @Operation(summary = "This is endpoint deletes employee", description = "DELETE for employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeEmployeeById(Integer id);

    @Override
    @Operation(summary = "Removes all employees", description = "Drop database", tags = {"DB"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeAllUsers();

    @Override
    @Operation(summary = "This is endpoint returns employee from specific country with pagination",
            description = "find employee based on country", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Page<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Override
    @Operation(summary = "This is endpoint returns a list of all the countries that employees are from",
            description = "get all the countries of all the employees", tags = {"Country"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<String> getAllUsersC();

    @Override
    @Operation(summary = "This is endpoint returns a list of all the countries that employees are from sorted",
            description = "get all the countries of all the employees countries in alphabetical order", tags = {"Country"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<String> getAllUsersSort();

    @Override
    @Operation(summary = "This is endpoint returns a list of all the emails that employees have",
            description = "get all emails of all the employees", tags = {"Email"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Optional<String> getAllUsersSo();

    @Override
    @Operation(summary = "This is endpoint find all employees with expired photos", description = "find all with expired photo", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsersByExpiredPhotos();

    @Override
    @Operation(summary = "This is endpoint sends email to all employees with expired photos",
            description = "sends email to employee, whose photo is expired", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void sendMail();

    @Override
    EmployeeReadDto addPhotoToEmployee(MultipartFile file, String description, Integer id) throws IOException, HttpMediaTypeNotSupportedException;

    @Override
    @Operation(summary = "This is endpoint shows photo", description = "shows photo", tags = {"Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    byte[] getPhoto(Integer id);

    @Override
    @Operation(summary = "This is endpoint removes photo with {id}", description = "removes photo", tags = {"Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removePhoto(Integer id);

    @Override
    @Operation(summary = "This is endpoint adds passport to employee", description = "add passport to employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto addPassportToEmployee(Integer employeeId);

    @Override
    @Operation(summary = "This is endpoint replace passport for some reason", description = "replace passport for some reason", tags = {"Employee"})
    EmployeeReadDto getNewPassport(Integer id, PassportStatus reason);

    @Override
    @Operation(summary = "This is endpoint delete passport", description = "delete passport", tags = {"Employee"})
    void deletePassFromEmployee(Integer id);
}
