import React, {Component} from 'react';
import './counterList.css';
import {db} from '../../firestore';
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
      this.fetchData(this.state.value);
      // console.log("passing done OR not??");
      event.preventDefault();
    }

    changeHandler = event => {
      this.setState({value: event.target.value});
    }

    fetchData(id) {
    console.log("Gotcha:");
    console.log(id);
    db.collection("prices")
        .get()
        .then(querySnapshot => {
        let priceMap ={};
        querySnapshot.docs.map(doc => {
            priceMap[doc.id]=priceMap[doc.id] || [];
            let ar = [doc.data().name,doc.data().price];
            priceMap[doc.id].push(ar);
            console.log(doc);
        })
        console.log(priceMap);
        this.setState({ priceList: priceMap});
        })
        .catch(error => {
        this.setState({loading: true});
        console.log(error);
        })

        db.collection("barcode")
        .doc(id)
        .get()
        .then(doc => {
            const data = doc.data();
            this.setState({list: data, loading: false});
            console.log("done");
            console.log("Data: ",data);
        })
        .catch(error => {
            console.log("Error: ",error);
            this.setState({loading: true});
        })
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

      if(this.state.formDone!==false) {
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