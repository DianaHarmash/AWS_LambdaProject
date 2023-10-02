package com.example.diplomaspringproject1_0.entity.enums;

public enum Speciality {
    MANAGEMENT("Менеджмент"),
    COMPUTER_SCIENCE("Комп\'ютерні науки"),
    ECOLOGY("Екологія"),
    MATHEMATICS("Математика"),
    COMPUTER_ENGINEERING("Комп\'ютерна інжинерія"),
    SYSTEM_ANALYSIS("Системна аналітика"),
    HEAT_ENERGY("Теплова енергетика");

    private final String specialityName;

    Speciality(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityName() {
        return specialityName;
    }
}
