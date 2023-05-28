package ru.netology.reporting;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Delivery {
        private final static String datePattern = "dd.MM.yyyy";

        public static String getData(int days) {
            return LocalDateTime.now()
                    .plusDays(days)
                    .format(DateTimeFormatter.ofPattern(datePattern));
        }

        private Delivery() {
        }

        public static String generateCity() {
            var city = new String[]{
                    "Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста",
                    "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань",
                    "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь",
                    "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архансельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград",
                    "Вологде", "Воронеж", "Иваново", "Орел", "Иркутск", "Калининград", "Капура", "Кемерово", "Кифов", "Кострома", "Курган", "Курск",
                    "Санкт-Петербург","Липецк", "Магадан", "Москва", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Пенза", "Псков", "Салехард",
                    "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинок", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень",
                    "Ульяновск", "Челябинск", "Ярославль", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь"};
            return city[new Random().nextInt(city.length)];
        }

        public static String wrongPhoneNumber(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return faker.phoneNumber().cellPhone();
        }

        public static DeliveryInfo generate(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new DeliveryInfo(
                    generateCity(),
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.phoneNumber().phoneNumber()
            );
        }

    }
}
