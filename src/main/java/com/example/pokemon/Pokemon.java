package com.example.pokemon;


import jakarta.annotation.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pokemon")
public class Pokemon {

  @Id
  @GeneratedValue()
  private Integer id;
  private String name;
  private String power;
  private String imgUrl;

  public Pokemon() {}

  public Pokemon(Integer id, String name, String power, String imgUrl) {
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

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
