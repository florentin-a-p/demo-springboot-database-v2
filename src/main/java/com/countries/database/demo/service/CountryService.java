package com.countries.database.demo.service;

import com.countries.database.demo.entity.Country;
import com.countries.database.demo.entity.ResponseMessage;
import com.countries.database.demo.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CountryService {
  @Autowired(required = false)
  CountryRepository countryRepository;
  public List<Country> getCountries() {
    return (List<Country>) countryRepository.findAll();
  }

  public Country getCountriesWithId(Integer countryId) {
    return countryRepository.findById(countryId).get();
  }

  public Country getCountriesWithName(String countryName) {
    List<Country> countryList = (List<Country>) countryRepository.findAll();
    for (Country country:countryList) {
      if (countryName.equals(country.getCountryName())) {
        return country;
      }
    }
    return new Country(0,"NONE","NONE");
  }

  public Page<Country> getCountriesAsPageWithFiltering (String countryName, String capital, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return countryRepository.findByCountryIdLikeAndCountryNameLike(countryName, capital, pageable);
  }

  public Page<Country> getCountriesAsPageWithFilteringAndSorting(String countryName, String capital, int page, int size, List<String> sortList, String sortOrder) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
    return countryRepository.findByCountryIdLikeAndCountryNameLike(countryName, capital, pageable);
  }

  public Country addCountry(Country addedCountry) {
    long size = countryRepository.count();
    log.info("[FLO] REPOSITORY SIZE IS: " + size);
    Integer newId = (int) size + 1;
    log.info("[FLO] newId IS: " + newId);
    Country country = new Country(newId, addedCountry.getCountryName(), addedCountry.getCapital());
    log.info("[FLO] newly added country is: " + country.getId() + country.getCountryName() + country.getCapital());
    countryRepository.save(country);
    log.info("[FLO] newly added country in repository is: " + countryRepository.findById(newId).get());
    return countryRepository.findById(newId).get();
  }

  public Country updateCountry(Country updatedCountry) {
    countryRepository.save(updatedCountry);
    return countryRepository.findById(updatedCountry.getId()).get();
  }

  public ResponseMessage deleteCountry(Integer countryId) {
    countryRepository.deleteById(countryId);
    return new ResponseMessage("Country deleted");
  }

  private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
    List<Sort.Order> sorts = new ArrayList<>();
    Sort.Direction direction;
    for (String sort : sortList) {
      if (sortDirection != null) {
        direction = Sort.Direction.fromString(sortDirection);
      } else {
        direction = Sort.Direction.DESC;
      }
      sorts.add(new Sort.Order(direction, sort));
    }
    return sorts;
  }
}
