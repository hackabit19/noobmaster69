import React from 'react';
import logoLogo from './app_logo.png';
import './Logo.css';

// console.log(logoLogo);

const logo = (props) => {
    return (
        <div className="Logo" style={{height:props.height}}>
            <img src={logoLogo} alt="TodoList" />
        </div>
    )
}

export default logo;