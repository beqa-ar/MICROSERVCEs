package dev.omedia.controller;

import dev.omedia.model.Person;
import dev.omedia.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class PublicController {
    private final PersonService service;

    @Autowired
    public PublicController(PersonService service) {
        this.service = service;
    }

    @GetMapping("{personalNo}")
    @Operation(summary = "Get Person by personalNo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Person by personalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Person with the given personalNo does not exist",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Person> getPerson(@Valid  @PathVariable String personalNo) {
        return new ResponseEntity<>(service.findByPersonalNo(personalNo), HttpStatus.OK);
    }
}
