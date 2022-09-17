package service.agency.domains;

import lombok.*;
import ministry.of.justice.model.person.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owners")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Owner {

    @Id
    @EqualsAndHashCode.Include
    @Pattern(regexp = "\\d{11}")
    @Column(name = "personal_no",nullable = false)
    private String personalNo;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 128)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;

    @NotNull
    @PastOrPresent
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    public Owner(Person person){
        personalNo=person.getPersonalNo();
        firstName=person.getFirstName();
        lastName=person.getLastName();
        birthDate=person.getBirthDate();
    }
}
