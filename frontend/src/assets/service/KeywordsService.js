import axios from 'axios';

class KeywordsService {
  
  getAllKeywords(check) {
    console.log(check);
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };

    return check ? axios.get('http://localhost:8082/keywords/subs') : 
                   axios.get('http://localhost:8082/keywords');
  }

  

  subscribe(keyword_id) {
    console.log('br3')
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.post(`http://localhost:8082/subscriptions/subscribe?keyword_id=${keyword_id}`);
  }

  unsubscribe(keyword_id) {
    axios.defaults.headers.common = {
      Authorization: sessionStorage.getItem('Authorization'),
    };
    return axios.delete(
      `http://localhost:8082/subscriptions/unsubscribe?keyword_id=${keyword_id}`
    );
  }
}
export default new KeywordsService();
