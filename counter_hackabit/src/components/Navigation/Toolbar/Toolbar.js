import React from 'react';
import './Toolbar.css';
import NavigationItems from '../NavigationItems/NavigationItems';
import '../SideDrawer/DrawerToggle/DrawerToggle.css';
import Logo from '../../Logo/Logo';

const toolbar = (props) => {
    return (
        <header className="Toolbar">
            <div onClick={props.click} className="DrawerToggle">
                <div></div>
                <div></div>
                <div></div>
            </div>
            <Logo height="80%" />
            <nav className="DesktopOnly">
                <NavigationItems />
            </nav>
        </header>
    )
}

export default toolbar;