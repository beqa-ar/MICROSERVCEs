package service.agency.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ministry.of.justice.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.agency.AutomobileService;
import service.agency.domains.Automobile;
import service.agency.enums.AutoType;
import service.agency.resttemplate.PersonClientImpl;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Month;
import java.time.Year;

@RestController
@RequestMapping
public class AutomobileController {
    private final AutomobileService service;
    private final PersonClientImpl personClient;

    @Autowired
    public AutomobileController(AutomobileService service, PersonClientImpl personClient) {
        this.service = service;
        this.personClient = personClient;
    }

    @GetMapping
    @Operation(summary = "Get Automobiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Automobiles", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Iterable<Automobile>> getAutomobiles(
            @Min(value = 0, message = "incorrect value") @Parameter(description = "page index(start from 0)")
            @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page

            , @Min(30) @Max(500) @Parameter(description = "page size(min 30  max 500)")
            @RequestParam(required = false, defaultValue = "${pageSize}", name = "pageSize") final int pageSize) {

        return new ResponseEntity<>(service.getAutomobiles(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @GetMapping("{vin}")
    @Operation(summary = "Get Automobile by vin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Automobile by vin", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Automobile with the given VIN does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Automobile> getAutomobile(@PathVariable final String vin) {
        return new ResponseEntity<>(service.getAutomobile(vin), HttpStatus.OK);
    }

    @DeleteMapping("{vin}")
    @Operation(summary = "Remove Automobile by vin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Automobile by vin", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Automobile with the given VIN does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Automobile> removeAutomobile(@PathVariable final String vin) {
        service.removeAutomobileById(vin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("people/{personalNo}/vehicles")
    @Operation(summary = "Register Automobile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "add Automobile", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Automobile with the given VIN already exists", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Automobile> registerAutomobile(@PathVariable String personalNo, @RequestParam String licenseNumber
            , @RequestParam String vin, @RequestParam Month manufactureMonth, @RequestParam Year manufactureYear
            , @RequestParam AutoType type) {
        Person owner = personClient.getByPersonalNo(personalNo);
        return new ResponseEntity<>(
                service.registerAutomobile(owner, licenseNumber, vin, manufactureMonth, manufactureYear, type)
                , HttpStatus.CREATED);

    }

    @PutMapping("{vin}")
    @Operation(summary = "Update Automobile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update Automobile", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Automobile with the given VIN dose not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Automobile> updateAutomobile(@PathVariable final String vin, @Valid @RequestBody final Automobile automobile) {
        return new ResponseEntity<>(service.updateAutomobile(vin, automobile), HttpStatus.OK);
    }
}
