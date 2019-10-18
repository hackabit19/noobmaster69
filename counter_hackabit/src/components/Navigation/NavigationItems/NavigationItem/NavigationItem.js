import React from 'react'; //navlink
import './NavigationItem.css';

const navigationItem = (props) => {
    return (
        <li className="NavigationItem">
            <a herf ="/" activeClassName="active">{props.children}</a>
        </li>
    )
}

export default navigationItem;