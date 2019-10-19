import React from 'react' ;
import './DrawerToggle.css';

const drawerToggle = (props) => {
    return (
        <div onclick={props.clicked}>
            MENU
        </div>
    )
}

export default drawerToggle;