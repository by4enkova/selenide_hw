package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardDeliveryTest {



    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSendValidForm() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск"); //город
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);//дата
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Волкова Александра");//фамилия и имя
        $("[data-test-id=phone] input").setValue("+79315420338");//моб телефон
        $("[data-test-id=agreement] span").click();//галочка
        $(withText("Забронировать")).click();//забронировать
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на "
                        + deliveryDate), Duration.ofSeconds(15));

    }

    @Test
    public void shouldSendWithAnotherDate() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Волкова Александра");
        $("[data-test-id=phone] input").setValue("+79315420338");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на "
                + deliveryDate), Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotSendWithInvalidCity() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Petrozavodsk");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Волкова Александра");
        $("[data-test-id=phone] input").setValue("+79315420338");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Доставка в выбранный город недоступна";
        String actualText = $("[data-test-id=city] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);

    }

    @Test
    public void shouldNotSendWithoutCity() {
        Configuration.holdBrowserOpen = true;

        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Волкова Александра");
        $("[data-test-id=phone] input").setValue("+79315420338");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = $("[data-test-id=city] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    public void shouldNotSendWithoutName() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=phone] input").setValue("+79315420338");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = $("[data-test-id=name] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    public void shouldNotSendInvalidName() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Petrova");
        $("[data-test-id=phone] input").setValue("+79315420338");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = $("[data-test-id=name] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldNotSendInvalidPhone() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Смирнова Александра");
        $("[data-test-id=phone] input").setValue("+793154203");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = $("[data-test-id=phone] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldNotSendWithoutPhone() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Смирнова Александра");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = $("[data-test-id=phone] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldNotSendWithoutCheckbox() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Петрозаводск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Смирнова Александра");
        $("[data-test-id=phone] input").setValue("+79315420333");

        $(withText("Забронировать")).click();
        String checkboxInvalid = $("[data-test-id=agreement].checkbox").getAttribute("className");
        assertTrue(checkboxInvalid.contains("input_invalid"));
    }

}

