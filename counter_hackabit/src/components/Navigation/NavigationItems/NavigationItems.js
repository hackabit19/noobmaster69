import React from 'react'; // navlinks
import './NavigationItems.css';
import NavigationItem from './NavigationItem/NavigationItem';

const NavigationItems = (props) => {
    return (
        <ul className="NavigationItems">
            <NavigationItem link="/"><strong>noobMaster69</strong></NavigationItem>
        </ul>
    )
}

export default NavigationItems;