package service.agency.domains;


import lombok.*;
import ministry.of.justice.model.person.Person;
import service.agency.enums.AutoType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Month;
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
    @Column(name = "manufacture_month", nullable = false)
    private Month manufactureMonth;


    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "auto_type", nullable = false)
    private AutoType autoType;


    @NotNull
    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    @Column(name = "license_number", nullable = false, length = 7)
    private String licenseNumber;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name ="personal_no")
    private Owner owner;

}
