package org.srinivas.siteworks.cucumber.changeservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.srinivas.siteworks.calculate.Calculate;
import org.srinivas.siteworks.calculate.SupplyCalculator;
import org.srinivas.siteworks.changeservice.ChangeServiceImpl;
import org.srinivas.siteworks.config.AppConfig;
import org.srinivas.siteworks.data.CoinsInventoryData;
import org.srinivas.siteworks.data.PropertiesReadWriter;
import org.srinivas.siteworks.denomination.Coin;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
public class FunctionalChangeserviceDefinition {

	private SupplyCalculator supplyCalculator;
	private ChangeServiceImpl changeServiceImpl;
	@Autowired
	private Calculate optimalCalculator;
	@Autowired
	PropertiesReadWriter propertiesReadWriter;
	private Integer changeAmount;
	private Collection<Coin> coins;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		supplyCalculator = new SupplyCalculator();
		// propertiesReadWriter = new PropertiesReadWriter();
		supplyCalculator.setPropertiesReadWriter(propertiesReadWriter);
		propertiesReadWriter.setResourceName("test-coin-inventory.properties");
		propertiesReadWriter.writeInventoryData(CoinsInventoryData.getInventoryData());
		changeServiceImpl = new ChangeServiceImpl();
		changeServiceImpl.supplyCalculator = supplyCalculator;
		changeServiceImpl.propertiesReadWriter = propertiesReadWriter;
		changeServiceImpl.optimalCalculator = optimalCalculator;
	}

	@After
	public void tearDown() throws Exception {
		propertiesReadWriter.writeInventoryData(CoinsInventoryData.getInventoryData());
		propertiesReadWriter = null;
		supplyCalculator = null;
		changeServiceImpl = null;
		coins = null;

	}

	@Given("^I provide (\\d+) for change$")
	public void i_provide_for_change(int arg1) throws Exception {
		setChangeAmount(arg1);
	}

	@When("^I call ChangeService for optimal change functional method$")
	public void i_call_ChangeService_for_optimal_change_functional_method() throws Exception {
		propertiesReadWriter.readInventoryData();
		coins = changeServiceImpl.getOptimalChangeFor(getChangeAmount());

	}

	@Then("^I validate the Optimal result$")
	public void i_validate_the_Optimal_result() throws Exception {
		assertTrue(coins.size() == 5);
		assertEquals(5, filterByValue(coins, 100).getCount().intValue());
		assertEquals(1, filterByValue(coins, 50).getCount().intValue());
		assertEquals(1, filterByValue(coins, 20).getCount().intValue());
		assertEquals(1, filterByValue(coins, 5).getCount().intValue());
		assertEquals(1, filterByValue(coins, 1).getCount().intValue());
	}

	@When("^I call ChangeService for supply change functional method$")
	public void i_call_ChangeService_for_supply_change_functional_method() throws Exception {
		propertiesReadWriter.readInventoryData();
		coins = changeServiceImpl.getChangeFor(getChangeAmount());

	}

	@Then("^I validate the Supply result$")
	public void i_validate_the_Supply_result() throws Exception {
		assertTrue(coins.size() == 5);
		assertEquals(11, filterByValue(coins, 100).getCount().intValue());
		assertEquals(24, filterByValue(coins, 50).getCount().intValue());
		assertEquals(59, filterByValue(coins, 10).getCount().intValue());
		assertEquals(1, filterByValue(coins, 5).getCount().intValue());
		assertEquals(1, filterByValue(coins, 1).getCount().intValue());
	}

	public Integer getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Integer changeAmount) {
		this.changeAmount = changeAmount;
	}

	private Coin filterByValue(Collection<Coin> coins, Integer value) {
		return coins.stream().filter(coin -> coin.getValue() == value).findFirst().get();
	}

}
