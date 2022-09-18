package insurance;

import insurance.enums.OdometerUnit;
import ministry.of.justice.exceptions.EntityAlreadyExistsException;
import ministry.of.justice.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import insurance.domains.CollateralAuto;
import service.agency.domains.Automobile;
import service.agency.domains.Owner;

import javax.persistence.EntityNotFoundException;
import java.time.Month;
import java.time.Year;

@Service
public class CollateralAutoService {
    private final CollateralAutoRepository repo;

    @Autowired
    public CollateralAutoService(CollateralAutoRepository repo) {
        this.repo = repo;
    }


    public CollateralAuto getCollateralAuto(final String vin) {
        return repo.findById(vin)
                .orElseThrow(() -> new EntityNotFoundException(CollateralAuto.class.getName() + "with vin: " + vin + " not found"));
    }

    public Page<CollateralAuto> getCollateralAutos(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public CollateralAuto addCollateralAuto(Automobile automobile, int odometer, OdometerUnit odometerUnit,int insuranceAmount) {
        if (repo.existsById(automobile.getVinCode())) {
           throw new EntityAlreadyExistsException(CollateralAuto.class.getName() + "with vin: " + automobile.getVinCode() + " already Exists");
            } else {
            CollateralAuto collateralAuto =new CollateralAuto(automobile,odometer,odometerUnit,insuranceAmount);
            return repo.save(collateralAuto);
        }
    }



    public CollateralAuto updateCollateralAuto(final String vin, final CollateralAuto auto) {
        if (repo.existsById(vin)) {
            auto.setVinCode(vin);
            return repo.save(auto);
        } else {
            throw new EntityNotFoundException(CollateralAuto.class.getName() + "with vin: " + vin + " not found");
        }
    }

    public void removeCollateralAutoById(final String vin) {
        repo.deleteById(vin);
    }



}
