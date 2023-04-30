package com.example.demowithtests.webTests;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportStatus;
import com.example.demowithtests.dto.passport.PassportRequest;
import com.example.demowithtests.dto.passport.PassportResponse;
import com.example.demowithtests.service.passport.PassportService;
import com.example.demowithtests.util.config.mapper.PassportMapper;
import com.example.demowithtests.web.passportController.PassportControllerBean;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PassportControllerBean.class)
public class PassportControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PassportService passportService;
    @MockBean
    PassportMapper passportMapper;

    Passport passport;

    @BeforeEach
    void setUp() {
        passport = Passport.builder()
                .id(1)
                .name("Fedir")
                .dateOfBirthday(LocalDate.parse("1999-02-21"))
                .isFree(Boolean.FALSE)
                .passportStatus(PassportStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("POST /api/passes")
    @WithMockUser(roles = "ADMIN")
    public void createTest() throws Exception {
        var response = new PassportResponse();
        response.id = 1;
        response.name = "Fedir";
        var request = new PassportRequest();
        request.name = "Fedir";

        when(passportMapper.toResponseDto(any(Passport.class))).thenReturn(response);
        when(passportMapper.toPassport(any(PassportResponse.class))).thenReturn(passport);
        when(passportMapper.toRequestDto(any(Passport.class))).thenReturn(request);
        when(passportMapper.toPassport(any(PassportRequest.class))).thenReturn(passport);
        when(passportService.create(any(Passport.class))).thenReturn(passport);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/passes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

        verify(passportService).create(any(Passport.class));
    }

    @Test
    @DisplayName("GET /api/passes/{id}")
    @WithMockUser(roles = "USER")
    public void getByIdTest() throws Exception {
        var response = new PassportResponse();
        response.id = 1;
        response.name = "Fedir";

        when(passportService.create(passport)).thenReturn(passport);
        when(passportMapper.toResponseDto(passport)).thenReturn(response);
        when(passportService.getById(1)).thenReturn(passport);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/passes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Fedir")));

        verify(passportService).getById(anyInt());
    }

    @Test
    @DisplayName("PUT /api/passes/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updateByIdTest() throws Exception {
        var response = new PassportResponse();
        response.id = 1;
        response.name = "Fedir";
        var request = new PassportRequest();
        request.name = "Fedir";

        when(passportMapper.toResponseDto(any(Passport.class))).thenReturn(response);
        when(passportMapper.toPassport(any(PassportResponse.class))).thenReturn(passport);
        when(passportMapper.toRequestDto(any(Passport.class))).thenReturn(request);
        when(passportMapper.toPassport(any(PassportRequest.class))).thenReturn(passport);
        when(passportService.updateById(eq(1), any(Passport.class))).thenReturn(passport);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/passes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(passportService).updateById(eq(1), any(Passport.class));
    }

    @Test
    @DisplayName("DELETE /api/passes/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deleteTest() throws Exception {
        doNothing().when(passportService).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/passes/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(passportService).removeById(1);
    }
}
