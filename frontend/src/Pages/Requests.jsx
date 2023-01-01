import React, { useState, useEffect } from 'react';
import RequestsServiceAPIs from '../assets/service/RequestsServiceAPIs';
import '../assets/css/connections.css';
import { useNavigate } from 'react-router-dom';
import noImage from '../assets/img/no-image2.png';

function Requests(props) {

    const [requests, setRequests] = useState([]);

    // Use of react-router-dom
    const navigate = useNavigate();

    useEffect(() => {
        RequestsServiceAPIs.accessConnectionRequests()
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
        RequestsServiceAPIs.acceptConnectionRequests(requestId)
            .then(res => {
                console.log(res);
                setRequests(requests.filter(req => req.id !== requestId))
            })
            .catch(err => {
                console.log(err);
            });
    }

    const ignore = (requestId) => {
        RequestsServiceAPIs.ignoreConnectionRequests(requestId)
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
                            <img className='personal-img' src={req.sender_details.image_data === null ? noImage : 'data:' + req.sender_details.image_format + ';base64,' + req.sender_details.image_data} alt="pic" />
                            <div className="info">
                                <p>{req.sender_details.first_name + " " + req.sender_details.last_name}</p>
                                <p className='title'>{req.professional_title === null ? null : req.professional_title}</p>
                            </div>
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
