package com.example.demowithtests.web.passportController;

import com.example.demowithtests.dto.passport.PassportRequest;
import com.example.demowithtests.dto.passport.PassportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface PassportControllerSwagger extends PassportController {

    @Override
    @Operation(summary = "This is endpoint creates a passport.", description = "Create a passport", tags = {"Passport"})
    @ApiResponses(value = @ApiResponse(responseCode = "201", description = "CREATED."))
    PassportResponse createPass(PassportRequest request);

    @Override
    @Operation(summary = "This is endpoint read passport by id", description = "Shows passport by id", tags = {"Passport"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    PassportResponse readPass(Integer id);

    @Override
    @Operation(summary = "This is endpoint updates passport by id", description = "Updates passport by id", tags = {"Passport"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    PassportResponse updatePass(Integer id, PassportRequest request);

    @Override
    @Operation(summary = "This is endpoint marked passport as deleted.", description = "Deletes a passport", tags = {"Passport"})
    @ApiResponses(value = @ApiResponse(responseCode = "201", description = "NO CONTENT."))
    void deletePass(Integer id);
}
