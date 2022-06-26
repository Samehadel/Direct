import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as Yup from 'yup';
import '../assets/css/Signup.css';
import UserAPIs from '../assets/service/UserAPIs';

// Form Schema Definition
const schema = Yup.object({
  firstName: Yup.string().required('First Name is required'),
  lastName: Yup.string().required('Last Name is required'),
  username: Yup.string().required('Email is required'),
  password: Yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
  confirmPassword: Yup.string().required('Password Confirmation is required')
    .oneOf([Yup.ref('password'), null], 'Passwords must match')
}).required();


function Signup() {

  // Component State
  const [sucess, setSuccess] = useState(false);
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  // Use React Hook Form
  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  })

  // Form Submission Code
  const formSubmit = (data) => {

    UserAPIs.signup(data)
      .then(res => {
        setError(false);
        setSuccess(true);
      })
      .catch(err => {
        setSuccess(false);
        setError(true);
        setErrorMessage(err.response.headers.message)
      })
  };


  return (
    <section className='wrapper fadeInDown'>

      <div id='formContent'>
        <form onSubmit={handleSubmit(formSubmit)}>
          <input
            type='text'
            className='fadeIn second in'
            placeholder='First Name'
            {...register('firstName')}
          />
          <p>{errors.firstName?.message}</p>
          <input
            type='text'
            className='fadeIn second in'
            placeholder='Last Name'
            {...register('lastName')}
          />
          <p>{errors.lastName?.message}</p>
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
          <input
            type='password'
            className='fadeIn second in'
            placeholder='Confirm Password'
            {...register('confirmPassword')}
          />
          <p>{errors.confirmPassword?.message}</p>
          <input type='submit' className='fadeIn fourth sub' value='Sign Up' />
        </form>
        {sucess && <div className='sucess'>Signed Up successfully <Link to='/sign-in'>Login</Link></div>}
        {error && <div className='error'>{errorMessage}</div>}
      </div>
    </section>
  );
}

export default Signup;
