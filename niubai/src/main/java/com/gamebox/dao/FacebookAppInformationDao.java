package com.gamebox.dao;

import com.gamebox.model.FacebookAppInformation;

public interface FacebookAppInformationDao {

    /**
     * 根据gameId查询
     * 
     * @param gameId
     * @return
     */
    public FacebookAppInformation findByGameId(Integer gameId);

    public FacebookAppInformation findByGameName(String gamename);
    
    public FacebookAppInformation findByIdentifier(String identifier);
}
