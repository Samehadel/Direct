import React, { useState, useEffect } from 'react';
import image from '../assets/img/team/team-3.jpg';
import ConnectionsServiceAPIs from '../assets/service/ConnectionsServiceAPIs';
import '../assets/css/network.css';
import { useNavigate } from 'react-router-dom';
import noImage from '../assets/img/no-image.png';

function Network(props) {
    const [connections, setConnections] = useState([]);
    const [showDropdown, setShowDropdown] = useState({ id: 0, show: false });

    useEffect(() => {
        ConnectionsServiceAPIs.retrieveNetwork()
            .then(res => {
                console.log("Success", res);
                setConnections(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, [])

    const handleRemoveConnection = (connectionId) => {
        console.log(connectionId);
        ConnectionsServiceAPIs.removeConnection(connectionId)
            .then(res => {
                console.log(res);
                setConnections(connections.filter(conn => conn.id !== connectionId));
            })
            .catch(err => {
                console.log(err);
            })
    }

    return (
        <section className='network-wrapper'>
            <div className="container">
                <div className="connections">
                    {
                        connections.map(conn =>
                            <div className="conn-card" key={conn.id}>
                                <div className="left">
                                    <img src={conn.imageData === null ? noImage : 'data:' + conn.imageFormat + ';base64,' + conn.imageData} alt="" />
                                    <div>
                                        <p className='name'>{conn.firstName.concat(' ', conn.lastName)}</p>
                                        <p className='title'>{conn.professionalTitle === null ? null : conn.professionalTitle}</p>
                                    </div>
                                </div>
                                <div className="right">
                                    <button className='message'>Message</button>
                                    <button className='options' onClick={() => { setShowDropdown({ id: conn.id, show: !showDropdown.show }) }}></button>
                                    {(showDropdown.id === conn.id && showDropdown.show) && <ul className='dropdown'>
                                        <li onClick={() => handleRemoveConnection(conn.id)}>Remove Connection</li>
                                    </ul>}
                                </div>

                            </div>
                        )
                    }
                </div>
            </div>
        </section >
    );
}
export default Network;