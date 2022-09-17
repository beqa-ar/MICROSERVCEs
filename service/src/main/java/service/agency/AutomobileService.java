package service.agency;

import ministry.of.justice.exceptions.EntityAlreadyExistsException;
import ministry.of.justice.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.agency.domains.Automobile;
import service.agency.enums.AutoType;

import javax.persistence.EntityNotFoundException;
import java.time.Month;
import java.time.Year;

@Service
public class AutomobileService {
    private final AutomobileRepository repo;


    @Autowired
    public AutomobileService(AutomobileRepository repo) {
        this.repo = repo;
    }

    public Automobile getAutomobile(final String vin) {
        return repo.findById(vin)
                .orElseThrow(() -> new EntityNotFoundException(Automobile.class.getName() + "with vin: " + vin + " not found"));
    }

    public Page<Automobile> getAutomobiles(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Automobile registerAutomobile(Person owner, String licenseNumber, String vin, Month manufactureMonth, Year manufactureYear, AutoType type) {
        if (repo.existsById(vin)) {
            repo.findById(vin).ifPresent(automobile ->{
                automobile.setOwner(owner);
                automobile.setLicenseNumber(licenseNumber);
                automobile.setVinCode(vin);
                automobile.setManufactureMonth(manufactureMonth);
                automobile.setManufactureYear(manufactureYear);
                automobile.setAutoType(type);
            });
            return repo.findById(vin).get();
        }else {
            Automobile automobile =new Automobile(vin,manufactureYear,manufactureMonth,type,licenseNumber, owner);
            return repo.save(automobile);
        }
    }

    public Automobile getAutomobileByLicenseNumberAndOwnerPersonalNo(final String licenseNumber,final String personalNo){
        return repo.findByLicenseNumberAndOwner_PersonalNo(licenseNumber,personalNo)
                .orElseThrow(() -> new EntityNotFoundException(Automobile.class.getName() + "with licenseNumber: " + licenseNumber +"and with  Owner's: " + personalNo+ " not found"));
    }


    public Automobile updateAutomobile(final String vin, final Automobile auto) {
        if (repo.existsById(vin)) {
            auto.setVinCode(vin);
            return repo.save(auto);
        } else {
            throw new EntityNotFoundException(Automobile.class.getName() + "with vin: " + vin + " not found");
        }
    }

    public void removeAutomobileById(final String vin) {
        repo.deleteById(vin);
    }

}
