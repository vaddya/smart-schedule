import React, { Component } from 'react';
import { Button, ControlLabel, FormControl, FormGroup, Modal } from 'react-bootstrap';
import dt from 'date-and-time';
import Datetime from 'react-datetime'
import './style.sass'

class TaskModal extends Component {

    constructor(props) {
        super(props);

        this.open = this.open.bind(this);
        this.onInputChange = this.onInputChange.bind(this);
        this.onDateChange = this.onDateChange.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleRemove = this.handleRemove.bind(this);
        this.handleSave = this.handleSave.bind(this);

        this.types = [
            'LECTURE',
            'PRACTICE',
            'SEMINAR',
            'LAB',
            'TEST',
            'CONSULTATION',
            'EXAM',
            'ANOTHER'
        ];

        this.dateFormat = 'DD-MM-YYYY';

        this.emptyTask = () => ({
            subject: '',
            type: 'ANOTHER',
            deadline: dt.format(new Date(), this.dateFormat),
            textTask: '',
            isComplete: false
        });

        this.state = {
            show: false,
            creating: true,
            task: this.emptyTask(),
            date: new Date()
        };
    }

    open(task, onSave, onRemove) {
        task = task || this.emptyTask();
        this.setState({
            show: true,
            creating: typeof task.id === 'undefined',
            task: task,
            date: dt.parse(task.deadline, this.dateFormat),
            onSave: onSave,
            onRemove: onRemove
        });
    }

    onInputChange(e) {
        const value = e.target.value;
        const id = e.target.id;

        this.setState({
            task: Object.assign({}, this.state.task, {
                [id]: value
            }),
        });
    }

    onDateChange(e) {
        this.setState({
            date: e.toDate()
        });
    }

    handleClose() {
        this.setState({
            show: false
        });
    }

    handleShow() {
        this.setState({
            show: true
        });
    }

    handleSave() {
        this.state.task.deadline = dt.format(this.state.date, this.dateFormat);
        this.state.onSave(this.state.task);
        this.handleClose();
    }

    handleRemove() {
        this.state.onRemove(this.state.task);
        this.handleClose();
    }

    render() {
        return (
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.state.creating ? 'Create task' : 'Edit task'}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <FormGroup controlId="subject">
                            <ControlLabel>Subject</ControlLabel>
                            <FormControl type="text"
                                         placeholder="Enter subject"
                                         value={this.state.task.subject}
                                         onChange={this.onInputChange}/>
                        </FormGroup>
                        <FormGroup controlId="type">
                            <ControlLabel>Type</ControlLabel>
                            <FormControl componentClass="select"
                                         placeholder="select"
                                         value={this.state.task.type}
                                         onChange={this.onInputChange}>
                                {this.types.map(type => (
                                    <option key={type} value={type}>{type}</option>
                                ))}
                            </FormControl>
                        </FormGroup>
                        <FormGroup controlId="deadline">
                            <ControlLabel>Deadline</ControlLabel>
                            <Datetime dateFormat={this.dateFormat}
                                      timeFormat={false}
                                      closeOnSelect={true}
                                      value={this.state.date}
                                      onChange={this.onDateChange}/>
                        </FormGroup>
                        <FormGroup controlId="textTask">
                            <ControlLabel>Text</ControlLabel>
                            <FormControl componentClass="textarea"
                                         placeholder="Enter text"
                                         value={this.state.task.textTask}
                                         onChange={this.onInputChange}/>
                        </FormGroup>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle="primary" onClick={this.handleSave}>
                        {this.state.creating ? 'Create' : 'Save'}
                    </Button>
                    {this.state.creating === false &&
                    <Button bsStyle="danger" onClick={this.handleRemove}>
                        Remove
                    </Button>}
                    <Button bsStyle="default" onClick={this.handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default TaskModal;