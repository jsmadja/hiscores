package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseModel<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        if(createdAt == null) {
            createdAt = new Date();
        }
        updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return !(id != null ? !id.equals(baseModel.id) : baseModel.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
