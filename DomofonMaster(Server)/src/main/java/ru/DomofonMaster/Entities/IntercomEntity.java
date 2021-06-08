package ru.DomofonMaster.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class IntercomEntity {
    @Id
    private Long id_intercom;
    private String manufacturer;

    public Long getId_intercom() {
        return id_intercom;
    }

    public void setId_intercom(Long id_intercom) {
        this.id_intercom = id_intercom;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntercomEntity intercomEntity = (IntercomEntity) o;
        return Objects.equals(id_intercom, intercomEntity.id_intercom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_intercom);
    }
}
