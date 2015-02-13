package com.gamebox.service;

import com.gamebox.model.FacebookAppInformation;

public interface FacebookAppInformationService {
    
    public FacebookAppInformation findByGameId(Integer gameId);
    
    public FacebookAppInformation findByGameName(String gamename);
    
    public FacebookAppInformation findByIdentifier(String gamename);
}
