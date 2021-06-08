package ru.DomofonMaster.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CityEntity {
    @Id
    private Long id_city;

    private String name_city;

    public Long getId_city() {
        return id_city;
    }

    public void setId_city(Long id_city) {
        this.id_city = id_city;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity cityEntity = (CityEntity) o;
        return Objects.equals(id_city, cityEntity.id_city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_city);
    }
}
