package com.gamebox.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.FacebookAppInformationDao;
import com.gamebox.model.FacebookAppInformation;
import com.gamebox.service.FacebookAppInformationService;

@Service
public class FacebookAppInformationServiceImpl implements FacebookAppInformationService {

    @Autowired
    private FacebookAppInformationDao facebookAppInformationDao;
    
    @Override
    public FacebookAppInformation getFacebookAppInformation(Integer gameId) {

        return facebookAppInformationDao.findByGameId(gameId);
    }

    @Override
    public FacebookAppInformation getFacebookAppInformation(String gamename) {

        return facebookAppInformationDao.findByGameName(gamename);
    }
}
