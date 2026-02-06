package org.loggers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NaukriUpdateProfile {

    public static void main(String[] args) throws Exception {

        // Step 1: Start ChromeDriver (Selenium Manager auto-manages driver)
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get("https://www.naukri.com/nlogin/login");

        // Step 2: Login
        driver.findElement(By.id("usernameField"))
                .sendKeys("100sambha@gmail.com");

        driver.findElement(By.id("passwordField"))
                .sendKeys("Sambha@100");

        driver.findElement(By.xpath("//button[text()='Login']")).click();

        System.out.println("If CAPTCHA appears, solve it manually...");
        Thread.sleep(15000);

        // Step 3: Go to Profile
        driver.get("https://www.naukri.com/mnjuser/profile");
        Thread.sleep(5000);

        // Step 4: Edit Summary
        WebElement editBtn = driver.findElement(By.xpath(
        		"//*[@id='lazyResumeHead']/div/div/div[2]/div/div)"));
//                "//span[text()='Summary']/ancestor::div[contains(@class,'widget')]//span[text()='Edit']"));
   
        editBtn.click();

        Thread.sleep(3000);

        // Step 5: Update Summary
        WebElement summaryBox = driver.findElement(By.xpath("//textarea"));
        summaryBox.clear();
        summaryBox.sendKeys("Your updated profile summary goes here...");

        Thread.sleep(2000);

        // Step 6: Save
        WebElement saveBtn = driver.findElement(By.xpath("//button[text()='Save']"));
        saveBtn.click();

        System.out.println("Profile summary updated successfully!");

        Thread.sleep(3000);
        driver.quit();
    }
}