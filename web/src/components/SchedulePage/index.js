import React, {Component} from 'react';
import {connect} from 'react-redux';
import NavBar from 'components/NavBar';
import {PageHeader} from 'react-bootstrap';

class SchedulePage extends Component {

    render() {
        return (
            <div>
                <NavBar active={'schedule'}/>
                <PageHeader>Schedule</PageHeader>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    taskList: state.tasks.taskList
});

const mapDispatchToProps = (dispatch) => ({});

export default connect(mapStateToProps, mapDispatchToProps)(SchedulePage);