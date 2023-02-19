package com.example.pokemon;


import com.example.power.Power;

import javax.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "power_id")
    private Power power;
    private String imgUrl;

    public Pokemon() {
    }

    public Pokemon(Integer id, String name, Power power, String imgUrl) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.imgUrl = imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
