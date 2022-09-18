package insurance.domains;


import insurance.enums.OdometerUnit;
import lombok.*;
import service.agency.domains.Automobile;
import service.agency.enums.AutoType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Month;
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
    @Column(name = "odometer_unit", nullable = false, length = 7)
    private OdometerUnit odometerUnit;

    @NotNull
    @PositiveOrZero
    @Column(name = "insurance_amount", nullable = false, precision = 12,scale = 2)
    private int insuranceAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "insurance_pension", nullable = false, precision = 12,scale = 2)
    private int insurancePension;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "personal_no")
    private Debtor debtor;

    public CollateralAuto(Automobile automobile, int odometer, OdometerUnit odometerUnit,int insuranceAmount){
        vinCode=automobile.getVinCode();
        manufactureYear=automobile.getManufactureYear();
        autoType=automobile.getAutoType();
        licenseNumber= automobile.getLicenseNumber();;
        this.odometer=odometer;
        this.odometerUnit=odometerUnit;
        this.insuranceAmount=insuranceAmount;
        this.insurancePension=calculateInsurancePension();
        debtor=new Debtor(automobile.getOwner());
    }

    private int calculateInsurancePension(){

    }

}
