package service.agency.resttemplate;

import ministry.of.justice.model.person.Person;
import org.springframework.http.ResponseEntity;

public interface PersonClient {
    Person getByPersonalNo(String personalNo);
}
