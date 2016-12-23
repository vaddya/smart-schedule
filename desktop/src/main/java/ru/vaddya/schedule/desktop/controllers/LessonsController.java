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
import java.time.format.DateTimeFormatter;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * ru.vaddya.schedule.desktop.controllers at smart-schedule
 *
 * @author vaddya
 * @since December 22, 2016
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

    private static final DateTimeFormatter DAY_MONTH = DateTimeFormatter.ofPattern("d MMMM", Main.bundle.getLocale());

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
        initControllers();
        initLessonsList();
    }

    private void initControllers() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/edit_lesson.fxml"));
        try {
            editLessonDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editLessonController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.bundle);
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
                            Main.bundle.getString("lesson_edit")
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
                        Main.bundle.getString("lesson_add")
                );
                parseLessonDialog();
                refreshLessons();
                break;
            case "removeLessonButton":
                LessonListItem lesson = (LessonListItem) lessonList.getSelectionModel().getSelectedItem();
                if (lesson == null) {
                    main.setToStatusBar(Main.bundle.getString("lesson_select_remove"), 5);
                } else {
                    removeLessonController.setActiveLesson(lesson.getLesson().getSubject());
                    removeLessonDialogStage = main.showDialog(lessonList.getScene().getWindow(),
                            removeLessonDialogStage,
                            removeLessonDialogParent,
                            Main.bundle.getString("lesson_remove")
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
        String currWeek = currentWeek.getDateOf(MONDAY).format(DAY_MONTH) + " - " + currentWeek.getDateOf(SUNDAY).format(DAY_MONTH) +
                " (" + Main.bundle.getString(schedule.getWeek(currentWeek).getWeekType().toString().toLowerCase()) + ")";
        currWeekLabel.setText(currWeek);
        currWeekLabel.setTextAlignment(TextAlignment.CENTER);
        lessonList.getItems().clear();
        StudyWeek week = schedule.getWeek(currentWeek);
        for (DayOfWeek day : DayOfWeek.values()) {
            if (week.getDay(day).isEmpty()) {
                continue;
            }
            Label label = new Label(Main.bundle.getString(day.toString().toLowerCase()) + " (" +
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
            refreshLessons();
            schedule.updateLessons();
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
