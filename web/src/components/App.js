import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as tasksActions from '../store/actions/tasks';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import TasksPage from 'components/TasksPage';
import HomePage from 'components/HomePage';
import PageNotFound from 'components/PageNotFound';
import SchedulePage from 'components/SchedulePage';

class App extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.props.tasksActions.retrieveTasks();
    }

    render() {
        return (
            <BrowserRouter>
                <Switch>
                    <Route exact path='/' component={HomePage}/>
                    <Route path='/tasks' component={TasksPage}/>
                    <Route path='/schedule' component={SchedulePage}/>
                    <Route component={PageNotFound}/>
                </Switch>
            </BrowserRouter>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        taskList: state.tasks.taskList
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        tasksActions: bindActionCreators(tasksActions, dispatch),
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(App);

