package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.desktop.Main;
import com.vaddya.schedule.desktop.lessons.CreatedLesson;
import com.vaddya.schedule.desktop.lessons.LessonListItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.vaddya.schedule.core.utils.TypeOfWeek.BOTH;
import static com.vaddya.schedule.desktop.util.TypeConverters.getDayOfWeekConverter;
import static com.vaddya.schedule.desktop.util.TypeConverters.getTypeOfWeekConverter;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * Контроллер для списка занятий
 *
 * @author vaddya
 */
public class LessonsController {

    private static final DateTimeFormatter WEEK_FORMATTER =
            DateTimeFormatter.ofPattern("d MMMM", Main.getLocale());

    @FXML
    private Label currWeekLabel;
    @FXML
    private DatePicker weekDatePicker;
    @FXML
    private ListView<Node> lessonList;
    private MainController mainController;
    private EditLessonController editLessonController;
    private Stage editLessonDialogStage;
    private Parent editLessonDialogParent;
    private RemoveLessonController removeLessonController;
    private Stage removeLessonDialogStage;
    private Parent removeLessonDialogParent;

    private SmartSchedule schedule;
    private LocalWeek currentWeek;

    public void init(MainController mainController, SmartSchedule schedule) {
        this.schedule = schedule;
        this.mainController = mainController;
        currentWeek = LocalWeek.current();
        weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
        weekDatePicker.setOnShowing(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getLocale()));
        weekDatePicker.setOnShown(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getLocale()));
        weekDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                LocalWeek week = LocalWeek.from(object);
                return week.getDateOf(MONDAY).format(WEEK_FORMATTER) + " - " +
                        week.getDateOf(SUNDAY).format(WEEK_FORMATTER);
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
                    TypeOfWeek week;
                    try {
                        week = schedule.getLessons().getWeekType(lesson.getId());
                    } catch (NoSuchLessonException e) {
                        week = schedule.getCurrentTypeOfWeek();
                    }
                    editLessonController.setActiveLesson(lesson, week, day, mainController.getSubjectSuggestions());
                    editLessonDialogStage = mainController.showDialog(lessonList.getScene().getWindow(),
                            editLessonDialogStage,
                            editLessonDialogParent,
                            Main.getString("lesson_edit")
                    );
                    parseLessonDialog();
                }
            }
        });
        currentWeek = LocalWeek.current();
        refreshLessons();
    }

    public void datePickerHandler(ActionEvent event) {
        currentWeek = LocalWeek.from(weekDatePicker.getValue());
        refreshLessons();
    }

    public void prevWeekButtonPressed(ActionEvent event) {
        currentWeek = LocalWeek.before(currentWeek);
        weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
        refreshLessons();
    }

    public void nextWeekButtonPressed(ActionEvent event) {
        currentWeek = LocalWeek.after(currentWeek);
        weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));
        refreshLessons();
    }

    public void addLessonButtonPressed(ActionEvent event) {
        editLessonController.setActiveLesson(null, BOTH, MONDAY, mainController.getSubjectSuggestions());
        editLessonDialogStage = mainController.showDialog(lessonList.getScene().getWindow(),
                editLessonDialogStage,
                editLessonDialogParent,
                Main.getString("lesson_add")
        );
        parseLessonDialog();
        refreshLessons();
    }

    public void removeLessonButtonPressed(ActionEvent event) {
        LessonListItem lesson = (LessonListItem) lessonList.getSelectionModel().getSelectedItem();
        if (lesson == null) {
            mainController.setToStatusBar(Main.getString("lesson_select_remove"), 5);
        } else {
            removeLessonController.setSubject(lesson.getLesson().getSubject());
            removeLessonDialogStage = mainController.showDialog(lessonList.getScene().getWindow(),
                    removeLessonDialogStage,
                    removeLessonDialogParent,
                    Main.getString("lesson_remove"));
            parseRemoveLessonDialog(lesson);
        }
    }

    private void refreshLessons() {
        TypeOfWeek typeOfWeek = schedule.getWeek(currentWeek).getTypeOfWeek();
        String currWeek = getTypeOfWeekConverter().toString(typeOfWeek);
        currWeekLabel.setText(currWeek);
        currWeekLabel.setTextAlignment(TextAlignment.CENTER);
        lessonList.getItems().clear();
        ScheduleWeek week = schedule.getWeek(currentWeek);
        for (DayOfWeek day : DayOfWeek.values()) {
            if (week.getDay(day).isEmpty()) {
                continue;
            }
            Label label = new Label(getDayOfWeekConverter().toString(day) + " (" +
                    Dates.FULL_DATE_FORMAT.format(currentWeek.getDateOf(day)) + ")");
            label.getStyleClass().add("title");
            if (currentWeek.getDateOf(day).equals(LocalDate.now())) {
                label.setText(label.getText() + " (" + Main.getString("today") + ")");
            }
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
                schedule.getWeek(currentWeek).getDay(lesson.getDay())
                        .removeLesson(lesson.getLesson());
            } else {
                schedule.getLessons().removeLesson(lesson.getLesson().getId());
            }
            refreshLessons();
        }
    }

    private void parseLessonDialog() {
        if (editLessonController.isSaved()) {
            CreatedLesson lesson = editLessonController.getCreatedLesson();
            if (editLessonController.isCreated()) {
                if (lesson.isOnce()) {
                    schedule.getWeek(currentWeek).getDay(lesson.getTargetDay())
                            .addLesson(lesson.getLesson());
                } else {
                    schedule.getLessons().addLesson(lesson.getTypeOfWeek(),
                            lesson.getTargetDay(), lesson.getLesson());
                }
            } else {
                if (lesson.isOnce()) {
                    if (lesson.isDayChanged()) {
                        schedule.getWeek(currentWeek)
                                .changeLessonDay(lesson.getSourceDay(), lesson.getTargetDay(), lesson.getLesson());
                    } else {
                        schedule.getWeek(currentWeek).getDay(lesson.getSourceDay())
                                .updateLesson(lesson.getLesson());
                    }
                } else {
                    if (lesson.isDayChanged()) {
                        schedule.getLessons().changeLessonDay(lesson.getLesson(), lesson.getTargetDay());
                    } else {
                        schedule.getLessons().updateLesson(lesson.getLesson());
                        schedule.getLessons().changeWeekType(lesson.getLesson(), lesson.getTypeOfWeek());
                    }
                }
            }
            refreshLessons();
        }
    }

}