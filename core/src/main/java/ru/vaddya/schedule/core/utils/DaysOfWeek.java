package ru.vaddya.schedule.core.utils;

//TODO: может не стоит делать интернационализацию в ядре? Мне кажется в ядре все должны быть абстрактные образы,
//а за строки пусть отвечает другой слой. Все равно все языки не написать, пусть пользователи делают.

/**
 * Перечисление дней недели
 *
 * @author vaddya
 */
public enum DaysOfWeek {
    // TODO: 10/23/2016 Интернационализация из файла
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскрсенье");

    private String ru;
    private String en;

    public static void main(String[] args) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            System.out.println(day.en);
        }
    }

    DaysOfWeek(String ru) {
        this.ru = ru;
        this.en = name().charAt(0) + name().substring(1).toLowerCase();
    }

    public String ru() {
        return ru;
    }

    public String en() {
        return en;
    }

    public static DaysOfWeek valueOfRu(String ru) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (ru.equals(day.ru())) {
                return day;
            }
        }
        return null;
    }
}
