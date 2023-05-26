package ru.netology.reporting;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

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

    @Test
    void shouldSuggestNewDateOnExistingAppointment() {
        String firstMeetingDate = DataGenerator.Delivery.getData(7);
        String secondMeetingDate = DataGenerator.Delivery.getData(8);

        DeliveryInfo deliveryInfo = DataGenerator.Delivery.generate("ru");
        $("[data-test-id='city'] .input__control").setValue(deliveryInfo.getCity());
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(getControl(), "a");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[data-test-id='name'] .input__control").setValue(deliveryInfo.getName());
        $("[data-test-id='phone'] .input__control").setValue(deliveryInfo.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button .button__text").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));

        Selenide.refresh();

        $("[data-test-id='city'] .input__control").setValue(deliveryInfo.getCity());
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(getControl(), "a");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(secondMeetingDate);
        $("[data-test-id='name'] .input__control").setValue(deliveryInfo.getName());
        $("[data-test-id='phone'] .input__control").setValue(deliveryInfo.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button .button__text").click();
        $x("//div[contains(text(), 'Перепланировать')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content .button__text").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
}
