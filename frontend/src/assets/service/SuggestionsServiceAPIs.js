import axios from 'axios';

class SuggestionsServiceAPIs {
  baseUrl = 'http://localhost:8082';

  retrieveSuggestedConnections() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl + '/profiles');
  }
}
export default new SuggestionsServiceAPIs();
