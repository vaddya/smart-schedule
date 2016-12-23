package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.WeekTime;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.lessons.CreatedLesson;
import ru.vaddya.schedule.desktop.lessons.LessonListItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * Контроллер для списка занятий
 *
 * @author vaddya
 */
public class LessonsController {

    private MainController main;

    private SmartSchedule schedule;

    private WeekTime currentWeek;

    private EditLessonController editLessonController;

    private Stage editLessonDialogStage;

    private Parent editLessonDialogParent;

    private RemoveLessonController removeLessonController;

    private Stage removeLessonDialogStage;

    private Parent removeLessonDialogParent;

    private static final DateTimeFormatter WEEK_FORMATTER =
            DateTimeFormatter.ofPattern("d MMMM", Main.getBundle().getLocale());

    @FXML
    private Label currWeekLabel;

    @FXML
    private DatePicker weekDatePicker;

    @FXML
    private ListView<Node> lessonList;

    public void init(MainController main, SmartSchedule schedule) {
        this.main = main;
        this.schedule = schedule;
        currentWeek = WeekTime.current();
        weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
        weekDatePicker.setOnShowing(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getBundle().getLocale()));
        weekDatePicker.setOnShown(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getBundle().getLocale()));
        weekDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                WeekTime weekTime = WeekTime.of(object);
                return weekTime.getDateOf(MONDAY).format(WEEK_FORMATTER) + " - " +
                        weekTime.getDateOf(SUNDAY).format(WEEK_FORMATTER);
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, WEEK_FORMATTER);
            }
        });
        initControllers();
        initLessonsList();
    }

    private void initControllers() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.getBundle());
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/edit_lesson.fxml"));
        try {
            editLessonDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editLessonController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.getBundle());
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/remove_lesson.fxml"));
        try {
            removeLessonDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeLessonController = fxmlLoader.getController();
    }

    private void initLessonsList() {
        lessonList.setFocusTraversable(false);
        lessonList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                if (lessonList.getSelectionModel().getSelectedItem() instanceof LessonListItem) {
                    LessonListItem item = (LessonListItem) lessonList.getSelectionModel().getSelectedItem();
                    Lesson lesson = item.getLesson();
                    DayOfWeek day = item.getDay();
                    editLessonController.setActiveLesson(lesson, day);
                    editLessonDialogStage = main.showDialog(lessonList.getScene().getWindow(),
                            editLessonDialogStage,
                            editLessonDialogParent,
                            Main.getBundle().getString("lesson_edit")
                    );
                    parseLessonDialog();
                }
            }
        });
        currentWeek = WeekTime.current();
        refreshLessons();
//        new Thread(() -> {
//            schedule.getWeek(WeekTime.before(currentWeek));
//            int i = 0;
//            WeekTime current = currentWeek;
//            while (i++ < 3) {
//                schedule.getWeek(WeekTime.after(current));
//                current = WeekTime.after(current);
//            }
//        }).start();
    }

    public void actionButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        switch (button.getId()) {
            case "prevWeekButton":
                currentWeek = WeekTime.before(currentWeek);
                weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
                refreshLessons();
                break;
            case "nextWeekButton":
                currentWeek = WeekTime.after(currentWeek);
                weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
                refreshLessons();
                break;
            case "addLessonButton":
                editLessonController.setActiveLesson(null, MONDAY);
                editLessonDialogStage = main.showDialog(lessonList.getScene().getWindow(),
                        editLessonDialogStage,
                        editLessonDialogParent,
                        Main.getBundle().getString("lesson_add")
                );
                parseLessonDialog();
                refreshLessons();
                break;
            case "removeLessonButton":
                LessonListItem lesson = (LessonListItem) lessonList.getSelectionModel().getSelectedItem();
                if (lesson == null) {
                    main.setToStatusBar(Main.getBundle().getString("lesson_select_remove"), 5);
                } else {
                    removeLessonController.setActiveLesson(lesson.getLesson().getSubject());
                    removeLessonDialogStage = main.showDialog(lessonList.getScene().getWindow(),
                            removeLessonDialogStage,
                            removeLessonDialogParent,
                            Main.getBundle().getString("lesson_remove")
                    );
                    parseRemoveLessonDialog(lesson);
                }
                break;
        }
    }

    public void datePickerHandler(ActionEvent event) {
        currentWeek = WeekTime.of(weekDatePicker.getValue());
        refreshLessons();
    }

    private void refreshLessons() {
//        String currWeek = String.format("%s - %s (%s)",
//                currentWeek.getDateOf(MONDAY).format(WEEK_FORMATTER),
//                currentWeek.getDateOf(SUNDAY).format(WEEK_FORMATTER),
//                Main.getBundle().getString(schedule.getWeek(currentWeek).getWeekType().toString().toLowerCase())
//        );
        String currWeek = Main.getBundle().getString(schedule.getWeek(currentWeek).getWeekType().toString().toLowerCase());
        currWeekLabel.setText(currWeek);
        currWeekLabel.setTextAlignment(TextAlignment.CENTER);
        lessonList.getItems().clear();
        StudyWeek week = schedule.getWeek(currentWeek);
        for (DayOfWeek day : DayOfWeek.values()) {
            if (week.getDay(day).isEmpty()) {
                continue;
            }
            Label label = new Label(Main.getBundle().getString(day.toString().toLowerCase()) + " (" +
                    Dates.FULL_DATE_FORMAT.format(currentWeek.getDateOf(day)) + ")");
            label.setDisable(true);
            lessonList.getItems().add(label);
            for (Lesson lesson : week.getDay(day).getLessons()) {
                lessonList.getItems().add(new LessonListItem(lesson, day));
            }
        }
    }

    private void parseRemoveLessonDialog(LessonListItem lesson) {
        if (removeLessonController.isRemoved()) {
            if (removeLessonController.isOnce()) {
                schedule.getWeek(currentWeek).getDay(lesson.getDay()).removeLesson(lesson.getLesson());
            } else {
                schedule.getSchedule(schedule.getWeekType(currentWeek)).removeLesson(lesson.getDay(), lesson.getLesson());
            }
            schedule.updateLessons();
            refreshLessons();
        }
    }

    private void parseLessonDialog() {
        if (editLessonController.isSaved()) {
            CreatedLesson lesson = editLessonController.getCreatedLesson();
            if (editLessonController.isCreated()) {
                if (lesson.isOnce()) {
                    schedule.getWeek(currentWeek).getDay(lesson.getSourceDay()).addLesson(lesson.getLesson());
                } else {
                    schedule.getCurrentSchedule().addLesson(lesson.getSourceDay(), lesson.getLesson());
                }
            } else {
                if (lesson.isOnce()) {
                    if (lesson.isDayChanged()) {
                        schedule.getWeek(currentWeek).changeLessonDay(lesson.getSourceDay(), lesson.getTargetDay(), lesson.getLesson());
                    } else {
                        schedule.getWeek(currentWeek).getDay(lesson.getSourceDay()).updateLesson(lesson.getLesson());
                    }
                } else {
                    if (lesson.isDayChanged()) {
                        schedule.getSchedule(schedule.getWeekType(currentWeek)).changeLessonDay(lesson.getSourceDay(), lesson.getTargetDay(), lesson.getLesson());
                    } else {
                        schedule.getSchedule(schedule.getWeekType(currentWeek)).updateLesson(lesson.getSourceDay(), lesson.getLesson());

                    }
                }
            }
            schedule.updateLessons();
            refreshLessons();
        }
    }
}
