import React from 'react';
import { Navigate } from 'react-router-dom';

function AuthenticatedRoute({ children }) {
    const isLoggedIn = sessionStorage.getItem('Authorization') === null ? false : true;

    return isLoggedIn ? children : <Navigate to='/sign-in' /> ;
}
export default AuthenticatedRoute;