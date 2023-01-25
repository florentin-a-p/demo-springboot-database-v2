CREATE TABLE springboot_country_tbl (
   id int primary key,
   countryName varchar(255),
   countryCapital varchar(255)
);

CREATE TABLE springboot_country_population_tbl (
   id int primary key,
   countryId int,
   countryPopulation int
);

CREATE TABLE springboot_country_continent_tbl (
   id int primary key,
   countryId int,
   countryContinent varchar(255),
   countryRegion varchar(255)
);
