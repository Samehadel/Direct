import React from "react";
import hero from '../assets/img/hero-img.png';

function Landing(){
    return(
    <section id="hero" className="hero">

    <div className="container">
      <div className="row">
        <div className="col-lg-6 d-flex flex-column justify-content-center">
          <h1>A platform to meet supportive and collaborative peers</h1>
          <h2 >Build connections and get personalized job posts from industry job
            hunters in your network</h2>
          <div >
            <div className="text-center text-lg-start">
              <a href="/sign-up"
                className="btn-get-started scrollto d-inline-flex align-items-center justify-content-center align-self-center">
                <span>Sign Up</span>
                <i className="bi bi-arrow-right"></i>
              </a>
            </div>
          </div>
        </div>
        <div className="col-lg-6 hero-img">
          <img src={hero} className="img-fluid" alt=""/>
        </div>
      </div>
    </div>

  </section>
    );
}
export default Landing;