import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/img/logo2.png';

function Header() {
  // Component State
  const [showLanding, setShowLanding] = useState(
    sessionStorage.getItem('Authorization') === null ? true : false
  );
  const [showControllers, setShowControllers] = useState(
    sessionStorage.getItem('Authorization') === null ? false : true
  );
  const [showDropdown, setShowDropdown] = useState(false);

  // React Hooks
  const navigate = useNavigate();

  return (
    <header id='header' className='header fixed-top'>
      <div className='container-fluid container-xl d-flex align-items-center justify-content-between'>
        <a className='logo d-flex align-items-center'>
          <img src={logo} alt='' />
          <span>Direct</span>
        </a>

        <nav id='navbar' className='navbar'>
          <ul className='list'>
            {showLanding && (
              <li>
                <a className='nav-link scrollto active' href='/'>
                  Home
                </a>
              </li>
            )}
            {showLanding && (
              <li>
                <a className='nav-link scrollto' href='#about'>
                  About
                </a>
              </li>
            )}
            {showLanding && (
              <li>
                <a className='nav-link scrollto' href='#contact'>
                  Contact
                </a>
              </li>
            )}
            {showControllers && (
              <li
                className='networking-dropdown'
                onClick={() => setShowDropdown(!showDropdown)}
              >
                Networking
              </li>
            )}
            {showDropdown && (
              <ul className='networking'>
                <li>
                  <a href='/connections'>Connections</a>
                </li>
                <li>
                  <a href='/requests'>Requests</a>
                </li>
                <li>
                  <a href='/network'>Network</a>
                </li>
              </ul>
            )}
            {showControllers && (
              <li>
                <a className='nav-link scrollto active' href='/keywords'>
                  Keywords
                </a>
              </li>
            )}
            {showControllers && (
              <li>
                <a className='nav-link scrollto' href='/publish'>
                  Publish
                </a>
              </li>
            )}
            {showControllers && (
              <li>
                <a className='nav-link scrollto' href='/inbox'>
                  Inbox
                </a>
              </li>
            )}
            {showControllers && (
              <li>
                <a className='nav-link scrollto' href='/profile'>
                  Profile
                </a>
              </li>
            )}
            {showLanding && (
              <li>
                <a className='getstarted scrollto' href='/sign-in'>
                  Sign In
                </a>
              </li>
            )}
            {showControllers && (
              <li>
                <button
                  className='getstarted scrollto'
                  onClick={() => {
                    sessionStorage.removeItem('Authorization');
                    setShowLanding(true);
                    setShowControllers(false);
                    navigate('/');
                  }}
                >
                  Log Out
                </button>
              </li>
            )}
          </ul>
          <i className='bi bi-list mobile-nav-toggle'></i>
        </nav>
      </div>
    </header>
  );
}
export default Header;
