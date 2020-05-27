import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseSeleniumTest {

  WebDriver driver;


    // xpath
    String insurance = "//button[@aria-label='Меню Страхование']";
    String insuranceTravel =
        "//ul[@aria-label='Подменю']//a[contains(text(), 'Страхование путешественников')]";
    String textInsuranceTravel =
        "//div[@class='kit-col_xs_12 kit-col_md_0 kit-col_lg_6 kit-col_xs-bottom_20 kit-col_lg-bottom_10 kit-col_xs-top_20 kit-col_lg-top_40']//h2[contains(text(), 'Страхование путешественников')]";
    String arrangeOnline = "//b[contains(text(), 'Оформить онлайн')]";
    String minimalTax = "//div[@class='online-card-program']//h3[contains(text(),'Минимальная')]";
    String buttonArrange = "//button[contains(text(), 'Оформить')]";
    String buttonContinue = "//button[contains(text(), 'Продолжить')]";

    String surnameIns = "//input[@placeholder='Surname']";
    String nameIns = "//input[@placeholder='Name']";
    String birthdayIns = "//input[@id='birthDate_vzr_ins_0']";

    String surname = "//input[@id='person_lastName']";
    String name = "//input[@id='person_firstName']";
    String middleName = "//input[@id='person_middleName']";
    String birthday = "//input[@id='person_birthDate']";
    String sexMale = "//label[contains(text(), 'Мужской')]";
    String sexFemale = "//label[contains(text(), 'Женский')]";

    String serialPass = "//input[@placeholder='Серия']";
    String numberPass = "//input[@placeholder='Номер']";
    String datePass = "//input[@id='documentDate']";
    String fromPass = "//input[@id='documentIssue']";

    String errorFields = "//div[@class='alert-form alert-form-error']";



    // Данные страхователя

     static String[] user1 =
        new String[] {
          "МУЖ",
          "Пупкин",
          "Василий",
          "Петрович",
          "20.04.1986",
          "8010",
          "125556",
          "10.12.2009",
          "Московским РУВД",
          "Pupkin",
          "Vasiliy",
          "20.04.1986"
        };
   static String[] user2 =
        new String[] {
          "ЖЕН",
          "Иванова",
          "Лариса",
          "Вячеславовна",
          "22.03.1990",
          "8020",
          "153556",
          "10.01.2015",
          "Ленинским РУВД",
          "Ivanova",
          "Larisa",
          "22.03.1990"
        };
    static String[] user3 =
        new String[] {
          "МУЖ",
          "Петров",
          "Константин",
          "Максимович",
          "14.03.1998",
          "8010",
          "153523",
          "14.03.2018",
          "Уфимским РУВД",
          "Petrov",
          "Konstantin",
          "14.03.1998"
        };

    Map<String, String> map = new HashMap<>();


    String[] fields =
        new String[] {
          "sex",
          "surname",
          "name",
          "middleName",
          "birthday",
          "serialPass",
          "numberPass",
          "datePass",
          "fromPass",
          "surnameIns",
          "nameIns",
          "birthdayIns"
        };

  String[][] users = {user1, user2, user3};



  @BeforeEach
  void initDriver(){
    String browser = System.getProperty("browser", "chrome");
    switch (browser) {
      case "chrome":
        {
          System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver");
          driver = new ChromeDriver();
          break;
        }
      case "firefox":
        {
          System.setProperty("webdriver.gecko.driver", "webdrivers/geckodriver");
          driver = new FirefoxDriver();
          break;
        }
    }

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @AfterEach
  void quitDriver() {
    driver.quit();
  }

  void goToUrl(String url) {
    driver.get(url);
  }

  void findFieldAndClick(String xpath){
      driver.findElement(By.xpath(xpath)).click();
  }

  void waitElement(String xpath){
      new WebDriverWait(driver, 5)
              .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
  }

  void waitClickableElement(String xpath){
    new WebDriverWait(driver, 5)
            .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
  }

  String checkTextElement(String xpath){
      return driver.findElement(By.xpath(xpath)).getText();
  }


  void clickAndSendKeys(String xpath, String text){
    waitClickableElement(xpath);
    driver.findElement(By.xpath(xpath)).click();

    driver.findElement(By.xpath(xpath)).sendKeys(text);
  }

  void writeField(HashMap<String, String> map, String[] field, String[] str) {
    cleansFields(fields);
      for (int i = 1; i<= map.size(); i++) {
        clickAndSendKeys(map.get(field[i]), str[i]);
      }
      if (str[0].equals("МУЖ")) {
        findFieldAndClick(sexMale);
      }
      else if (str[0].equals("ЖЕН")) {
        findFieldAndClick(sexFemale);
      }
  }

  void createMap(Map<String, String> map){
    map.put("surname", surname);
    map.put("name", name);
    map.put("middleName", middleName);
    map.put("birthday",birthday);
    map.put("serialPass", serialPass);
    map.put("numberPass", numberPass);
    map.put("datePass", datePass);
    map.put("fromPass", fromPass);
    map.put("surnameIns", surnameIns);
    map.put("nameIns", nameIns);
    map.put("birthdayIns", birthdayIns);
  }

  String fieldValue(String xpath) {
    return driver.findElement(By.xpath(xpath)).getAttribute("value");
  }
  void cleansFields(String[] strings) {
    for (int i = 1; i < strings.length; i++){
      cleanField(map.get(strings[i]));
    }
  }
  void cleanField(String xpath) {
    driver.findElement(By.xpath(xpath)).clear();
  }
  void testField(String[] user){
    for (int i = 1; i < user.length; i++){
      Assertions.assertEquals(user[i], fieldValue(map.get(fields[i])), "Error! Не сходятся поля!");
    }

  }
  void ParamTest(Map<String, String> map, String[] fields, String[][] users){
    for (String[] user : users) {
        writeField((HashMap<String, String>) map, fields, user);
        testField(user);
    }
  }

}
