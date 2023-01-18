package com.countries.database.demo;

public class DemoApplicationJCL {
  public static void main(String[] args) {
    // Apache Commons Logging
    org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("MAIN CLASS");
    log.info("This is an info message");
    log.error("This is an error message");
    log.debug("Here is a debug message");
  }
}
