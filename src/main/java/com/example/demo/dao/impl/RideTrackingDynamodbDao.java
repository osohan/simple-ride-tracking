package com.example.demo.dao.impl;

import com.example.demo.config.AmazonDynamoDBClient;
import com.example.demo.dao.RideTrackingDao;
import com.example.demo.entity.RideTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RideTrackingDynamodbDao implements RideTrackingDao {

    @Autowired
    private AmazonDynamoDBClient amazonDynamoDBClient;

    @Override
    public void save(RideTracking rideTracking) {
        amazonDynamoDBClient.getDynamoDBMapper().save(rideTracking);
    }
}
