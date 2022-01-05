import React, { useEffect, useState } from 'react';
import PublicationsAPIs from '../assets/service/PublicationsAPIs';
import image from '../assets/img/team/team-3.jpg';
import '../assets/css/inbox.css';
import { Link } from 'react-router-dom';

function Inbox(props) {

    const [messages, setMessages] = useState([]);

    useEffect(() => {
        PublicationsAPIs.accessInboxPublications()
            .then(res => {
                console.log(res.data[0].link);
                setMessages(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    return (
        <section className='inbox-wrapper'>

            <div className="container">
                <h2>Inbox</h2>
                <div className="grid">
                    {messages.map(m =>
                        <div className="message-card" key={m.id}>
                            <div className="info">
                                <img src={image} alt="" />
                                <div>
                                    <p>{m.senderFirstName.concat(" ", m.senderLastName)}</p>
                                    <p>Software Engineer - Demo</p>
                                </div>
                            </div>
                            <div className="message">
                                <div className="content">
                                    <h5>Job Description: </h5>
                                    <p>{m.content}</p>
                                </div>
                                <div className="link">
                                    <h5>Job Link: </h5>
                                    <a href={m.link} target='_blank'>{m.link}</a>
                                </div>
                            </div>
                        </div>)}
                </div>
            </div>
        </section>
    );
}
export default Inbox;