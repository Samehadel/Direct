import axios from 'axios';

class PublicationsAPIs {
  baseUrl = 'http://localhost:8082/publications';

  publish(publicationModel) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.post(this.baseUrl + '/publish', publicationModel);
  }

  accessInboxPublications() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.get(this.baseUrl);
  }
}
export default new PublicationsAPIs();
