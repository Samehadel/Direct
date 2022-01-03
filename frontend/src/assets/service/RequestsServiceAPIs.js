import axios from 'axios';

class RequestsServiceAPIs {
  baseUrl = 'http://localhost:8082/requests';

  sendConnectionRequest(receiverId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.post(this.baseUrl + '/send', {
      senderId: null,
      receiverId: receiverId,
    });
  }

  accessConnectionRequests() {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.get(this.baseUrl);
  }

  acceptConnectionRequests(requestId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.put(this.baseUrl + `/accept/${requestId}`);
  }

  ignoreConnectionRequests(requestId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.delete(this.baseUrl + `/reject/${requestId}`);
  }
}
export default new RequestsServiceAPIs();
