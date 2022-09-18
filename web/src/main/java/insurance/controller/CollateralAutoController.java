package insurance.controller;

import insurance.CollateralAutoService;
import insurance.domains.CollateralAuto;
import insurance.enums.LoanType;
import insurance.enums.OdometerUnit;
import insurance.resttemplate.AutomobileClientImpl;
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
import service.agency.domains.Automobile;
import service.agency.enums.AutoType;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Month;
import java.time.Year;

@RestController
public class CollateralAutoController {
    private final CollateralAutoService service;
    private final AutomobileClientImpl automobileClient;

    @Autowired
    public CollateralAutoController(CollateralAutoService service, AutomobileClientImpl automobileClient) {
        this.service = service;
        this.automobileClient = automobileClient;
    }

    @GetMapping
    @Operation(summary = "Get CollateralAutos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found CollateralAutos", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Iterable<CollateralAuto>> getCollateralAutos(
            @Min(value = 0, message = "incorrect value") @Parameter(description = "page index(start from 0)")
            @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page

            , @Min(30) @Max(500) @Parameter(description = "page size(min 30  max 500)")
            @RequestParam(required = false, defaultValue = "${pageSize}", name = "pageSize") final int pageSize) {

        return new ResponseEntity<>(service.getCollateralAutos(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }

    @GetMapping("{vin}")
    @Operation(summary = "Get CollateralAuto by vin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found CollateralAuto by vin", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "CollateralAuto with the given VIN does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<CollateralAuto> getCollateralAuto(@PathVariable final String vin) {
        return new ResponseEntity<>(service.getCollateralAuto(vin), HttpStatus.OK);
    }

    @DeleteMapping("{vin}")
    @Operation(summary = "Remove CollateralAuto by vin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete CollateralAuto by vin", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "CollateralAuto with the given VIN does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<CollateralAuto> removeCollateralAuto(@PathVariable final String vin) {
        service.removeCollateralAutoById(vin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "add CollateralAuto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "add CollateralAuto", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "CollateralAuto with the given params already exists", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<CollateralAuto> addCollateralAuto(@RequestParam String personalNo, @RequestParam String licenseNumber
            , @RequestParam int odometer, @RequestParam OdometerUnit odometerUnit, @RequestParam double insuranceAmount
            , @RequestParam LoanType loanType, @RequestParam(required = false) Double franchiseAmount) {
        Automobile automobile = automobileClient.getByLicenseNumberAndOwnerPersonalNo(licenseNumber, personalNo);
        if(automobile==null){
            throw new EntityNotFoundException("CollateralAuto with the given params dont exists");
        }
        return new ResponseEntity<>(
                service.addCollateralAuto(automobile, odometer, odometerUnit, insuranceAmount,loanType,franchiseAmount)
                , HttpStatus.CREATED);

    }

    @PutMapping("{vin}")
    @Operation(summary = "Update CollateralAuto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update CollateralAuto", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "CollateralAuto with the given VIN dose not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<CollateralAuto> updateCollateralAuto(@PathVariable final String vin, @Valid @RequestBody final CollateralAuto automobile) {
        return new ResponseEntity<>(service.updateCollateralAuto(vin, automobile), HttpStatus.OK);
    }

}
