package dev.omedia.models;

import dev.omedia.enums.AutoType;
import dev.omedia.enums.OdometerUnit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Year;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "autos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Auto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "auto_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_id_seq")
    @SequenceGenerator(name = "auto_id_gen", sequenceName = "auto_id_seq", allocationSize = 1)
    private long id;

    @NotNull
    @PastOrPresent
    @Column(name = "manufacture_year", nullable = false)
    private Year manufactureYear;

    @NotNull
    @Size(min = 17, max = 17)
    @Column(name = "vin", nullable = false, length = 17)
    private String vinCode;

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


    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Column(name = "insurance_amount", nullable = false, precision = 10, scale = 2)
    private int insuranceAmount;

    @ManyToOne
    @JoinColumn(name ="owner_id")
    private Owner owner;
}
