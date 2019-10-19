import React, {Component} from 'react';
// import './counterList.css';
import Spinner from '../../components/UI/Spinner/Spinner';
// import Aux from '../hoc/Au/Au';

class List extends Component {
    state = {
        list : {},
        priceList: {},
        loading: true,
        value: '',
        formDone: false
    }

    formHandler = event => {
      this.setState({formDone: true});
    //   console.log(this.state.value);
    //   this.fetchData(this.state.value);
      // console.log("passing done OR not??");
      event.preventDefault();
    }

    changeHandler = event => {
      this.setState({value: event.target.value});
    }

    render() {

      let form = (
        <div className="InputForm" >
          <h1>Enter the Customer ID</h1>
          <form onSubmit={this.formHandler}>
              {/* <Input 
                  value={this.state.value}
                  changed={this.changeHandler} /> */}
              <input 
                type="value" 
                value={this.state.value} 
                onChange={this.changeHandler}/>
              {/* <button btnType="Success" disabled={this.state.value===''}>SUBMIT</button> */}
              <input type="submit" />
          </form>

        </div>
      )

      let display = form;

      if(this.state.formDone!=false) {
          display = <Spinner />;
      }

        return (
            <div>
                {display}
            </div>
        )
    }
}

export default List;