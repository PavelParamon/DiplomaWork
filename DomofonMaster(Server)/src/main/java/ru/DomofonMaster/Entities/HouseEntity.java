package ru.DomofonMaster.Entities;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class HouseEntity {
    @Id
    private Long id_house;

    private int number;
    private String notes;
    private Long street_id;

    public Long getId_house() {
        return id_house;
    }

    public void setId_house(Long id_house) {
        this.id_house = id_house;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getStreet_id() {
        return street_id;
    }

    public void setStreet_id(Long street_id) {
        this.street_id = street_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseEntity houseEntity = (HouseEntity) o;
        return Objects.equals(id_house, houseEntity.id_house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_house);
    }
}
