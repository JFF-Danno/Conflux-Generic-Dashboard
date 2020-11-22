import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import StakingApp from './StakingApp';
import Connect from './Connect';
import UnStakingApp from './UnStakingApp';
import reportWebVitals from './reportWebVitals';

ReactDOM.render( <App/>,  document.getElementById('send'));
ReactDOM.render( <StakingApp />,  document.getElementById('stake'));
ReactDOM.render( <Connect />,  document.getElementById('connect'));
ReactDOM.render( <UnStakingApp />,  document.getElementById('unstake'));

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
