package com.example.demo;

import com.example.grpc.tracking.RideTrackingServiceGrpc;
import com.example.grpc.tracking.TrackingDataOuterClass;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class DemoApplicationTests {

    @Test
    void contextLoads() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("77.83.172.180", 31432).usePlaintext().build();
        RideTrackingServiceGrpc.RideTrackingServiceStub stub = RideTrackingServiceGrpc.newStub(channel);
        StreamObserver<TrackingDataOuterClass.TrackingData> server = stub.track(new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {
                System.out.println("onNext");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });

        String rideId = UUID.randomUUID().toString();
        for (int i = 0; i < 10; i++) {
            server.onNext(TrackingDataOuterClass.TrackingData.newBuilder()
                    .setRideId(rideId)
                    .setTimestamp(Instant.now().toEpochMilli())
                    .setLat(0F)
                    .setLong(0F)
                    .build());
            Thread.sleep(1000);
        }

        server.onCompleted();

    }

}
