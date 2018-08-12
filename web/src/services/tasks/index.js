import axios from 'axios';
import uuidv4 from 'uuid/v4';

const tasks = [
    {
        'isComplete': true,
        'deadline': '16-07-2018',
        'id': 'f997509a-8cbd-43a4-a70c-a17dfd185361',
        'subject': 'Программирование',
        'textTask': 'Подключить DynamoDB',
        'type': 'PRACTICE'
    },
    {
        'isComplete': false,
        'deadline': '17-07-2018',
        'id': 'f997509a-8cbd-43a4-a70c-a17dfd185362',
        'subject': 'Программирование',
        'textTask': 'Изучить React & Redux',
        'type': 'LECTURE'
    },
    {
        'isComplete': false,
        'deadline': '13-07-2018',
        'id': 'f997509a-8cbd-43a4-a70c-a17dfd185363',
        'subject': 'Фронтэнд',
        'textTask': 'Изучить SASS',
        'type': 'PRACTICE'
    }
];

const getTaskById = async (id) => {
    return tasks.filter(task => task.id === id)[0];

    try {
        const resp = await axios({
            url: `/api/farm/{id}`,
            method: 'get',
            headers: {}
        });

        return resp.data;
    } catch (err) {
        console.log(err);
        return null;
    }
};

const getAllTasks = async () => {
    return tasks; // TEMP

    try {
        const resp = await axios({
            url: `http://localhost:8080/api/tasks`,
            method: 'get',
            headers: {}
        });

        return resp.data;
    } catch (err) {
        console.log(err);
        return null;
    }
};

const updateTaskById = async (task) => {
    return {}; // TEMP

    try {
        const resp = await axios({
            url: `/api/tasks`,
            method: 'post',
            data,
            headers: {}
        });

        return resp.data;
    } catch (err) {
        console.log(err);
        return null;
    }
};

const createNewTask = async (task) => {
    task.id = uuidv4();
    return task;

    try {
        const resp = await axios({
            url: `/api/tasks`,
            method: 'post',
            task,
            headers: {}
        });

        return resp.data;
    } catch (err) {
        console.log(err);
        return null;
    }
};

const removeTaskById = async (task) => {
    return {}; // TEMP

    try {
        const resp = await axios({
            url: `/api/tasks/${id}`,
            method: 'delete',
            headers: {}
        });

        return resp.data;
    } catch (err) {
        console.log(err);
        return null;
    }
};

export {
    getTaskById,
    getAllTasks,
    updateTaskById,
    createNewTask,
    removeTaskById,
};