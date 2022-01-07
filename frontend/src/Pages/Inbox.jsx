import React, { useEffect, useState } from 'react';
import PublicationsAPIs from '../assets/service/PublicationsAPIs';
import image from '../assets/img/team/team-3.jpg';
import '../assets/css/inbox.css';
import { Link } from 'react-router-dom';
import { ReactComponent as MarkAsReadSVG } from '../assets/img/icons/envelope-open-regular.svg';

function Inbox(props) {

    const [messages, setMessages] = useState([]);

    useEffect(() => {
        PublicationsAPIs.accessInboxPublications()
            .then(res => {
                console.log(res.data);
                setMessages(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    // Mark message as read
    const handleMarkAsRead = (publicationId) => {
        console.log(publicationId);

        const publication = messages.find(m => m.id === publicationId);


        PublicationsAPIs.markPublicationAsRead(publicationId, publication.read)
            .then(res => {
                console.log(res);

                const newMessages = messages.map(m => {
                    if (m.id === publicationId) {
                        m.read = !m.read
                    }

                    return m;
                })
                setMessages(newMessages);
            })
    }

    return (
        <section className='inbox-wrapper'>

            <div className="container">
                <h2>Inbox</h2>
                <div className="grid">
                    {messages.map(m =>
                        <div className="message-card" key={m.id} style={{ backgroundColor: !m.read ? "#e4ebe6" : 'white' }}>
                            <div className="info">
                                <img src={image} alt="" />
                                <div>
                                    <p>{m.senderFirstName.concat(" ", m.senderLastName)}</p>
                                    <p>Software Engineer - Demo</p>
                                </div>
                                <MarkAsReadSVG
                                    title={m.read ? 'mark as unread' : 'mark as read'}
                                    className='mark-read'
                                    style={{ color: !m.read ? ' #1034a6' : '#777' }}
                                    onClick={() => handleMarkAsRead(m.id)} />
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