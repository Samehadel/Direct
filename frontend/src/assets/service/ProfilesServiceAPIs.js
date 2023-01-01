import axios from 'axios';

class ProfilesServiceAPIs {
  baseUrl = 'http://localhost:8082/profile';

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

    formData.append('image_file', image);
    return axios.post(this.baseUrl + '/image', formData, config);
  }

  getProfileImage() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    console.log(this.baseUrl + '/image')
    return axios.get(this.baseUrl + '/image');
  }
}

export default new ProfilesServiceAPIs();
