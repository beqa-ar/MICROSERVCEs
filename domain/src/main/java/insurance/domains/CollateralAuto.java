package insurance.domains;


import lombok.*;
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

//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "odometer_unit", nullable = false, length = 7)
//    private OdometerUnit odometerUnit;
//
//
//
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "personal_no")
//    private Owner owner;

}
