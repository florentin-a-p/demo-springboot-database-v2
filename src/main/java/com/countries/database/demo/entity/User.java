package com.countries.database.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "springboot_user_tbl")
public class User {
  @Id
  private int id;
  private String userName;
  private String password;
  private String email;
}
