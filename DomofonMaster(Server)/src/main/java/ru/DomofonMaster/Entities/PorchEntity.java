package ru.DomofonMaster.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PorchEntity {
    @Id
    private Long id_porch;
    private int number;
    private Long house_id;

    public Long getId_porch() {
        return id_porch;
    }

    public void setId_porch(Long id_porch) {
        this.id_porch = id_porch;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(Long house_id) {
        this.house_id = house_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PorchEntity porchEntity = (PorchEntity) o;
        return Objects.equals(id_porch, porchEntity.id_porch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_porch);
    }
}
