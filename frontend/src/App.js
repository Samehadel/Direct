import './App.css';

import './assets/vendor/bootstrap/css/bootstrap.min.css';
import './assets/css/style.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Landing from './Pages/Landing';
import Signin from './Pages/Signin';
import Signup from './Pages/Signup';
import Connections from './Pages/Connections';
import Requests from './Pages/Requests';
import Header from './Components/Header.jsx';
import AuthenticatedRoute from './Components/AuthenticatedRoute';
import Keywords from './Pages/Keywords';
import Network from './Pages/Network';
import Publish from './Pages/Publish';
import Inbox from './Pages/Inbox';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path='/' element={<Landing />} />
        <Route path='/sign-up' element={<Signup />} />
        <Route path='/sign-in' element={<Signin />} />
        <Route
          path='/connections'
          element={
            <AuthenticatedRoute>
              <Connections />
            </AuthenticatedRoute>
          }
        />
        <Route
          path='/requests'
          element={
            <AuthenticatedRoute>
              <Requests />
            </AuthenticatedRoute>
          }
        />
        <Route
          path='/keywords'
          element={
            <AuthenticatedRoute>
              <Keywords />
            </AuthenticatedRoute>
          }
        />
        <Route
          path='/network'
          element={
            <AuthenticatedRoute>
              <Network />
            </AuthenticatedRoute>
          }
        />
        <Route
          path='/publish'
          element={
            <AuthenticatedRoute>
              <Publish />
            </AuthenticatedRoute>
          }
        />
        <Route
          path='/inbox'
          element={
            <AuthenticatedRoute>
              <Inbox />
            </AuthenticatedRoute>
          }
        />
      </Routes>
    </Router>
  );
}
export default App;
