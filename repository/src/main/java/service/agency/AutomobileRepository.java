package service.agency;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import service.agency.domains.Automobile;

import java.util.Optional;

@Repository
public interface AutomobileRepository extends PagingAndSortingRepository<Automobile,String> {
    Optional<Automobile> findByLicenseNumberAndOwner_PersonalNo(String licenseNumber,String personalNo);
}
