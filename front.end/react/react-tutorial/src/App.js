import logo from './logo.svg';
import './App.css';
import React, {Component} from 'react'
import Table from './Table'


class App extends Component {
  render() {
    return (
			<div className="container">
        <Table />
      </div>						
    )
  }
}

export default App;
