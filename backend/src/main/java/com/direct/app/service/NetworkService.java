package com.direct.app.service;

import com.direct.app.io.dto.ProfileDto;

import java.util.List;

public interface NetworkService {
    public List<ProfileDto> retrieveNetwork() throws Exception;

    public void removeConnection(Long connectionId) throws Exception;
}
