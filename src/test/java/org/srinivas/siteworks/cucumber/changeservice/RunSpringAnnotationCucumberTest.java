package org.srinivas.siteworks.cucumber.changeservice;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin= {"pretty", "json:target/cucumber"},
		features = {"classpath:functionalChangeService.feature"}
		)
public class RunSpringAnnotationCucumberTest {
	
}
