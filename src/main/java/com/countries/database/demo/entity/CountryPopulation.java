package com.countries.database.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="springboot_country_population_tbl")
@Data
public class CountryPopulation {
  @Id
  @Column(name="id")
  private int id;

  @Column(name="countryid")
  private int countryId;

  @Column(name="countrypopulation")
  private int countryPopulation;
}
