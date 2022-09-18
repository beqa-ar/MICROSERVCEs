package insurance.domains;

import lombok.*;
import service.agency.domains.Owner;

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
public class Debtor {
    @Id
    @EqualsAndHashCode.Include
    @Pattern(regexp = "\\d{11}")
    @Column(name = "personal_no",nullable = false)
    private String personalNo;

    @NotBlank
    @Column(name = "full_name", nullable = false, length = 256)
    private String fullName;

    @NotNull
    @PastOrPresent
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    public Debtor(Owner owner){
        personalNo=owner.getPersonalNo();
        fullName=owner.getFirstName()+" "+owner.getLastName();
        birthDate=owner.getBirthDate();
    }
}
