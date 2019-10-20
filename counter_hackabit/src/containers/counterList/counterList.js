import React, {Component} from 'react';
import './counterList.css';
import {db} from '../../firestore';
import Spinner from '../../components/UI/Spinner/Spinner';
import Aux from '../../hoc/Au/Au';
import ShoppingCard from '../../components/ShoppingCard/ShoppingCard';
import Button from '../../components/UI/Button/Button';
import Input from '../../components/UI/Input/Input';

class List extends Component {
    state = {
        list : {},
        priceList: {},
        loading: true,
        value: '',
        formDone: false
    }

    componentDidMount () {
        db.collection("counter").doc("1")
        .onSnapshot(doc => {
            // console.log(typeof doc.data()['id']);
            this.setState({value: doc.data()['id'].toString()});
        });
    }

    // textInput = React.createRef();

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

    // checkout() {
    //     console.log(typeof this.state.value, this.state.value);
    //     // e.preventDefault();
    //     // db.collection('barcode').doc(this.state.value).delete();
    //     db.collection("barcode").doc(this.state.value).delete().then(function() {
    //         console.log("Document successfully deleted!");
    //     }).catch(function(error) {
    //         console.error("Error removing document: ", error);
    //     });
    // }

    checkout = event => {
        if(this.state.value!==''){
        // event.preventDefault();
        db.collection("barcode").doc(this.state.value).delete().then(function() {
            console.log("Document successfully deleted!");
        }).catch(function(error) {
            console.error("Error removing document: ", error);
        });}
        

        db.collection("counter").doc("1").set({
            id: ''
        })
        .then(function() {
            console.log("Document successfully written!");
        })
        .catch(function(error) {
            console.error("Error writing document: ", error);
        });

        this.setState({formDone: false, value: ''});
        event.preventDefault();
    }

    fetchData(id) {
    // console.log("Gotcha:");
    // console.log(id);
    db.collection("prices")
        .get()
        .then(querySnapshot => {
        let priceMap ={};
        querySnapshot.docs.map(doc => {
            priceMap[doc.id]=priceMap[doc.id] || [];
            let ar = [doc.data().name,doc.data().price];
            priceMap[doc.id].push(ar);
            // console.log(doc);
        })
        // console.log(priceMap);
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
            // console.log("done");
            // console.log("Data: ",data);
        })
        .catch(error => {
            console.log("Error: ",error);
            this.setState({loading: false});
        })
    }

    render() {

        let bill = <Spinner />;

        if(this.state.loading===true && this.state.list) {
            bill = <Spinner />;
        }

      let totalPrice = 0;

    //   console.log(typeof this.state.list);
    //   console.log("loading:",this.state.loading);


      if(!this.state.loading && this.state.list) {
        // console.log("loading:",this.state.loading);
        // console.log("typeof:",typeof this.state.list);
        var ar = Object.keys(this.state.list);
        // console.log("PriceList:",this.state.priceList[item]["0"]["1"]);
        // console.log(ar);
        let checkout = ar.map(item => {
            if(this.state.priceList[item]) {
          const pric = this.state.priceList[item]["0"]["1"];
          const quan =  this.state.list[item];
        // var vec = JSON.stringify(this.state.priceList[item]);
        //   console.log(quan);
          const cost = pric*quan;
          totalPrice = totalPrice + cost;
        //   console.log(this.state.priceList[item]["0"]["0"],pric,quan);
          return <ShoppingCard
              key={this.state.priceList[item]["0"]["0"]}
              name={this.state.priceList[item]["0"]["0"]}
              price={pric}
              quantity={quan}
              cost={cost}
               />
            }
        })
        let foot = null;
        if(this.state.value!=='') {
        foot = (
          <div className="Footer">
            <div className="Empty"></div>
            <div className="PriceTag"><strong>Total Cost: {totalPrice}</strong></div>
            {/* <button onclick={(event) => this.checkout(event)}>Checkout</button> */}
            <div className="Logout"><a href="#" onClick={this.checkout}><strong>CHECKOUT==></strong></a></div>
          </div>
        )
        }

        bill = (
            <Aux>
              {checkout}
              {foot}
            </Aux>
          )
          // console.log(totalPrice);
        } else if(!this.state.list){
            bill = (
               <Aux>
                   <div className="Error">
                       <div className="Sad"><strong>:(</strong></div>
                       <div><strong><h4>Did not find what you were looking for!!</h4></strong></div>
                   </div>
               </Aux>
            );
        }

      let form = (
        <div className="InputForm" >
          <h1>Enter the Customer ID</h1>
          <form onSubmit={this.formHandler}>
              <Input 
                  value={this.state.value}
                  changed={this.changeHandler} />
              {/* <input 
                type="value" 
                autoFocus
                value={this.state.value} 
                onChange={this.changeHandler}/> */}
              <Button btnType="Success" disabled={this.state.value===''}>SUBMIT</Button>
              {/* <input type="submit" /> */}
          </form>

        </div>
      )

    //   let display = form;

    //   if(this.state.formDone!==false) {
    //       display = <Spinner />;
    //   }

    let display = null;

      if(!this.state.formDone) {
        display = form;
      } else {
        display = bill;
      }

        return (
            <div>
                {display}
            </div>
        )
    }
}

export default List;