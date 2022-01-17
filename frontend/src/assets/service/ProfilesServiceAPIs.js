import axios from 'axios';

class ProfilesServiceAPIs {
  baseUrl = 'http://localhost:8082/profiles';

  editAccountInfo(profileDetails) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.put(this.baseUrl + '/details');
  }

  editAccoutImage(image) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    const formData = new FormData();

    const config = {
      headers: {
        'content-type': 'multipart/form-data',
      },
    };

    formData.append('image', image);

    return axios.post(this.baseUrl + '/image', formData, config);
  }

  getAccountDetails() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl + '/details');
  }
}
export default new ProfilesServiceAPIs();
