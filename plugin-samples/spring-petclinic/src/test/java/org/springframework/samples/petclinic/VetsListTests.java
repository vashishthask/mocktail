package org.springframework.samples.petclinic;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;

public class VetsListTests {

	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
                System.out.println("Before running before");
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                System.out.println("after running before");
	}

	@Test
	public void testVerifyHenryVet() throws Exception {
                System.out.println("Before running before");
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                System.out.println("after running before");
                System.out.println("Before running test");
		driver.get(baseUrl + "/petclinic/vets");
		assertEquals(
				"Henry Stevens",
				driver.findElement(
						By.xpath("//div[@id='main']/table/tbody/tr[6]/td"))
						.getText());
		assertEquals(
				"radiology",
				driver.findElement(
						By.xpath("//div[@id='main']/table/tbody/tr[6]/td[2]"))
						.getText());
               
                System.out.println("after running test");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
