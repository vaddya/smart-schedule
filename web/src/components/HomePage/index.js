import React, {Component} from 'react';
import {connect} from 'react-redux';
import NavBar from 'components/NavBar';
import {PageHeader} from 'react-bootstrap';

class HomePage extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <NavBar/>
                <PageHeader>Home</PageHeader>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({});

const mapDispatchToProps = (dispatch) => ({});

export default connect(mapStateToProps, mapDispatchToProps)(HomePage);