import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import getStore from './store/store';
import App from './components/App';
import createHistory from 'history/createBrowserHistory';

const history = createHistory();
const store = getStore(history);

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('app')
);
