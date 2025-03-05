package br.com.softwalter.validate_file.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("ip_address")
    private String ipAddress;

    public Person() {
    }

    public Person(String id, String firstName, String lastName, String email, String gender, String ipAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email) && Objects.equals(gender, person.gender) && Objects.equals(ipAddress, person.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, gender, ipAddress);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id='" + id + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", ip_address='" + ipAddress + '\'' +
                '}';
    }
}
