package com.quertle.demo;

import org.junit.Before;
import org.junit.Test;

import com.quertle.demo.controller.GeneralController;
import com.quertle.demo.model.General;
import com.quertle.demo.service.GeneralService;

import junit.framework.Assert;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class DemoApplicationTests {

	private GeneralService generalService;
	
	@Before
	public void init() {
		generalService = new GeneralService();
	}
	
	@Test
	public void contextLoads() {
		GeneralController generalController = new GeneralController(this.generalService);
		General general = generalController.getGeneral();
		Assert.assertEquals(general.getFirstName(), "First Name");
		Assert.assertEquals(general.getLastName(), "Last Name");
	}
	
}
