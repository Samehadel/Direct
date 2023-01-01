import axios from 'axios';

class RequestsServiceAPIs {
  baseUrl = 'http://localhost:8082/requests';

  sendConnectionRequest(receiver_id) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return axios.post(this.baseUrl + `/send?receiver=${receiver_id}`);
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
