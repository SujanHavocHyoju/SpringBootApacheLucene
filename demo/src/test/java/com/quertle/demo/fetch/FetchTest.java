package com.quertle.demo.fetch;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import com.quertle.demo.service.FetchService;

public class FetchTest {

	private String dataSource;

	private FetchService fetchService = new FetchService();

	@Before
	public void init() {
		dataSource = "${fierceNews.datasource}";
	}

	@Test
	public void fetch() {
		fetchService.saveIfExists(dataSource);
	}

}
