import { createNewTask, removeTaskById, getAllTasks, updateTaskById } from 'services/tasks';

const retrieveTasks = () => async dispatch => {
    try {
        const farms = await getAllTasks();
        dispatch({
            type: ACTION_TASKS_RETRIEVE,
            payload: farms,
        });
    } catch (err) {
        dispatch({
            type: ACTION_TASKS_RETRIEVE_FAILED,
            payload: err,
        });
    }
};

const updateTask = (task) => async dispatch => {
    try {
        await updateTaskById(task);
        dispatch({
            type: ACTION_TASKS_UPDATE,
            payload: task,
        });
    } catch (err) {
        dispatch({
            type: ACTION_TASKS_UPDATE_FAILED,
            payload: err,
        });
    }
};

const createTask = (task) => async dispatch => {
    try {
        task = await createNewTask(task);
        dispatch({
            type: ACTION_TASKS_CREATE,
            payload: task,
        });
    } catch (err) {
        dispatch({
            type: ACTION_TASKS_CREATE_FAILED,
            payload: err,
        });
    }
};

const removeTask = (task) => async dispatch => {
    try {
        await removeTaskById(task);
        dispatch({
            type: ACTION_TASKS_DELETE,
            payload: task,
        });
    } catch (err) {
        dispatch({
            type: ACTION_TASKS_DELETE_FAILED,
            payload: err,
        });
    }
};

export {
    retrieveTasks,
    updateTask,
    createTask,
    removeTask
};

export const ACTION_TASKS_RETRIEVE = 'ACTION_TASKS_RETRIEVE';
export const ACTION_TASKS_RETRIEVE_FAILED = 'ACTION_TASKS_RETRIEVE_FAILED';
export const ACTION_TASKS_UPDATE = 'ACTION_TASKS_UPDATE';
export const ACTION_TASKS_UPDATE_FAILED = 'ACTION_TASKS_UPDATE_FAILED';
export const ACTION_TASKS_CREATE = 'ACTION_TASKS_CREATE';
export const ACTION_TASKS_CREATE_FAILED = 'ACTION_TASKS_CREATE_FAILED';
export const ACTION_TASKS_DELETE = 'ACTION_TASKS_DELETE';
export const ACTION_TASKS_DELETE_FAILED = 'ACTION_TASKS_DELETE_FAILED';