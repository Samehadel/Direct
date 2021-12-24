import axios from 'axios';

class UserAPIs {
  signup(signupModel) {
    console.log('Sign Up:', signupModel);
    return axios.post('http://localhost:8082/users/signup', signupModel);
  }

  signin(signinModel) {
    console.log('Sign In:', signinModel);
    return axios.post('http://localhost:8082/login', signinModel);
  }
}
export default new UserAPIs();
