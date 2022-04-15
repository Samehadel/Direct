package com.direct.app.service;

import com.direct.app.shared.dto.ProfileDto;

import java.util.Set;

public interface NetworkService {
    public Set<ProfileDto> retrieveNetwork() throws Exception;

    public boolean removeConnection(long connectionId);
}