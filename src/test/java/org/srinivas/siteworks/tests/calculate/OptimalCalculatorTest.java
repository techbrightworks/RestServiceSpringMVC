package org.srinivas.siteworks.tests.calculate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.srinivas.siteworks.calculate.Calculate;
import org.srinivas.siteworks.config.AppConfig;
import org.srinivas.siteworks.data.PropertiesReadWriter;
import org.srinivas.siteworks.denomination.Coin;
import org.srinivas.siteworks.tests.data.CoinsInventoryData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class OptimalCalculatorTest {

    @Autowired
    private Calculate optimalCalculator;

    @Autowired
    PropertiesReadWriter propertiesReadWriter;

    private static final String FILE_NAME_TEST_COIN_INVENTORY_PROPERTIES = "test-coin-inventory.properties";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks ( this );
        Path path = Paths.get ( "src", "test", "resources", FILE_NAME_TEST_COIN_INVENTORY_PROPERTIES );
        propertiesReadWriter.setResourceName ( path.toAbsolutePath ().toString () );

    }

    @After
    public void tearDown() throws Exception {
        propertiesReadWriter.writeInventoryData ( CoinsInventoryData.getInventoryData () );
        propertiesReadWriter = null;
        optimalCalculator = null;
    }

    @Test
    public void testCalculate() throws Exception {
        propertiesReadWriter.readInventoryData ();
        List<Coin> coins = optimalCalculator.calculate ( 576, propertiesReadWriter.denominations () );
        assertTrue ( coins.size () == 5 );
        assertEquals ( 5, filterByValue ( coins, 100 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 50 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 20 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 5 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 1 ).getCount ().intValue () );
    }

    @Test
    public void testErrorScenario() throws Exception {
        propertiesReadWriter.readInventoryData ();
        Integer[] error = new Integer[0];
        List<Coin> coins = optimalCalculator.calculate ( 576, error );
        assertTrue ( coins.size () == 0 );
    }


    private Coin filterByValue(List<Coin> coins, Integer value) {
        return coins.stream ().filter ( coin -> coin.getValue () == value ).findFirst ().get ();
    }

}
