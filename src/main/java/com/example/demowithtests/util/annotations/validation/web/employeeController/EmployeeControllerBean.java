package com.example.demowithtests.util.annotations.validation.web.employeeController;

import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.service.employee.EmployeeService;
import com.example.demowithtests.service.photo.PhotoService;
import com.example.demowithtests.util.config.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeControllerSwagger {

    private final EmployeeService employeeService;
    private final PhotoService photoService;
    private final EmployeeMapper employeeMapper;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        var employee = employeeMapper.dtoToEmployee(requestForSave);
        return employeeMapper.toEmployeeDto(employeeService.create(employee));
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return employeeMapper.toListReadDto(employeeService.getAll());
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        log.info("getPage() Controller - start: page = {}, size = {}", page, size);
        Pageable paging = PageRequest.of(page, size);
        Page<EmployeeReadDto> employeeReadDto = employeeMapper.toPageReadDto(employeeService.getAllWithPagination(paging));
        log.info("getPage() Controller - end: employeeReadDto = {}", employeeReadDto);
        return employeeReadDto;
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.info("getEmployeeById() Controller - start: id = {}", id);
        var employee = employeeService.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);
        var dto = employeeMapper.employeeToReadDto(employee);
        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto dto) {
        var employee = employeeService.updateById(id, employeeMapper.dtoToEmployee(dto));
        return employeeMapper.toEmployeeDto(employee);
    }

    //Удаление по id
    @DeleteMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return employeeMapper.toPageReadDto(
                employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString()));
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @GetMapping("/users/expired-photo/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersByExpiredPhotos() {
        return employeeMapper.toListReadDto(employeeService.getByExpiredPhotos());
    }

    @PostMapping("/users/expired-photo/mail")
    @ResponseStatus(HttpStatus.OK)
    public void sendMail() {
        employeeService.sendMailToUsersWithExpiredPhotos();
    }

    @PostMapping(value = "/users/{id}/photos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addPhotoToEmployee(@RequestParam MultipartFile file, @RequestParam String description,
                                              @PathVariable Integer id)
            throws IOException, HttpMediaTypeNotSupportedException {
        photoService.addPhoto(file, description, id);
        return employeeMapper.employeeToReadDto(employeeService.getById(id));
    }

    @GetMapping(value = "/users/photos/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhoto(@PathVariable Integer id) {
        return photoService.findPhoto(id);
    }

    @DeleteMapping("/users/photo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removePhoto(@PathVariable Integer id) {
        photoService.deletePhoto(id);
    }

    @Override
    @PostMapping("/users/{id}/passes")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addWorkPassToEmployee(@PathVariable("id") Integer employeeId) {
        return employeeMapper.employeeToReadDto(employeeService.addPassportToEmployee(employeeId));
    }

    @Override
    @DeleteMapping("/users/{id}/pass")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassFromEmployee(@PathVariable Integer id) {
        employeeService.deletePassportFromEmployee(id);
    }
}
