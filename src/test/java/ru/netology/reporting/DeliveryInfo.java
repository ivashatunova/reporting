package ru.netology.reporting;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryInfo {
    private final String city;
    private final String name;
    private final String phone;
}
