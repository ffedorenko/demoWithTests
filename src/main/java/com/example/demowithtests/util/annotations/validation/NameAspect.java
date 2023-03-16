package com.example.demowithtests.util.annotations.validation;

import com.example.demowithtests.domain.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class NameAspect {
    @Pointcut(value = "@annotation(InitNameAnnotation)")
    public void namePointCut() {
    }

    @Before(value = "namePointCut()")
    public void formatName(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Employee) {
                Employee employee = (Employee) arg;
                Field nameField = employee.getClass().getDeclaredField("name");
                if (nameField.isAnnotationPresent(Name.class)) {
                    nameField.setAccessible(true);
                    String nameValue = (String) nameField.get(employee);
                    if (nameValue != null) {
                        String formattedName = nameValue.substring(0, 1).toUpperCase()
                                + nameValue.substring(1).toLowerCase();
                        nameField.set(employee, formattedName);
                    }
                }
            }
        }
    }
}
