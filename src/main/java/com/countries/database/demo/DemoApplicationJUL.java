package com.countries.database.demo;


public class DemoApplicationJUL {
  public static void main(String[] args) {
    // java.util.logging
    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger("MAIN CLASS");
    logger.info("This is an info message");
    logger.severe("This is an error message"); // == ERROR
    logger.fine("Here is a debug message"); // == DEBUG
  }
}
