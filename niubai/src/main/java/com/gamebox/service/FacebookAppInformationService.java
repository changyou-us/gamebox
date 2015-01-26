package com.gamebox.service;

import com.gamebox.model.FacebookAppInformation;

public interface FacebookAppInformationService {
    
    public FacebookAppInformation getFacebookAppInformation(Integer gameId);
    
    public FacebookAppInformation getFacebookAppInformation(String gamename);
}
