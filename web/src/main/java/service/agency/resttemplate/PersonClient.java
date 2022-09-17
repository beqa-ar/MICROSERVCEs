package service.agency.resttemplate;

import ministry.of.justice.model.person.Person;

public interface PersonClient {
    Person getByPersonalNo(String personalNo);
}
