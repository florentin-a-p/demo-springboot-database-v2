package com.countries.database.demo.repository;

import com.countries.database.demo.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {
  String FILTER_COUNTRY_ON_NAME_AND_CAPITAL = "select b from Country b where UPPER(b.countryName) like CONCAT('%',UPPER(?1),'%') and UPPER(b.capital) like CONCAT('%',UPPER(?2),'%')";

  @Query(FILTER_COUNTRY_ON_NAME_AND_CAPITAL)
  Page<Country> findByCountryIdLikeAndCountryNameLike(String countryName, String capital, Pageable pageable);


  @Query(FILTER_COUNTRY_ON_NAME_AND_CAPITAL)
  List<Country> findByCountryIdLikeAndCountryNameLike(String countryName, String capital);

}
