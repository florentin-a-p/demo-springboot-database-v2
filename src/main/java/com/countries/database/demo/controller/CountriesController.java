package com.countries.database.demo.controller;

import com.countries.database.demo.entity.Country;
import com.countries.database.demo.entity.ResponseMessage;
import com.countries.database.demo.exception.EmptyTableException;
import com.countries.database.demo.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CountriesController {
  @Autowired(required = false)
  CountryService countryService;

  // start GET methods //
  @Operation(summary = "Get all countries")
  @GetMapping(path="/v0/getCountries")
  public ResponseEntity<List<Country>> getCountries() {
    log.info("[FLO] /getCountries is called");
    try {
      ResponseEntity<List<Country>> response = new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
      log.info("[FLO] response length is: "+response.getBody().size());

      if (response.getBody().size() < 1) {
        throw new EmptyTableException("Data doesn't exist in table!!!");
      } else {
        return response;
      }
    } catch (InvalidDataAccessResourceUsageException e) { //db exists, tbl doesn't exist
      log.info("[FLO] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.GONE);
    } catch (CannotCreateTransactionException e) { //during springboot run, db is deleted
      log.info("[FLO] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    } catch (EmptyTableException e) { //tbl exists but is empty
      log.info("[FLO] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) { //any other exceptions
      log.info("[FLO] exception message is: "+e);
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  @GetMapping(path="/v0/getCountries/{countryId}")
  public ResponseEntity<Country> getCountriesWithId(@PathVariable Integer countryId) {
    log.info("[FLO] /getCountries/{countryId} is called");
    try {
      return new ResponseEntity<>(countryService.getCountriesWithId(countryId), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(path="/v0/getCountries/countryName")
  public ResponseEntity<Country> getCountriesWithName(@RequestParam(value="name") String countryName) {
    log.info("[FLO] /getCountries/countryName is called");
    try {
      return new ResponseEntity<>(countryService.getCountriesWithName(countryName), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * @param countryName     Filter for countryName if required
   * @param capital          Filter for id if required
   * @param page            number of the page returned
   * @param size            number of entries in each page
   * @return Page object with customers after filtering
   */
  @GetMapping(path="/v1/getCountries")
  public Page<Country> getCountriesWithPageInterface(@RequestParam(defaultValue = "") String countryName,
                                                     @RequestParam(defaultValue = "") String capital,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "30") int size) {
    log.info("[FLO] /v1/getCountries is called");
    return countryService.getCountriesAsPageWithFiltering(countryName, capital, page, size);
  }

  /**
   * @param countryName     Filter for countryName if required
   * @param capital         Filter for id if required
   * @param page            number of the page returned
   * @param size            number of entries in each page
   * @param sortList        list of columns to sort on
   * @param sortOrder       sort order. Can be ASC or DESC
   * @return Page object with customers after filtering
   */
  @GetMapping(path="/v2/getCountries")
  public Page<Country> getCountriesWithPageInterfaceAndSorted(@RequestParam(defaultValue = "") String countryName,
                                                              @RequestParam(defaultValue = "") String capital,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "30") int size,
                                                              @RequestParam(defaultValue = "") List<String> sortList,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
    log.info("[FLO] /v2/getCountries is called");
    return countryService.getCountriesAsPageWithFilteringAndSorting(countryName, capital, page, size, sortList, sortOrder.toString());
  }
  // end GET methods //

  // start non GET methods //
  @PostMapping(path="addCountry")
  public ResponseEntity<Country> addCountry(@RequestBody Country addedCountry) {
    log.info("[FLO] /addCountry is called");
    try {
      return new ResponseEntity<>(countryService.addCountry(addedCountry), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "Update existing country")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated the country",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = Country.class)) })})
  @PutMapping(path="updateCountry")
  public ResponseEntity<Country> updateCountry(@RequestBody Country updatedCountry) {
    log.info("[FLO] /updateCountry is called");
    try {
      return new ResponseEntity<>(countryService.updateCountry(updatedCountry), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "Delete a country by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted the country",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = Country.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Country not found",
          content = @Content) })
  @DeleteMapping(path="deleteCountry/{countryId}")
  public ResponseEntity<ResponseMessage> deleteCountry(@Parameter(description = "id of country to be deleted") @PathVariable Integer countryId) {
    log.info("[FLO] deleteCountry/{countryId} is called");
    try {
      return new ResponseEntity<>(countryService.deleteCountry(countryId), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
  // end non GET methods //
}
