import React, { useState, useEffect } from 'react';
import ConnectionsServiceAPIs from './../assets/service/ConnectionsServiceAPIs';
import SuggestionsServiceAPIs from './../assets/service/SuggestionsServiceAPIs';
import RequestsServiceAPIs from './../assets/service/RequestsServiceAPIs';
import '../assets/css/connections.css';
import noImage from '../assets/img/no-image2.png';
import { useNavigate } from 'react-router-dom';

function Connections(props) {

    const [profiles, setProfiles] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        SuggestionsServiceAPIs.retrieveSuggestedConnections()
            .then(res => {
                console.log(res.data);
                setProfiles(res.data)
            })
            .catch(err => {
                console.log(err);
                if (err.response.status === 403) {
                    sessionStorage.removeItem('Authorization')
                    navigate('/sign-in')
                }
            })
    }, [])

    const sendConnectionRequest = (receiverId) => {
        console.log('Sending Request To: ', receiverId);
        RequestsServiceAPIs.sendConnectionRequest(receiverId)
            .then(res => {
                console.log(res);
                setProfiles(profiles.filter(p => p.id !== receiverId))
            })
            .catch(err => {
                console.log(err);
            })
    }
    return (
        <section className='secondary-wrapper'>

            <div className='container'>
                <h2 className='main-header'>Suggestions: Industry leaders in Egypt you may know</h2>
                <div className='grid-2'>
                    {profiles.map(p =>
                        <div className='card' key={p.id}>
                            <img className='personal-img' src={p.image_data === null ? noImage : 'data:' + p.image_format + ';base64,' + p.image_data} alt="pic" />
                            <p>{p.first_name + " " + p.last_name}</p>
                            <p className='title'>{p.professional_title === null ? null : p.professional_title}</p>
                            <button className='connect' onClick={() => sendConnectionRequest(p.id)}>Connect</button>
                        </div>
                    )}
                </div>
            </div>
        </section>
    );
}
export default Connections;