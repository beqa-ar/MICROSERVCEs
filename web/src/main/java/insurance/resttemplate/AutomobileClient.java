package insurance.resttemplate;

import service.agency.domains.Automobile;

public interface AutomobileClient {
    Automobile getByLicenseNumberAndOwnerPersonalNo(String licenseNumber, String personalNo);
}
