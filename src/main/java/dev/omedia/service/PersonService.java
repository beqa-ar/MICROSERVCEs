package dev.omedia.service;

import dev.omedia.exception.EntityAlreadyExistsException;
import dev.omedia.exception.EntityNotFoundException;
import dev.omedia.model.Person;
import dev.omedia.repsitory.PersonRepository;
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

    @Cacheable(cacheNames = "person", key = "#personalNo")
    public Person findByPersonalNo(final String personalNo) {
        return repo.findById(personalNo)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getName() + "with personal no : " + personalNo + " not found"));
    }

    @CachePut(cacheNames = "person", key = "#person.personalNo")
    public Person addPerson(final Person person) {
        if (repo.existsById(person.getPersonalNo())) {
            throw new EntityAlreadyExistsException(Person.class.getName() + "personal no : " + person.getPersonalNo() + " already exists");
        }
        return repo.save(person);
    }

    @CachePut(cacheNames = "person", key = "#personalNo")
    public Person updatePerson(final String personalNo, final Person person) {
        if (repo.existsById(personalNo)) {
            person.setPersonalNo(personalNo);
            return repo.save(person);
        } else {
            throw new EntityNotFoundException(Person.class.getName() + "personal no : " + personalNo + " not found");
        }
    }

    @CacheEvict(cacheNames = "person", key = "#personalNo")
    public void removePersonByPersonalNo(final String personalNo) {
        repo.deleteById(personalNo);
    }


}
