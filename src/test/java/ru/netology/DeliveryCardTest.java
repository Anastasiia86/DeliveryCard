package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    @Test
    public void shouldSuccessfulFormSubmission() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Хабаровск");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(verificationDate);
        $("[data-test-id=name] input").setValue("Татьяна Петрова");
        $("[data-test-id=phone] input").setValue("+79092678989");
        $("[data-test-id=agreement]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate),
                        Duration.ofSeconds(15));
    }

    @Test   //№2
    public void shouldSuccessfulFormSubmissionAfterInteractingWithComplexElements() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ха");
        $(Selectors.byText("Хабаровск")).click();
        $("[data-test-id=date] input").click();
        int days = 4;
        for (int cycle = 0; cycle < days; cycle++) {
            $(".calendar").sendKeys(Keys.ARROW_RIGHT);
        }
        $(".calendar").sendKeys(Keys.ENTER);
        $("[data-test-id=name] input").setValue("Татьяна Петрова" +
                "");
        $("[data-test-id=phone] input").setValue("+79092678989");
        $("[data-test-id=agreement]").click();
        $(".button").shouldHave(Condition.text("Забронировать")).click();
        String verificationDate = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate),
                        Duration.ofSeconds(15));
    }


//class DeliveryCardTest {
    //@Test
    // void shouldRegisterByAccountNumberDOMModification() {
    //  open("http://localhost:9999");
    // $$(".tab-item").find(exactText("По номеру счёта")).click();
    // $("[name='number']").setValue("4055 0100 0123 4613 8564");
    //  $("[name='phone']").setValue("+792000000000");
    //  $$("button").find(exactText("Продолжить")).click();
    //   $(withText("Успешная авторизация")).shouldBe(visible, Duration.ofMillis(5000));
    //  $(byText("Личный кабинет")).shouldBe(visible, Duration.ofMillis(5000));
    // }

    //  @Test
    //  void shouldRegisterByAccountNumberVisibilityChange() {
    //    open("http://localhost:9999");
    //   $$(".tab-item").find(exactText("По номеру счёта")).click();
    //    $$("[name='number']").last().setValue("4055 0100 0123 4613 8564");
    //   $$("[name='phone']").last().setValue("+792000000000");
    //  $$("button").find(exactText("Продолжить")).click();
    //  $(withText("Успешная авторизация")).shouldBe(visible, Duration.ofSeconds(5));
    //  $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
    // }
}

