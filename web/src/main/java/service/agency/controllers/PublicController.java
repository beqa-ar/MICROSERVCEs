package service.agency.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.agency.AutomobileService;
import service.agency.domains.Automobile;
import service.agency.resttemplate.PersonClientImpl;

@RestController
@RequestMapping("automobiles")
public class PublicController {
    private final AutomobileService service;

    @Autowired
    public PublicController(AutomobileService service, PersonClientImpl personClient) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get Automobile by Owners personal no & license number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Automobile", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Automobile with the given params does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Automobile> getAutomobile(@RequestParam final String personalNo,@RequestParam final String licenseNumber) {
        return new ResponseEntity<>(service.getAutomobileByLicenseNumberAndOwnerPersonalNo(licenseNumber,personalNo), HttpStatus.OK);
    }
}
