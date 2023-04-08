package com.example.demowithtests.service.employee;

import com.example.demowithtests.domain.*;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.cabinet.CabinetService;
import com.example.demowithtests.service.email.EmailService;
import com.example.demowithtests.service.employeesCabinets.EmployeesCabinetsService;
import com.example.demowithtests.service.passport.PassportService;
import com.example.demowithtests.util.annotations.validation.InitNameAnnotation;
import com.example.demowithtests.util.exception.ResourceNotAvailableException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demowithtests.util.UniqueFieldChecker.isFieldNew;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final PassportService passportService;
    private final CabinetService cabinetService;
    private final EmployeesCabinetsService employeesCabinetsService;

    @InitNameAnnotation
    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        var employee = employeeRepository.findAllByIsFiredIsFalseOrIsFiredIsNull();
        employee.forEach(this::changeFiredStatus);
        return employee;
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAllByIsFiredIsFalseOrIsFiredIsNull(pageable);
        list.forEach(this::changeFiredStatus);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        changeFiredStatus(employee);
        if (employee.getIsFired() == Boolean.TRUE) {
            throw new ResourceNotAvailableException("Employee with this id was fired");
        }
        return employee;
    }

    public void changeFiredStatus(Employee employee) {
        if (employee.getIsFired() == null) {
            employee.setIsFired(Boolean.FALSE);
            employeeRepository.save(employee);
        }
    }

    @InitNameAnnotation
    @Override
    public Employee updateById(Integer id, Employee employee) {
        log.info("updateById(Integer id, Employee employee) Service Start id - {}, employee - {}", id, employee);
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (isFieldNew(employee.getName(), entity.getName())) {
                        entity.setName(employee.getName());
                    }
                    if (isFieldNew(employee.getEmail(), entity.getEmail())) {
                        entity.setEmail(employee.getEmail());
                    }
                    if (isFieldNew(employee.getCountry(), entity.getCountry())) {
                        entity.setCountry(employee.getCountry());
                    }
                    if (isFieldNew(employee.getIsFired(), entity.getIsFired())) {
                        entity.setIsFired(employee.getIsFired());
                    }
                    if (isFieldNew(employee.getPhotos(), entity.getPhotos())) {
                        entity.getPhotos().addAll(employee.getPhotos());
                    }
                    log.info("updateById(Integer id, Employee employee) Service end - entity - {}", entity);
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);
        //employee.setIsDeleted(true);
        employeeRepository.delete(employee);
        //repository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }


    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContainingAndIsFiredIsFalse(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAllByIsFiredIsFalseOrIsFiredIsNull();
        List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                .collect(Collectors.toList());
        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAllByIsFiredIsFalseOrIsFiredIsNull();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAllByIsFiredIsFalseOrIsFiredIsNull();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.of(opt);
    }

    @Override
    public List<Employee> getByExpiredPhotos() {
        List<Employee> allEmployeeList = getAll();
        LocalDate fiveYearsOld = LocalDate.now().minusYears(5).plusWeeks(1);
        List<Employee> expiredPhotoEmployees = new ArrayList<>();
        for (Employee employee : allEmployeeList) {
            for (Photo photos : employee.getPhotos()) {
                if (fiveYearsOld.isAfter(photos.getAddDate())) {
                    expiredPhotoEmployees.add(employee);
                }
            }
        }
        return expiredPhotoEmployees;
    }

    @Override
    public void sendMailToUsersWithExpiredPhotos() {
        List<Employee> expiredPhotoEmployees = getByExpiredPhotos();
        expiredPhotoEmployees.forEach((employee) -> emailService.sendMessage(employee.getEmail(),
                "Expired photo", "Please update your photo"));
    }

    @Override
    public Employee addPassportToEmployee(Integer employeeId) {
        log.info("addPassportToEmployee(Integer employeeId) - start:");
        Passport passport = passportService.getFree();
        Employee employeeToUpdate = employeeRepository.findById(employeeId).map((employee) -> {
                    passport.setEmployee(employee);
                    passport.setName(employee.getName());
                    passport.setDateOfBirthday(employee.getDateOfBirthday());
                    passport.setIsFree(Boolean.FALSE);
                    passport.setPassportStatus(PassportStatus.ACTIVE);
                    passport.setExpireDate(LocalDate.now().plusYears(5));
                    employee.setPassport(passport);
                    passportService.create(passport);
                    return employee;
                })
                .orElseThrow(ResourceNotFoundException::new);
        log.info("addPassportToEmployee(Integer employeeId) - end: employee = {}", employeeToUpdate);
        return employeeRepository.save(employeeToUpdate);
    }

    public Employee updatePassport(Integer employeeId, PassportStatus reason) {
        log.info("updatePassport(Integer employeeId, PassportStatus reason) - start:");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(ResourceNotFoundException::new);
        Passport newPassport = passportService.replacePassport(employee.getPassport(), reason);
        employee.setPassport(newPassport);
        log.info("updatePassport(Integer employeeId, PassportStatus reason) - end: employee = {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deletePassportFromEmployee(Integer id) {
        log.info("deletePassportFromEmployee(Integer id) - start:");
        Employee employeeToUpdate = getById(id);
        passportService.removeById(employeeToUpdate.getPassport().getId());
        employeeToUpdate.setPassport(null);
        log.info("deletePassportFromEmployee(Integer id) - end: employee = {}", employeeToUpdate);
        employeeRepository.save(employeeToUpdate);
    }

    @Override
    public Employee addEmployeeToCabinet(Integer employeeId, Integer cabinetId) {
        log.info("addEmployeeToCabinet(Integer employeeId, Integer cabinetId) - start:");
        Employee employee = getById(employeeId);
        Cabinet cabinet = cabinetService.readById(cabinetId);
        employeesCabinetsService.createRelation(employee, cabinet);
        Employee result = getById(employeeId);
        log.info("addEmployeeToCabinet(Integer employeeId, Integer cabinetId) - end: result = {}", result);
        return result;
    }

    @Override
    public void removeEmployeeFromCabinet(Integer employeeId, Integer cabinetId) {
        employeesCabinetsService.deactivateRelation(employeeId, cabinetId);
    }
}
