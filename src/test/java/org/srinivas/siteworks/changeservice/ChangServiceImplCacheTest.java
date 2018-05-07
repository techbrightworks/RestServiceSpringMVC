package org.srinivas.siteworks.changeservice;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.srinivas.siteworks.calculate.Calculate;
import org.srinivas.siteworks.calculate.SupplyCalculator;
import org.srinivas.siteworks.config.AppConfig;
import org.srinivas.siteworks.data.CoinsInventoryData;
import org.srinivas.siteworks.data.PropertiesReadWriter;
import org.srinivas.siteworks.denomination.Coin;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
public class ChangServiceImplCacheTest {

	private SupplyCalculator supplyCalculator;
	private ChangeServiceImpl changeServiceImpl;
	@Autowired
	private Calculate optimalCalculator;

	@Autowired
	PropertiesReadWriter propertiesReadWriter;

	@Autowired
	CacheManager cachemanager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		supplyCalculator = new SupplyCalculator();
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
		optimalCalculator = null;

	}

	@Test
	public void testGetOptimalChangeForCaching() throws Exception {
		propertiesReadWriter.readInventoryData();
		changeServiceImpl.getOptimalChangeFor(576);
		List<Coin> coins = getCacheValues("coins", 576);
		assertTrue(coins.size() == 5);
		changeServiceImpl.getOptimalChangeFor(576);
		changeServiceImpl.getOptimalChangeFor(600);
		changeServiceImpl.getOptimalChangeFor(576);
		changeServiceImpl.getOptimalChangeFor(600);
	}

	@SuppressWarnings("unchecked")
	public List<Coin> getCacheValues(String cacheName, Integer keyval) {
		Cache cache = cachemanager.getCache(cacheName);
		for (Object key : cache.getKeys()) {
			if (Integer.valueOf(key.toString()).intValue() == keyval.intValue()) {
				Element element = cache.get(key);
				return (List<Coin>) element.getObjectValue();
			}
		}
		return new ArrayList<Coin>();
	}

}
