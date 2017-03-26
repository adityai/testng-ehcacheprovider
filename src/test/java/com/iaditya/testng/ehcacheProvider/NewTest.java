package com.iaditya.testng.ehcacheProvider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Test suite using ehcache to store the test data with the data provider
 * 
 * @author adityai
 *
 */
public class NewTest {

	private Cache cache;
	
	/**
	 * Constructor. Alternatively this can be implemented as BeforeSuite method
	 */
	public NewTest() {
		Map<String, String> dataMap;
		CacheManager cacheManager = new CacheManager();
		cacheManager.addCache("testCache");
		cache = cacheManager.getCache("testCache");
		dataMap = new HashMap<String, String>();
		dataMap.put("testMethod", "testMethod1");
		dataMap.put("lastName", "Doe");
		cache.put(new Element("testMethod1", dataMap));

		dataMap = new HashMap<String, String>();
		dataMap.put("testMethod", "testMethod2");
		dataMap.put("lastName", "Doer");
		cache.put(new Element("testMethod2", dataMap));
	}
	
	/**
	 * Data provider provisioned with ehcache
	 * 
	 * @param method
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="ehcacheProvider")
	public Object[][] getEhCacheData(Method method) {
		Object[][] data = null;
		Map<String, String> dataMap;
		dataMap = (Map<String, String>) cache.get(method.getName()).getObjectValue();
		System.out.print(dataMap);
		
		data = new Object[][] {{ dataMap }};
        return data;
    }
		
	/**
	 * Simple test method
	 * @param dataMap
	 */
	@Test(dataProvider="ehcacheProvider")
	public void testMethod1(Map<String, String> dataMap) {
		Assert.assertEquals(dataMap.get("lastName"), "Doe");
	}

	/**
	 * Simple test method
	 * @param dataMap
	 */
	@Test(dataProvider="ehcacheProvider")
	public void testMethod2(Map<String, String> dataMap) {
		Assert.assertEquals(dataMap.get("lastName"), "Doer");
	}

}
