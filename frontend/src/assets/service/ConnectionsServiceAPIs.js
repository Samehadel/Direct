import axios from 'axios';

class ConnectionsServiceAPIs {
  baseUrl = 'http://localhost:8082/connections';

  retrieveSuggestedConnections() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl + '/profiles');
  }

  retrieveNetwork() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl);
  }

  removeConnection(connectionId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.delete(this.baseUrl + `/remove/${connectionId}`);
  }
}
export default new ConnectionsServiceAPIs();
