import React, { useState, useEffect } from 'react';
import image from '../assets/img/team/team-3.jpg';
import ConnectionsServiceAPIs from '../assets/service/ConnectionsServiceAPIs';
import '../assets/css/connections.css';
import { useNavigate } from 'react-router-dom';

function Requests(props) {

    const [requests, setRequests] = useState([]);

    // Use of react-router-dom
    const navigate = useNavigate();

    useEffect(() => {
        ConnectionsServiceAPIs.accessConnectionRequests()
            .then(res => {
                console.log(res);
                setRequests(res.data)
            })
            .catch(err => {
                console.log(err);
                if (err.response.status === 403) {
                    sessionStorage.removeItem('Authorization')
                    navigate('/sign-in')
                }
            })
    }, [])

    const accept = (requestId) => {
        ConnectionsServiceAPIs.acceptConnectionRequests(requestId)
            .then(res => {
                console.log(res);
                setRequests(requests.filter(req => req.id !== requestId))
            })
            .catch(err => {
                console.log(err);
            });
    }

    const ignore = (requestId) => {
        ConnectionsServiceAPIs.ignoreConnectionRequests(requestId)
            .then(res => {
                console.log(res);
                setRequests(requests.filter(req => req.id !== requestId))
            })
            .catch(err => {
                console.log(err);
            })
    }
    return (
        <section className='secondary-wrapper'>
            <div className="container">
                <h1 className='main-header'>Invitations</h1>
                <div className='requests grid-2'>
                    {requests.map(req =>
                        <div className='card' key={req.id}>
                            <img className='personal-img' src={image} alt="pic" />
                            <p>{req.firstName + " " + req.lastName}</p>
                            <button className='accept' onClick={() => accept(req.id)}>Accept</button>
                            <button className='ignore' onClick={() => ignore(req.id)}>Ignore</button>
                        </div>
                    )}
                </div>
            </div>
        </section>
    );
}
export default Requests;
