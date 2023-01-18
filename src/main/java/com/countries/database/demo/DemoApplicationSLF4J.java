package com.countries.database.demo;

public class DemoApplicationSLF4J {
  public static void main(String[] args) {
    // SLF4J
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("MAIN CLASS");
    logger.info("This is an info message");
    logger.error("This is an error message");
    logger.debug("Here is a debug message"); //  you do not need 'logger.isDebugEnabled' checks anymore. SLF4J will handle that for you).
  }
}
