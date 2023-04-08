package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.dto.cabinet.CabinetRequest;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface CabinetControllerSwagger extends CabinetController {
    @Override
    @Operation(summary = "This is endpoint created a cabinet.", description = "Create a cabinet", tags = {"Cabinet"})
    @ApiResponses(value = @ApiResponse(responseCode = "201", description = "CREATED."))
    CabinetResponse createCabinet(CabinetRequest request);

    @Override
    @Operation(summary = "This is endpoint gets a cabinet from id", description = "Show a cabinet from id", tags = {"Cabinet"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    CabinetResponse readCabinet(Integer id);

    @Override
    @Operation(summary = "This is endpoint updates a cabinet", description = "Update a cabinet", tags = {"Cabinet"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    CabinetResponse updateCabinet(Integer id, CabinetRequest request);

    @Override
    @Operation(summary = "This is endpoint deletes a cabinet", description = "Delete a cabinet", tags = {"Cabinet"})
    @ApiResponses(value = @ApiResponse(responseCode = "204", description = "NO_CONTENT."))
    void deleteCabinet(Integer id);
}
