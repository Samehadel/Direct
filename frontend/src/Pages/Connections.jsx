import React, { useState, useEffect } from 'react';
import ConnectionsServiceAPIs from './../assets/service/ConnectionsServiceAPIs';
import '../assets/css/connections.css';
import image from '../assets/img/team/team-3.jpg';
import { useNavigate } from 'react-router-dom';

function Connections(props) {

    const [profiles, setProfiles] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        ConnectionsServiceAPIs.retrieveProfiles()
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
        ConnectionsServiceAPIs.sendConnectionRequest(receiverId)
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
                            <img className='personal-img' src={image} alt="pic" />
                            <p>{p.firstName + " " + p.lastName}</p>
                            <button className='connect' onClick={() => sendConnectionRequest(p.id)}>Connect</button>
                        </div>
                    )}
                </div>
            </div>
        </section>
    );
}
export default Connections;