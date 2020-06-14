package com.example.demo.service;

import com.example.demo.entity.RideTracking;
import com.example.grpc.tracking.TrackingDataOuterClass;
import org.springframework.stereotype.Service;

@Service
public class RideTrackingHelperService {

    public RideTracking convertToRideTrackingDynamodbEntity(TrackingDataOuterClass.TrackingData trackingData) {
        RideTracking rideTracking = new RideTracking();
        rideTracking.setRideId(trackingData.getRideId());
        rideTracking.setTimestamp(trackingData.getTimestamp());
        rideTracking.setLat(trackingData.getLat());
        rideTracking.setLon(trackingData.getLong());
        return rideTracking;
    }

}
