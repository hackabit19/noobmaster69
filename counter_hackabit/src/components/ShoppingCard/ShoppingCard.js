import React from 'react';
import './ShoppingList.css'

const shoppingCard = (props) => {
    return (
        <div className="ShoppingCard">
            <div className="name"><h2>{props.name}</h2></div>
            <div className="price">Price: {props.price}</div>
            <div className="quantity">Quantity: {props.quantity}</div>
            <div className="cost">Net Amount: {props.cost}</div>
        </div>
    )
}

export default shoppingCard;