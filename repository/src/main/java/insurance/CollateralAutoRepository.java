package insurance;

import insurance.domains.CollateralAuto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollateralAutoRepository extends PagingAndSortingRepository<CollateralAuto, String> {
}
