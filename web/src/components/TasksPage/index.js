import React, { Component } from 'react';
import { connect } from 'react-redux';
import NavBar from 'components/NavBar';
import { Badge, Button, Label, ListGroup, ListGroupItem, PageHeader } from 'react-bootstrap';
import TaskModal from 'components/TaskModal';
import { bindActionCreators } from 'redux';
import * as tasksActions from 'store/actions/tasks';
import dt from 'date-and-time';
import './style.sass'

const Task = ({task, isOverdue, onClick}) => (
    <ListGroupItem onClick={e => onClick(task)}>
        <span className={task.isComplete ? 'task-done' : 'task-active'}>{task.textTask}</span>
        <Label bsStyle={task.isComplete ? 'success' : (isOverdue ? 'danger' : 'primary')}>{task.deadline}</Label>
        <Badge>{task.subject + ' (' + task.type + ')'}</Badge>
    </ListGroupItem>
);

class TasksPage extends Component {

    constructor(props) {
        super(props);

        this.onCreateTaskClick = this.onCreateTaskClick.bind(this);
        this.onTaskClick = this.onTaskClick.bind(this);
    }

    static isOverdue(task) {
        return dt.parse(task.deadline, 'DD-MM-YYYY') < Date.now();
    }

    onCreateTaskClick() {
        this.modal.open(
            null,
            task => {
                this.props.tasksActions.createTask(task)
            }
        );
    }

    onTaskClick(task) {
        this.modal.open(
            task,
            task => {
                this.props.tasksActions.updateTask(task)
            },
            task => {
                this.props.tasksActions.removeTask(task);
            }
        );
    }

    render() {
        return (
            <div>
                <NavBar active={'tasks'}/>
                <PageHeader>
                    Tasks
                    <Button bsStyle="primary"
                            className="pull-right"
                            onClick={this.onCreateTaskClick}>
                        Create task
                    </Button>
                </PageHeader>
                {this.props.taskList.length
                    ? <ListGroup>
                        {this.props.taskList.map(task => (
                            <Task key={task.id}
                                  task={task}
                                  isOverdue={TasksPage.isOverdue(task)}
                                  onClick={this.onTaskClick}/>
                        ))}
                    </ListGroup>
                    : <h3>There are no tasks.</h3>}
                <TaskModal ref={modal => this.modal = modal}/>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    taskList: state.tasks.taskList
});

const mapDispatchToProps = (dispatch) => ({
    tasksActions: bindActionCreators(tasksActions, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(TasksPage);