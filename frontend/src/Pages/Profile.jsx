import React, { useState, useEffect } from 'react';
import ProfilesServiceAPIs from '../assets/service/ProfilesServiceAPIs';
import '../assets/css/profile.css';
import noImage from '../assets/img/no-image2.png';

function Profile(props) {

    const [details, setDetails] = useState(null);

    useEffect(() => {
        ProfilesServiceAPIs.getAccountDetails()
            .then(res => {
                console.log(res);
                setDetails(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    const uploadImage = (event) => {
        const uploadedImage = event.target.files[0];


        ProfilesServiceAPIs.editAccoutImage(uploadedImage)
            .then(res => {
                console.log(res);

            })
            .catch(err => {
                console.log(err);
            });
    }
    return (
        <section className='proflie-wrapper'>
            <div className="container-xl">
                <form>
                    <label htmlFor="file-input" className='profile-imag'>
                        <img src={details === null || details.imageData == null ? noImage : 'data:' + details.imageFormat + ';base64,' + details.imageData} />
                    </label>
                    <input type='file' accept='image/*' id='file-input' className='upload-image' onChange={uploadImage} />
                </form>
                <div className='info'>
                    <p>{details === null ? null : details.firstName.concat(' ', details.lastName)}</p>
                    <p>{details === null ? null : details.professionalTitle}</p>
                    <p>{ }</p>
                </div>
            </div>
        </section>
    );
}
export default Profile; 