import React, { useState, useEffect } from 'react';
import ProfilesServiceAPIs from '../assets/service/ProfilesServiceAPIs';
import '../assets/css/profile.css';
import noImage from '../assets/img/no-image2.png';

function Profile(props) {

    const [details, setDetails] = useState({});

    useEffect(() => {
        console.log('use Effect');
        ProfilesServiceAPIs.getProfileImage()
        .then(res => {
            const d = {image_format: res.headers['content-type'], image_data: res.data}
            console.log(res);
            setDetails({image_data: res.data, image_format: res.headers['content-type']});
        });
    }, []);

    const uploadImage = (event) => {
        const uploadedImage = event.target.files[0];


        ProfilesServiceAPIs.editAccoutImage(uploadedImage)
            .then(res => {

            })
            .catch(err => {
                console.log(err);
            });
    }

    const checkImageNotExist = () => {
        return details === null || details.image_data == null || details.image_data == ''
    }

    return (
        <section className='proflie-wrapper'>
            <div className="container-xl">
                <form>
                    <label htmlFor="file-input" className='profile-imag'>
                        <img src={checkImageNotExist() ? noImage : 'data:' + details.image_format + ';base64,' + details.image_data} />            
                    </label>
                    <input type='file' accept='image/*' id='file-input' className='upload-image' onChange={uploadImage} />
                </form>
                <div className='info'>
                    <p>{details === null ? null : details.first_name + ' ' + details.last_name}</p>
                    <p>{details === null ? null : details.professional_title}</p>
                    <p>{ }</p>
                </div>
            </div>
        </section>
    );
}
export default Profile; 