package insurance.domains;


import insurance.enums.LoanType;
import insurance.enums.OdometerUnit;
import lombok.*;
import service.agency.domains.Automobile;
import service.agency.enums.AutoType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collateral_auto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CollateralAuto {

    @Id
    @Size(min = 17, max = 17)
    @EqualsAndHashCode.Include
    @Column(name = "vin", nullable = false, length = 17)
    private String vinCode;

    @NotNull
    @PastOrPresent
    @Column(name = "manufacture_year", nullable = false)
    private Year manufactureYear;


    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "auto_type", nullable = false)
    private AutoType autoType;


    @NotNull
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    @Column(name = "license_number", nullable = false, length = 7)
    private String licenseNumber;

    @NotNull
    @PositiveOrZero
    @Column(name = "odometer", nullable = false, length = 6)
    private int odometer;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "odometer_unit", nullable = false)
    private OdometerUnit odometerUnit;

    @NotNull
    @PositiveOrZero
    @Column(name = "insurance_amount", nullable = false, precision = 12, scale = 2)
    private double insuranceAmount;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @PositiveOrZero
    @Column(name = "franchise_amount", precision = 12, scale = 2)
    private double franchiseAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "insurance_pension", nullable = false, precision = 12, scale = 2)
    private double insurancePension;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "personal_no")
    private Debtor debtor;

    public CollateralAuto(Automobile automobile, int odometer, OdometerUnit odometerUnit, double insuranceAmount) {
        vinCode = automobile.getVinCode();
        manufactureYear = automobile.getManufactureYear();
        autoType = automobile.getAutoType();
        licenseNumber = automobile.getLicenseNumber();
        this.odometer = odometer;
        this.odometerUnit = odometerUnit;
        this.insuranceAmount = insuranceAmount;
        this.insurancePension = calculateInsurancePension();
        debtor = new Debtor(automobile.getOwner());
    }

    private double calculateInsurancePension() {
        double amount = insuranceAmount * 0.03;

        int range = Year.now().getValue() - manufactureYear.getValue();
        if (range > 9 && range < 25) {
            amount += insuranceAmount * 0.01;
        }

        int age = Period.between(debtor.getBirthDate(), LocalDate.now()).getYears();
        if (age > 17 && age < 24) {
            amount += insuranceAmount * 0.01;
        }

        if(loanType.equals(LoanType.FRANCHISE)){
            amount-=franchiseAmount*0.1;
        }else {
            amount+=insuranceAmount * 0.01;
        }
        return amount;
    }

}
