import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from "react-router-dom";
import { yupResolver } from '@hookform/resolvers/yup';
import * as Yup from 'yup';
import { Link } from 'react-router-dom';
import '../assets/css/Signup.css';
import UserAPIs from '../assets/service/UserAPIs';

const schema = Yup.object({
  username: Yup.string().required('Email is required'),
  password: Yup.string().required('Password is required'),
}).required();


function Signin() {

  // Component State
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  // React Hook 
  const navigate = useNavigate();

  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  })
  const formSubmit = (data) => {
    
    console.log('Form Submitted');
    console.log(data);

    UserAPIs.signin(data)
      .then(res => {
        setError(false);
        sessionStorage.setItem('Authorization', res.headers.authorization);
        navigate('/')
      })
      .catch(err => {
        console.log(err);
        if (err.response.status === 401) {
          setError(true);
          setErrorMessage('Incorrect Username or Password')
        }
      })

  };


  return (
    <section className='wrapper fadeInDown'>
      <div id='formContent'>
        <form onSubmit={handleSubmit(formSubmit)}>
          <input
            type='text'
            className='fadeIn second in'
            placeholder='Email'
            {...register('username')}
          />
          <p>{errors.username?.message}</p>
          <input
            type='password'
            className='fadeIn second in'
            placeholder='Password'
            {...register('password')}
          />
          <p>{errors.password?.message}</p>
          <input type='submit' className='fadeIn fourth sub' value='Login' />
        </form>
        {error && <div className='error'>{errorMessage}</div>}
        <div>
          Don't have an account?&nbsp;
          <Link to='/sign-up'>Sign Up</Link>
        </div>

      </div>
    </section>
  );
}
export default Signin;
