package gov.usgs.wma.mlrlegacy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.ResponseEntity;

import com.github.springtestdbunit.annotation.DatabaseSetup;


@DatabaseSetup("classpath:/testData/setupOne/")
public class ControllerRIT extends BaseControllerIT {

	@Test
	public void getMonitoringLocationsFound() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/monitoringLocations", String.class);

		assertEquals(400, responseEntity.getStatusCodeValue());
		//JSONAssert.assertEquals("[" + getExpectedReadJson("oneMillion.json") + "]", responseEntity.getBody(), JSONCompareMode.STRICT);
	}
	

	@Test
	public void getMonitoringLocationsByAgencyCodeAndSiteNumberFound() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/monitoringLocations?agencyCode=USGS&siteNumber=123456789012345", String.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		JSONAssert.assertEquals("[" + getExpectedReadJson("oneMillion.json") + "]", responseEntity.getBody(), JSONCompareMode.STRICT);
	}
	
	@Test
	public void getMonitoringLocationsByAgencyCodeAndSiteNumberNotFound() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/monitoringLocations?agencyCode=USGS&siteNumber=123456789012349", String.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		JSONAssert.assertEquals("[]", responseEntity.getBody(), JSONCompareMode.STRICT);
	}

	@Test
	public void getMonitoringLocationFound() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/monitoringLocations/1000000", String.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
		JSONAssert.assertEquals(getExpectedReadJson("oneMillion.json"), responseEntity.getBody(), JSONCompareMode.STRICT);
	}

	@Test
	public void getMonitoringLocationNotFound() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/monitoringLocations/1000001", String.class);

		assertEquals(404, responseEntity.getStatusCodeValue());
		assertNull(responseEntity.getBody());
	}

}
