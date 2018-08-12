import {
    ACTION_TASKS_CREATE,
    ACTION_TASKS_DELETE,
    ACTION_TASKS_RETRIEVE,
    ACTION_TASKS_UPDATE
} from 'store/actions/tasks';

const tasksInitialState = {
    taskList: []
};

const tasksReducer = (state = tasksInitialState, action) => {
    switch (action.type) {
        case ACTION_TASKS_RETRIEVE: {
            return {
                ...state,
                taskList: action.payload,
            }
        }
        case ACTION_TASKS_CREATE: {
            return {
                ...state,
                taskList: [...state.taskList, action.payload]
            }
        }
        case ACTION_TASKS_UPDATE: {
            return {
                ...state,
                taskList: state.taskList.map(task => task.id === action.payload.id ? action.payload : task)
            }
        }
        case ACTION_TASKS_DELETE: {
            return {
                ...state,
                taskList: state.taskList.filter(task => task.id !== action.payload.id)
            }
        }
        default: {
            return {
                ...state
            }
        }
    }
};

export default tasksReducer;

