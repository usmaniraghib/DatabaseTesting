package com.raghib.selenium;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWithJDBC {

	public static void main(String[] args) throws SQLException, InterruptedException {
		databaseConnectionTest();
		//gmailLoginTest();
		//kvsLoginTest();
	}

	public static String databseURL = "jdbc:mysql://localhost:3306/qadbt";
	public static String databseUserName = "root";
	public static String databsePassword = "root";
	public static String sqlQuery = "select *from GmailLogin";
	public static String tableColumnOne = "username";
	public static String tableColumnTwo = "password";

	public static String chromeDriverPath = System.getProperty("user.dir");
	public static WebDriver driver = null;
	public static Connection connectionObject = null;
	public static Statement statementObject = null;
	public static ResultSet resultSetObject = null;

	public static String gmailLoginURL = "https://accounts.google.com/v3/signin/identifier?dsh=S-754440734%3A1672416755385772&continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&emr=1&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&osid=1&passive=1209600&service=mail&flowName=GlifWebSignIn&flowEntry=ServiceLogin&ifkv=AeAAQh7Jyv6zCsTxwJFuFZ1fG4Nl8Wf1kp9Ahzr8388098pqPrcBxLItCxLykIqCci1gM6JPYrlFJA";
	public static String gmailUserNameXPath = "//input[@id='identifierId']";
	public static String gmailUserNameNextButtonXPath = "//span[contains(text(),'Next')]";
	public static String gmailPasswordXPath = "//body/div[1]/div[1]/div[2]/div[1]/c-wiz[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/span[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]";
	public static String gmailPasswordNextButtonXPath = "//span[contains(text(),'Next')]";

	public static String kvsLoginURL = "https://examinationservices.nic.in/examsys22part2/root/Home.aspx?enc=Ei4cajBkK1gZSfgr53ImFRYH7WnJVuUgORGXbdjV7EbeO3oUw4300Nel7JVo4U3w";
	public static String kvsUserNameXPath = "//input[@id='ctl00_ContentPlaceHolder1_txtRegno']";
	public static String kvsPasswordXPath = "//input[@id='ctl00_ContentPlaceHolder1_txtPassword']";
	public static String kvsCaptchaXPath = "//input[@id='ctl00_ContentPlaceHolder1_txtsecpin']";
	public static String kvsSigninButton = "//input[@id='ctl00_ContentPlaceHolder1_btnsignin']";
	public static String kvsSignoutButton = "//a[contains(text(),'Logout')]";
	public static String kvsSignoutYesButton = "//a[@id='btnLogout']";

	public static void databaseConnectionTest() throws SQLException {
		Connection connectionObject = DriverManager.getConnection(databseURL, databseUserName, databsePassword);
		Statement statementObject = connectionObject.createStatement();
		ResultSet resultSetObject = statementObject.executeQuery(sqlQuery);
		while (resultSetObject.next()) {
			System.out.println("Username : " + resultSetObject.getString(tableColumnOne) + " & Password is : "
					+ resultSetObject.getString(tableColumnTwo));
		}
	}

	public static void kvsLoginTest() throws SQLException, InterruptedException {

		if (connectionObject == null) {
			connectionObject = DriverManager.getConnection(databseURL, databseUserName, databsePassword);
		}

		if (statementObject == null) {
			statementObject = connectionObject.createStatement();
		}

		if (resultSetObject == null) {
			resultSetObject = statementObject.executeQuery(sqlQuery);
		}

		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath + "\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.get(kvsLoginURL);
		driver.manage().window().maximize();

		while (resultSetObject.next()) {
			Duration duration = Duration.ofSeconds(30);	
			WebDriverWait wait = new WebDriverWait(driver, duration);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(kvsUserNameXPath)));
			driver.findElement(By.xpath(kvsUserNameXPath)).sendKeys(resultSetObject.getString(tableColumnOne));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(kvsPasswordXPath)));
			driver.findElement(By.xpath(kvsPasswordXPath)).sendKeys(resultSetObject.getString(tableColumnTwo));

			try (Scanner scannerObject = new Scanner(System.in)) {
				System.out.print("Please Enter The Captcha : ");
				String captchaResult = scannerObject.nextLine();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(kvsCaptchaXPath)));
				driver.findElement(By.xpath(kvsCaptchaXPath)).sendKeys(captchaResult);
			}
			driver.findElement(By.xpath(kvsSigninButton)).click();
		}
		Thread.sleep(10000);
		driver.findElement(By.xpath(kvsSignoutButton)).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(kvsSignoutYesButton)).click();
		Thread.sleep(10000);
		driver.quit();
	}

	public static void gmailLoginTest() throws SQLException, InterruptedException {

		if (connectionObject == null) {
			connectionObject = DriverManager.getConnection(databseURL, databseUserName, databsePassword);
		}

		if (statementObject == null) {
			statementObject = connectionObject.createStatement();
		}

		if (resultSetObject == null) {
			resultSetObject = statementObject.executeQuery(sqlQuery);
		}

		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath + "\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.get(gmailLoginURL);
		driver.manage().window().maximize();

		while (resultSetObject.next()) {
			Duration duration = Duration.ofSeconds(30);	
			WebDriverWait wait = new WebDriverWait(driver, duration);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(gmailUserNameXPath)));
			driver.findElement(By.xpath(gmailUserNameXPath)).sendKeys(resultSetObject.getString(tableColumnOne));
			driver.findElement(By.xpath(gmailUserNameNextButtonXPath)).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(gmailPasswordXPath)));
			driver.findElement(By.xpath(gmailPasswordXPath)).sendKeys(resultSetObject.getString(tableColumnTwo));
			driver.findElement(By.xpath(gmailPasswordNextButtonXPath)).click();
		}
		Thread.sleep(50000);
		driver.quit();
	}

}
