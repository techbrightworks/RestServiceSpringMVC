package org.srinivas.siteworks.calculate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.denomination.Coin;


public interface Calculate {
	public static final Logger log = LoggerFactory.getLogger(Calculate.class);
 /**
  * Calculate.
  *
  * @param pence the pence
  * @param denominations the denominations
  * @return the list
  * @throws Exception the exception
  */
 public List<Coin> calculate(Integer pence,Integer[] denominations)throws Exception;
 
 default void logIfCalculateUsed(){
	 log.info("---Inside calculate() Method--");
 }
 
}
