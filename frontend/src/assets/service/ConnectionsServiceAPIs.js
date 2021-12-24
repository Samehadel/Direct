import axios from 'axios';

class ConnectionsServiceAPIs {
  baseUrl = 'http://localhost:8082';

  retrieveProfiles() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl + '/profiles');
  }

  sendConnectionRequest(receiverId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.post(this.baseUrl + '/connections/request', {
      senderId: null,
      receiverId: receiverId,
    });
  }

  accessConnectionRequests() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl + '/connections/requests');
  }

  acceptConnectionRequests(requestId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.put(
      this.baseUrl + `/connections/requests/accept/${requestId}`
    );
  }

  ignoreConnectionRequests(requestId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.delete(
      this.baseUrl + `/connections/requests/reject/${requestId}`
    );
  }

  retrieveNetwork() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(baseUrl + '/connections');
  }
}
export default new ConnectionsServiceAPIs();
