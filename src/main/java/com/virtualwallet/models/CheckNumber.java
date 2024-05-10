package com.virtualwallet.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cvv_numbers")
public class CheckNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cvv_number_id")
    int id;
    @Column(name = "cvv")
    String cvvNumber;

    public CheckNumber() {
    }

    public CheckNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCvv() {
        return cvvNumber;
    }

    public void setCvv(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckNumber checkNumber)) return false;
        return getId() == checkNumber.getId() && Objects.equals(getCvv(), checkNumber.getCvv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCvv());
    }
}
