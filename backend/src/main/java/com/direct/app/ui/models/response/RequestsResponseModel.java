package com.direct.app.ui.models.response;

public class RequestsResponseModel {

    private long id;
    private SenderDetails senderDetails = new SenderDetails();

    public RequestsResponseModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SenderDetails getSenderDetails() {
        return senderDetails;
    }

    public void setSenderDetails(SenderDetails senderDetails) {
        this.senderDetails = senderDetails;
    }
}
