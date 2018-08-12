import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';
import { createLogger } from 'redux-logger';
import { composeWithDevTools } from 'redux-devtools-extension';
import { routerMiddleware } from 'react-router-redux';
import reducers from './reducers/index.js';

export default (history) => {
    return createStore(
        combineReducers(reducers),
        composeWithDevTools(applyMiddleware(thunk, createLogger(), routerMiddleware(history)))
    );
};


