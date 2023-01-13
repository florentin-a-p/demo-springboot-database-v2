package com.countries.database.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="springboot_country_continent_tbl")
@Data
public class CountryContinent {
  @Id
  @Column(name="id")
  private int id;

  @Column(name="countryid")
  private int countryId;

  @Column(name="countrycontinent")
  private String countryContinent;

  @Column(name="countryregion")
  private String countryRegion;
}
