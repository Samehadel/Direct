import axios from 'axios';

class UserAPIs {
  signup(signupModel) {
    const { firstName : first_name, lastName : last_name, username : username, password } = signupModel;
    console.log('Sign Up:', signupModel);
    return axios.post('http://localhost:8082/users/signup', {first_name, last_name, username, password});
  }

  signin(signinModel) {
    const { userName : username, password } = signinModel;
    console.log('Sign In:', {username, password});

    return axios.post('http://localhost:8082/login', signinModel);
  }
}
export default new UserAPIs();
