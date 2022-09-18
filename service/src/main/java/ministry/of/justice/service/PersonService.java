package ministry.of.justice.service;

import ministry.of.justice.exceptions.EntityAlreadyExistsException;
import ministry.of.justice.exceptions.EntityNotFoundException;
import ministry.of.justice.model.person.Person;
import ministry.of.justice.repsitory.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class PersonService {
    private final PersonRepository repo;

    @Autowired
    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public Page<Person> getPersons(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Person findByPersonalNo(final String personalNo) {
        return repo.findById(personalNo)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getName() + "with personal no : " + personalNo + " not found"));
    }

    public Person addPerson(final Person person) {
        if (repo.existsById(person.getPersonalNo())) {
            throw new EntityAlreadyExistsException(Person.class.getName() + "personal no : " + person.getPersonalNo() + " already exists");
        }
        return repo.save(person);
    }

    public Person updatePerson(final String personalNo, final Person person) {
        if (repo.existsById(personalNo)) {
            person.setPersonalNo(personalNo);
            return repo.save(person);
        } else {
            throw new EntityNotFoundException(Person.class.getName() + "personal no : " + personalNo + " not found");
        }
    }

    public void removePersonByPersonalNo(final String personalNo) {
        repo.deleteById(personalNo);
    }


}
