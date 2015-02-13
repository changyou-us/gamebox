package com.gamebox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gamebox.dao.FacebookAppInformationDao;
import com.gamebox.model.FacebookAppInformation;
import com.gamebox.service.FacebookAppInformationService;

@Service
public class FacebookAppInformationServiceImpl implements FacebookAppInformationService {

    @Autowired
    private FacebookAppInformationDao facebookAppInformationDao;
    
    @Cacheable("FacebookAppInformationFindByGameId")
    @Override
    public FacebookAppInformation findByGameId(Integer gameId) {

        return facebookAppInformationDao.findByGameId(gameId);
    }

    @Cacheable("FacebookAppInformationFindByGameName")
    @Override
    public FacebookAppInformation findByGameName(String gamename) {

        return facebookAppInformationDao.findByGameName(gamename);
    }
    
    @Cacheable("FacebookAppInformationFindByIdentifier")
    @Override
    public FacebookAppInformation findByIdentifier(String identifier) {

        return facebookAppInformationDao.findByIdentifier(identifier);
    }
}
