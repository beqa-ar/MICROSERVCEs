package dev.omedia.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    @Column(name = "owner_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_id_seq")
    @SequenceGenerator(name = "owner_id_gen", sequenceName = "owner_id_seq", allocationSize = 1)
    private long id;

    @NotBlank
    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @NotNull
    @PastOrPresent
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
}
