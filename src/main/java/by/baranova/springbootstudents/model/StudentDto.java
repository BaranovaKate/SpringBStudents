package by.baranova.springbootstudents.model;


import by.baranova.springbootstudents.annotation.ContactNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

public class StudentDto implements Serializable {

    private Long id;

    @NotBlank(message = "Имя студента не должно быть пустым")
    @Size(min = 2, max = 16, message = "Имя дожно быть от 2 до 16 символов")
    private String name;

    @NotBlank(message = "Фамилия студента не должна быть пустой")
    @Size(min = 2, max = 16, message = "Фамилия дожна быть от 2 до 16 символов")
    private String lastName;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @ContactNumber(message = "Необходимо указать номер телефона от 9 до 14 цифр")
    private String phoneNumber;

    @NotNull(message = "Необходимо указать дату рождения")
    private LocalDate birthday;

    public StudentDto() {
    }

    public StudentDto(Long id, String name, String lastName, String phoneNumber, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
