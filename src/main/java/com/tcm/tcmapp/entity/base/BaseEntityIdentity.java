package com.tcm.tcmapp.entity.base;


import javax.persistence.*;

@MappedSuperclass
public class BaseEntityIdentity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntityIdentity{" +
                "id=" + id +
                '}';
    }

}