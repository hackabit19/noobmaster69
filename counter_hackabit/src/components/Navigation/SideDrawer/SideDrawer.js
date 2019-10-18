import React from 'react';
import Logo from '../../Logo/Logo';
import NavigationItems from '../NavigationItems/NavigationItems';
import './SideDrawer.css';
import Aux from '../../../hoc/Au/Au';
import Backdrop from '../../UI/Backdrop/Backdrop';

const sideDrawer = (props) => {
    let attachedClasses = "SideDrawer Close";
    if(props.show) {
        attachedClasses = "SideDrawer Open";
    }

    return (
        <Aux>
            <Backdrop show={props.show} clicked={props.closed} />
            <div onClick={props.closed}>
                <div className={attachedClasses}>
                    <Logo height="11%" />
                    <nav>
                        <NavigationItems />
                    </nav>
                </div>
            </div>
        </Aux>
    )
}

export default sideDrawer;