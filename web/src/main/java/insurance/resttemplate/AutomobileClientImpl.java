package insurance.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.agency.domains.Automobile;

@Service
public class AutomobileClientImpl implements AutomobileClient {

    private final RestTemplate restTemplate;

    private final String SERVICE_AGENCY_SERVICE_AUTOMOBILE = "http://localhost:8081/?personalNo={personalNo}&licenseNumber={licenseNumber}";

    @Autowired
    public AutomobileClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Automobile getByLicenseNumberAndOwnerPersonalNo(String licenseNumber, String personalNo) {
        return restTemplate.getForObject(
                SERVICE_AGENCY_SERVICE_AUTOMOBILE
                , Automobile.class
                , personalNo
                , licenseNumber);
    }
}
