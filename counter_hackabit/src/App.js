import React, {Component} from 'react';
// import logo from './logo.svg';
import Layout from './hoc/Layout/Layout';
import CounterList from './containers/counterList/counterList';
import './App.css';

class App extends Component {
  render () {
    return (
      <div>
        <Layout>
          <CounterList />
        </Layout>
      </div>
    );
  }
}

export default App;
