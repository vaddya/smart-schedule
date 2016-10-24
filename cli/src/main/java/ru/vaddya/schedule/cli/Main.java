package ru.vaddya.schedule.cli;

/**
 * Стартовый класс для консольного приложения
 *
 * @author vaddya
 */
public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.printSchedule();
        app.printTasks();
        app.parseInput();
    }
}
