import axios from 'axios';

class KeywordsService {
  getAllKeywords(check) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return check ? axios.get('http://localhost:8082/keywords/subs') : 
                   axios.get('http://localhost:8082/keywords');
  }

  

  subscribe(keywordId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.post('http://localhost:8082/subscriptions/subscribe', {
      userId: null,
      keywordId,
    });
  }

  unsubscribe(keywordId) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.delete(
      `http://localhost:8082/subscriptions/drop/keyword/${keywordId}`
    );
  }
}
export default new KeywordsService();
