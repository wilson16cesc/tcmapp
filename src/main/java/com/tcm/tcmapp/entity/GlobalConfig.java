package com.tcm.tcmapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "glb_global_config")
public class GlobalConfig extends BaseEntity {

    public GlobalConfig() {
    }

    public GlobalConfig(String id, String textValue, Long integerValue) {
        this.id = id;
        this.textValue = textValue;
        this.integerValue = integerValue;
    }

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "text_value", length = 5000)
    private String textValue;

    @Column(name = "integer_value")
    private Long integerValue;

    public Long getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Long integerValue) {
        this.integerValue = integerValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalConfig that = (GlobalConfig) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "id='" + id + '\'' +
                ", textValue='" + textValue + '\'' +
                ", integerValue=" + integerValue +
                '}';
    }
}