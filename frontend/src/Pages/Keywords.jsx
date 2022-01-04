import React, { useState, useEffect } from 'react';
import KeywordsService from '../assets/service/KeywordsService';
import '../assets/css/keywords.css';
import { useNavigate } from 'react-router-dom';

function Keywords() {

    // Component State
    const [keywords, setKeywords] = useState([]);
    const [result, setResult] = useState([]);
    const [query, setQuery] = useState('');

    // Use of react-router-dom
    const navigate = useNavigate();

    // APIs calls 
    useEffect(() => {
        KeywordsService.getAllKeywords()
            .then(res => {
                let data = res.data;
                console.log("data: ", data);
                setKeywords(data)
                setResult(data)
            })
            .catch(err => {
                console.log(err)
                if (err.response.status === 403) {
                    sessionStorage.removeItem('Authorization')
                    navigate('/sign-in')
                }
            })
    }, [])

    // Apply Search
    const handleChange = (event) => {
        const value = event.target.value;

        console.log('Search For:', value);
        const searchResult = value === '' ? keywords
            : keywords.filter(k => k.description.toLowerCase().includes(value.toLowerCase()));

        setResult(searchResult);
        setQuery(value);
    }

    const subscribe = (keywordId) => {

        console.log('Subscribe To: ', keywordId);
        if (keywords.find(k => k.id === keywordId).subscribed === true) { // User Already Subscibed: Remove Subscription
            KeywordsService.unsubscribe(keywordId)
                .then(res => {
                    let newResult = result.map(r => {
                        if (r.id === keywordId) {
                            r.subscribed = false;
                            return r;
                        } else {
                            return r;
                        }
                    })
                    setResult(newResult);
                })
                .catch(err => console.log(err))
        } else { // User Hasn't Subscibed: Add Subscription
            KeywordsService.subscribe(keywordId)
                .then(res => {
                    let newResult = result.map(r => {
                        if (r.id === keywordId) {
                            r.subscribed = true;
                            return r;
                        } else {
                            return r;
                        }
                    })
                    setResult(newResult);
                })
                .catch(err => console.log(err))
        }

    }

    // Rendering
    return (
        <section className='keywords-wrapper'>
            <input type='text' placeholder='Search For Keyword...' onChange={handleChange} value={query} className='in' />
            <div className='keywords'>
                {result.map(k =>
                    <button style={{ backgroundColor: k.subscribed ? "#6edf5f" : '#bebebe' }} key={k.id} onClick={() => subscribe(k.id)}>
                        {k.description}
                    </button>)
                }
            </div>
        </section >
    );

}

export default Keywords;