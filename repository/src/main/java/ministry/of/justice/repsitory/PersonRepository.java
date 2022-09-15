package ministry.of.justice.repsitory;

import ministry.of.justice.model.person.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person,String> {
}
