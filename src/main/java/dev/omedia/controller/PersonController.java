package dev.omedia.controller;

import dev.omedia.model.Person;
import dev.omedia.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("persons")
public class PersonController {
    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get Persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Persons", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Iterable<Person>> getPersons(
            @Min(value = 0, message = "incorrect value") @Parameter(description = "page index(start from 0)")
            @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page

            , @Min(30) @Max(500) @Parameter(description = "page size(min 30  max 500)")
            @RequestParam(required = false, defaultValue = "${pageSize}", name = "pageSize") final int pageSize) {

        return new ResponseEntity<>(service.getPersons(PageRequest.of(page, pageSize)), HttpStatus.OK);
    }


    @DeleteMapping("{personalNo}")
    @Operation(summary = "Remove Person by personalNo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete Person by personalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Person with the given personalNo does not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Person> removePerson(@Valid @PathVariable String personalNo) {
        service.removePersonByPersonalNo(personalNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "add Person", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Person with the given personalNo already exists", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Person> addPerson(@Valid @RequestBody final Person person) {
        System.out.println(person);
        return new ResponseEntity<>(service.addPerson(person), HttpStatus.CREATED);
    }

    @PutMapping("{personalNo}")
    @Operation(summary = "Update Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update Person", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Person with the given personalNo dose not exist", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Person> updatePerson(@Valid @PathVariable final String personalNo, @Valid @RequestBody final Person person) {
        return new ResponseEntity<>(service.updatePerson(personalNo, person), HttpStatus.OK);
    }


}
