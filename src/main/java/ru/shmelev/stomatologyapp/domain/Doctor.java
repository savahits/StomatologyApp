package ru.shmelev.stomatologyapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    private String patronymic;

    @NotBlank(message = "Телефон обязателен")
    @Column(nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Override
    public String toString() {
        return "Doctor{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", specialization=" + specialization +
                '}';
    }
}