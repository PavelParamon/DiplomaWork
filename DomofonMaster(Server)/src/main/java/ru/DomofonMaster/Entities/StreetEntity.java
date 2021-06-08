package ru.DomofonMaster.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class StreetEntity {
    @Id
    private Long id_street;
    private String name_street;
    private Long city_id;

    public Long getId_street() {
        return id_street;
    }

    public void setId_street(Long id_street) {
        this.id_street = id_street;
    }

    public String getName_street() {
        return name_street;
    }

    public void setName_street(String name_street) {
        this.name_street = name_street;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetEntity streetEntity = (StreetEntity) o;
        return Objects.equals(id_street, streetEntity.id_street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_street);
    }
}
