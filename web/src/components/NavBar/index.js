import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Nav, Navbar, NavItem} from 'react-bootstrap';

class NavBar extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Navbar collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="/">Smart Schedule</a>
                    </Navbar.Brand>
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavItem eventKey={1} href="/tasks" active={this.props.active === 'tasks'}>
                            Tasks
                        </NavItem>
                        <NavItem eventKey={2} href="/schedule" active={this.props.active === 'schedule'}>
                            Schedule
                        </NavItem>
                    </Nav>
                    <Nav pullRight>
                        <NavItem eventKey={1} href="/login">
                            Login
                        </NavItem>
                        <NavItem eventKey={2} href="/logout" style={{marginRight: 20}}>
                            Logout
                        </NavItem>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

const mapStateToProps = (state) => ({});

const mapDispatchToProps = (dispatch) => ({});

export default connect(mapStateToProps, mapDispatchToProps)(NavBar);