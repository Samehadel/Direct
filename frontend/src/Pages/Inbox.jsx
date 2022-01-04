import React, { useEffect, useState } from 'react';
import PublicationsAPIs from '../assets/service/PublicationsAPIs';

function Inbox(props) {

    const [messages, setMessages] = useState([]);

    useEffect(() => {
        PublicationsAPIs.accessInboxPublications()
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    return (
        <section>

        </section>
    );
}
export default Inbox;