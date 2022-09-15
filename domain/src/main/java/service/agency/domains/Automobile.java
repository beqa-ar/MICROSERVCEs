package service.agency.domains;


import lombok.*;
import service.agency.enums.AutoType;
import service.agency.enums.OdometerUnit;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.Year;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Automobiles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Automobile {

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
    @Digits(integer = 6, fraction = 0)
    @Column(name = "odometer", nullable = false,  precision = 6)
    private int odometer;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "odometer_unit", nullable = false)
    private OdometerUnit odometerUnit;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    @Column(name = "license_number", nullable = false, length = 7)
    private String licenseNumber;



    @ManyToOne
    @JoinColumn(name ="owner_id")
    private Owner owner;
}
