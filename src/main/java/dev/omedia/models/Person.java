package dev.omedia.models;

import lombok.*;

import javax.persistence.*;
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
@Table(name = "persons")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {


    @Id
    @EqualsAndHashCode.Include
    @Pattern(regexp = "\\d{11}")
    @Column(name = "personal_no",nullable = false)
    private long personalNo;

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
}
