package service.agency.resttemplate;

import ministry.of.justice.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonClientImpl implements PersonClient {

    private final RestTemplate restTemplate;

    private final String URI_MINISTRY_OF_JUSTICE_SERVICE_PERSON = "http://localhost:8080/{personalNo}";

    @Autowired
    public PersonClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Person getByPersonalNo(String personalNo) {
        return restTemplate.getForObject(
                URI_MINISTRY_OF_JUSTICE_SERVICE_PERSON
                , Person.class
                , personalNo);
    }
}
