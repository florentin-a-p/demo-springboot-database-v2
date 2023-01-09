package com.countries.database.demo.repository;

import com.countries.database.demo.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {
}
