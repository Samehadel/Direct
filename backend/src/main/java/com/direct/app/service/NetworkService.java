package com.direct.app.service;

import com.direct.app.ui.models.response.ProfileResponseModel;

import java.util.Set;

public interface INetworkService {
    public Set<ProfileResponseModel> retrieveNetwork() throws Exception;

    public boolean removeConnection(long connectionId);
}
