package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.WeekTime;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.lessons.CreatedLesson;
import ru.vaddya.schedule.desktop.lessons.LessonListItem;
import ru.vaddya.schedule.desktop.tasks.TaskList;
import ru.vaddya.schedule.desktop.tasks.TaskListItem;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * Контроллер для главного окна
 *
 * @author vaddya
 */
public class MainController implements Initializable {

    /*
     * Schedule
     */
    private SmartSchedule schedule = new SmartScheduleImpl();

    /*
     * Lessons
     */
    private WeekTime currentWeek;

    private EditLessonController editLessonController;

    private Stage editLessonDialogStage;

    private Parent editLessonDialogParent;

    private RemoveLessonController removeLessonController;

    private Stage removeLessonDialogStage;

    private Parent removeLessonDialogParent;

    /*
     * Tasks
     */
    private TaskList taskList;

    private EditTaskController editTaskController;

    private Stage editTaskDialogStage;

    private Parent editTaskDialogParent;

    /*
     * FXML Lessons
     */
    @FXML
    private Label currWeekLabel;

    @FXML
    private DatePicker weekDatePicker;

    @FXML
    private ListView<Node> lessonList;

    /*
     * FXML Tasks
     */
    @FXML
    private Label tasksCountLabel;

    @FXML
    private TableView<TaskListItem> tasksTableView;

    @FXML
    private TableColumn<TaskListItem, String> taskStatusColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskSubjectColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskTypeColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskDeadlineColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskTextColumn;

    private static final DateTimeFormatter DAY_MONTH = DateTimeFormatter.ofPattern("d MMMM", Main.bundle.getLocale());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTasksTable();
        initLessonsList();
        weekDatePicker.setValue(currentWeek.getDateOf(MONDAY));

        initControllers();
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

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/edit_task.fxml"));
        try {
            editTaskDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editTaskController = fxmlLoader.getController();
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
                    showLessonDialog(lessonList.getScene().getWindow());
                    parseLessonDialog();
                }
            }
        });
        currentWeek = WeekTime.current();
        refreshLessons();
        new Thread(() -> {
            schedule.getWeek(WeekTime.before(currentWeek));
            int i = 0;
            WeekTime current = currentWeek;
            while (i++ < 3) {
                schedule.getWeek(WeekTime.after(current));
                current = WeekTime.after(current);
            }
        }).start();
    }

    private void initTasksTable() {
        tasksTableView.setColumnResizePolicy(param -> false);

        taskStatusColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.145));
        taskSubjectColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.20));
        taskTypeColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.15));
        taskDeadlineColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
        taskTextColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.4));

        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        taskSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        taskTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTextColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        taskList = new TaskList(schedule.getTasks());
        taskList.addListener(c -> updateCountTasks());
        tasksTableView.setItems(taskList.getItems());
        updateCountTasks();

        tasksTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                TaskListItem task = tasksTableView.getSelectionModel().getSelectedItem();
                if (task != null) {
                    editTaskController.setActiveTask(task);
                    showTaskDialog(tasksTableView.getScene().getWindow());
                    if (editTaskController.isSaved()) taskList.update(task);
                }
            }
        });
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

    public void datePickerHandler(ActionEvent event) {
        currentWeek = WeekTime.of(weekDatePicker.getValue());
        refreshLessons();
    }

    public void actionButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        TaskListItem task;
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
                showLessonDialog(lessonList.getScene().getWindow());
                parseLessonDialog();
                refreshLessons();
                break;
            case "removeLessonButton":
                LessonListItem lesson = (LessonListItem) lessonList.getSelectionModel().getSelectedItem();
                if (lesson != null) {
                    removeLessonController.setActiveLesson(lesson.getLesson().getSubject());
                    showRemoveLessonDialog(lessonList.getScene().getWindow());
                    parseRemoveLessonDialog(lesson);
                }
                break;
            case "addTaskButton":
                task = new TaskListItem();
                editTaskController.setActiveTask(task);
                showTaskDialog(button.getScene().getWindow());
                if (editTaskController.isSaved()) tasksTableView.getItems().add(task);
                break;
            case "editTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                if (task != null) {
                    editTaskController.setActiveTask(task);
                    showTaskDialog(tasksTableView.getScene().getWindow());
                    taskList.update(task);
                    tasksTableView.refresh();
                }
                break;
            case "removeTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                tasksTableView.getItems().remove(task);
                break;
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

        }
    }

    private void updateCountTasks() {
        tasksCountLabel.setText(Main.bundle.getString("tasks_count") + " " + taskList.count());
    }

    private void showRemoveLessonDialog(Window window) {
        if (removeLessonDialogStage == null) {
            removeLessonDialogStage = new Stage();
            removeLessonDialogStage.setTitle(Main.bundle.getString("lesson_remove"));
            removeLessonDialogStage.setResizable(false);
            removeLessonDialogStage.setScene(new Scene(removeLessonDialogParent));
            removeLessonDialogStage.initModality(Modality.WINDOW_MODAL);
            removeLessonDialogStage.initOwner(window);
        }
        removeLessonDialogStage.showAndWait();
    }

    private void showLessonDialog(Window window) {
        if (editLessonDialogStage == null) {
            editLessonDialogStage = new Stage();
            editLessonDialogStage.setTitle(Main.bundle.getString("lesson_add"));
            editLessonDialogStage.setResizable(false);
            editLessonDialogStage.setScene(new Scene(editLessonDialogParent));
            editLessonDialogStage.initModality(Modality.WINDOW_MODAL);
            editLessonDialogStage.initOwner(window);
        }
        editLessonDialogStage.showAndWait();
    }

    private void showTaskDialog(Window window) {
        if (editTaskDialogStage == null) {
            editTaskDialogStage = new Stage();
            editTaskDialogStage.setTitle(Main.bundle.getString("task_add"));
            editTaskDialogStage.setResizable(false);
            editTaskDialogStage.setScene(new Scene(editTaskDialogParent));
            editTaskDialogStage.initModality(Modality.WINDOW_MODAL);
            editTaskDialogStage.initOwner(window);
        }
        editTaskDialogStage.showAndWait();
    }
}