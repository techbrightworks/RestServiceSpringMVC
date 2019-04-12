package org.srinivas.siteworks.tests.changeservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.srinivas.siteworks.calculate.Calculate;
import org.srinivas.siteworks.calculate.SupplyCalculator;
import org.srinivas.siteworks.changeservice.ChangeServiceImpl;
import org.srinivas.siteworks.config.AppConfig;
import org.srinivas.siteworks.data.PropertiesReadWriter;
import org.srinivas.siteworks.denomination.Coin;
import org.srinivas.siteworks.tests.data.CoinsInventoryData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ChangeServiceImplTest {

    private static final String FILE_NAME_TEST_COIN_INVENTORY_PROPERTIES = "test-coin-inventory.properties";
    @Autowired
    PropertiesReadWriter propertiesReadWriter;
    private SupplyCalculator supplyCalculator;
    private ChangeServiceImpl changeServiceImpl;
    @Autowired
    private Calculate optimalCalculator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks ( this );
        supplyCalculator = new SupplyCalculator ();
        supplyCalculator.setPropertiesReadWriter ( propertiesReadWriter );
        //propertiesReadWriter.setResourceName("test-coin-inventory.properties");
        Path path = Paths.get ( "src", "test", "resources", FILE_NAME_TEST_COIN_INVENTORY_PROPERTIES );
        propertiesReadWriter.setResourceName ( path.toAbsolutePath ().toString () );
        propertiesReadWriter.writeInventoryData ( CoinsInventoryData.getInventoryData () );
        changeServiceImpl = new ChangeServiceImpl ();
        changeServiceImpl.supplyCalculator = supplyCalculator;
        changeServiceImpl.propertiesReadWriter = propertiesReadWriter;
        changeServiceImpl.optimalCalculator = optimalCalculator;
    }

    @After
    public void tearDown() throws Exception {
        propertiesReadWriter.writeInventoryData ( CoinsInventoryData.getInventoryData () );
        propertiesReadWriter = null;
        supplyCalculator = null;
        changeServiceImpl = null;
    }

    @Test
    public void testGetOptimalChangeFor() throws Exception {
        propertiesReadWriter.readInventoryData ();
        Collection<Coin> coins = changeServiceImpl.getOptimalChangeFor ( 576 );
        assertTrue ( coins.size () == 5 );
        assertEquals ( 5, filterByValue ( coins, 100 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 50 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 20 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 5 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 1 ).getCount ().intValue () );
    }

    @Test
    public void testGetChangeFor() throws Exception {
        propertiesReadWriter.readInventoryData ();
        Collection<Coin> coins = changeServiceImpl.getChangeFor ( 2896 );
        assertTrue ( coins.size () == 5 );
        assertEquals ( 11, filterByValue ( coins, 100 ).getCount ().intValue () );
        assertEquals ( 24, filterByValue ( coins, 50 ).getCount ().intValue () );
        assertEquals ( 59, filterByValue ( coins, 10 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 5 ).getCount ().intValue () );
        assertEquals ( 1, filterByValue ( coins, 1 ).getCount ().intValue () );
    }


    private Coin filterByValue(Collection<Coin> coins, Integer value) {
        return coins.stream ().filter ( coin -> coin.getValue () == value ).findFirst ().get ();
    }
}
