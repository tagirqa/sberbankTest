import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SberbankTest extends BaseSeleniumTest {

  @DisplayName("Сбербанк тест")
  @Test
  void sberbankTest() throws InterruptedException {
    goToUrl("http://www.sberbank.ru/ru/person");  //Перейти на страницу http://www.sberbank.ru/ru/person
    findFieldAndClick(insurance); // Нажать на – Страхование
    findFieldAndClick(insuranceTravel); // Выбрать – Путешествие и покупки

    Assertions.assertEquals(  // Проверить наличие на странице заголовка – Страхование путешественников
        "Страхование путешественников",
        checkTextElement(textInsuranceTravel),
        "Текст 'Страхование путешественников' не найден!");

    findFieldAndClick(arrangeOnline);  // Нажать на – Оформить Онлайн
    findFieldAndClick(minimalTax); // На вкладке – Выбор полиса выбрать сумму страховой защиты – Минимальная
    waitClickableElement(buttonArrange);
    findFieldAndClick(buttonArrange); // Нажать Оформить
    createMap(map);

    ParamTest(map, fields, users); // На вкладке Оформить заполнить поля и проверить их

    findFieldAndClick(buttonContinue); // Нажать продолжить

    assertThat(  // Проверить, что появилось сообщение - Заполнены не все обязательные поля
        "Текст 'При заполнении данных произошла ошибка' не найден!",
        checkTextElement(errorFields),
        is(equalTo("При заполнении данных произошла ошибка")));

  }

}
