package com.example.demo.service;

import com.example.demo.dao.RideTrackingDao;
import com.example.demo.entity.RideTracking;
import com.example.grpc.tracking.RideTrackingServiceGrpc;
import com.example.grpc.tracking.TrackingDataOuterClass;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class RideTrackingService extends RideTrackingServiceGrpc.RideTrackingServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(RideTrackingService.class.getName());

    @Autowired
    private RideTrackingDao rideTrackingDao;

    @Autowired
    private RideTrackingHelperService rideTrackingHelperService;

    @Override
    public StreamObserver<TrackingDataOuterClass.TrackingData> track(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<TrackingDataOuterClass.TrackingData>() {
            @Override
            public void onNext(TrackingDataOuterClass.TrackingData trackingData) {
                logger.info(trackingData.getRideId() + " | " + trackingData.getTimestamp());
                saveRideTrackingData(trackingData);
            }
            @Override
            public void onError(Throwable throwable) {
                logger.error("error: " + throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                logger.info("onCompleted");
            }
        };
    }

    private void saveRideTrackingData(TrackingDataOuterClass.TrackingData trackingData) {
        RideTracking rideTracking = rideTrackingHelperService.convertToRideTrackingDynamodbEntity(trackingData);
        logger.info("saveRideTrackingData: " + rideTracking.toString());
        rideTrackingDao.save(rideTracking);
    }

}
