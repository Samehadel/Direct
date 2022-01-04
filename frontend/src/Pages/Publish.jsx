import React, { useState, useEffect } from 'react';
import KeywordsService from '../assets/service/KeywordsService';
import PublicationsAPIs from '../assets/service/PublicationsAPIs';
import '../assets/css/keywords.css';
import '../assets/css/publish.css';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as Yup from 'yup';

// Form Schema Definition
const schema = Yup.object({
    jobDescription: Yup.string(),
    jobLink: Yup.string().required('Link is required')
}).required();

function Publish() {

    // Component State
    const [keywords, setKeywords] = useState([]);
    const [result, setResult] = useState([]);
    const [selectedKeywords, setSelectedKeywords] = useState([]);
    const [query, setQuery] = useState('');
    const [isSuccess, setSucess] = useState(false);
    const [isFailed, setFailure] = useState(false);

    // Use React Hook Form
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    })

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

    const handleKeywordClick = (keywordId) => {

        const clickedKeyword = keywords.find(k => k.id === keywordId);

        if (!clickedKeyword.subscribed && selectedKeywords.length < 3) { // Checks if user selected this keyword before & if user picked less than 3 keywords
            let newResult = result.map(r => {
                if (r.id === keywordId) {
                    r.subscribed = true;
                    return r;
                } else {
                    return r;
                }
            })
            setResult(newResult);
            setSelectedKeywords([...selectedKeywords, clickedKeyword])
        } else {
            let newResult = result.map(r => {
                if (r.id === keywordId) {
                    r.subscribed = false;
                    return r;
                } else {
                    return r;
                }
            })
            setResult(newResult);
            setSelectedKeywords(selectedKeywords.filter(k => k.id !== keywordId));
        }
    }

    // Publish job post
    const handleJobPublish = (data) => {
        console.log('Publish');


        if (selectedKeywords.length > 0) { // Make sure user picked at least 1 keyword
            const publication = {
                content: data.jobDescription,
                link: data.jobLink,
                keywords: selectedKeywords.map(k => k.id)
            }

            PublicationsAPIs.publish(publication)
                .then(res => {
                    console.log(res);
                    setSucess(true);
                    setFailure(false);
                    setTimeout(() => window.location.reload(false), 2000);
                })
                .catch(err => {
                    console.log(err);
                })
        } else {
            setFailure(true)
        }

    }

    // Rendering
    return (
        <section className='inbox-wrapper'>

            <div className="container">
                <h3>Publish a job post to your network</h3>
                {isSuccess && <div className='success'>Publication Sent Successfully</div>}
                {isFailed && <div className='failure'>Pick at least 1 keyword</div>}
                <div className="col">
                    <div className="left">
                        <form onSubmit={handleSubmit(handleJobPublish)}>
                            <textarea
                                placeholder='Job Description'
                                cols="90"
                                rows="100"
                                className='in desc'
                                {...register('jobDescription')}></textarea>
                            <input
                                type="textarea"
                                placeholder='Job Link'
                                className='in'
                                {...register('jobLink')} />
                            <p>{errors.jobLink?.message}</p>
                            <input
                                type="submit"
                                value='Publish'
                                className='sub' />
                        </form>
                    </div>
                    <div className="right">
                        <p>Select at most 3 keywords</p>
                        <input type='text' placeholder='Search For Keyword . . .' onChange={handleChange} value={query} className='in' />
                        <div className='keywords'>
                            {result.map(k =>
                                <button
                                    style={{ backgroundColor: k.subscribed ? "#6edf5f" : '#bebebe' }}
                                    key={k.id}
                                    onClick={() => handleKeywordClick(k.id)} >
                                    {k.description}
                                </button>)
                            }
                        </div>
                    </div>
                </div>
            </div>
        </section >
    );
}
export default Publish;