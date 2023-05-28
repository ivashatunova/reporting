package ru.netology.reporting;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ChangeDateTest {
    private Keys getControl() {
        String os = System.getProperty("os.name");

        if (os.contains("Mac")) {
            return Keys.COMMAND;
        } else {
            return Keys.CONTROL;
        }
    }


    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    private final String firstMeetingDate = DataGenerator.Delivery.getData(7);


    @Test
    void shouldSendForm() {
        DeliveryInfo deliveryInfo = DataGenerator.Delivery.generate("ru");
        $("[data-test-id='city'] .input__control").setValue(deliveryInfo.getCity());
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[data-test-id='name'] .input__control").setValue(deliveryInfo.getName());
        $("[data-test-id='phone'] .input__control").setValue(deliveryInfo.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button .button__text").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible, Duration.ofSeconds(20));
    }

    @Test
    void wrongPhoneNumber() {
        DeliveryInfo deliveryInfo = DataGenerator.Delivery.generate("ru");
        $("[data-test-id='city'] .input__control").setValue(deliveryInfo.getCity());
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[data-test-id='name'] .input__control").setValue(deliveryInfo.getName());
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.Delivery.wrongPhoneNumber("ru"));
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(" [data-test-id='phone'] .input__sub")
                .shouldHave(exactText("Неверный формат номера мобильного телефона"))
                .shouldBe(visible);
    }
}
