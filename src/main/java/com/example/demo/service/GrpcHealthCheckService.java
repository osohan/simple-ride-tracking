package com.example.demo.service;

import com.example.grpc.health.v1.HealthCheck;
import com.example.grpc.health.v1.HealthGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcHealthCheckService extends HealthGrpc.HealthImplBase {

    public void check(HealthCheck.HealthCheckRequest request, StreamObserver<HealthCheck.HealthCheckResponse> responseObserver) {
        responseObserver.onNext(HealthCheck.HealthCheckResponse.newBuilder()
                .setStatus(HealthCheck.HealthCheckResponse.ServingStatus.SERVING)
                .build());
        responseObserver.onCompleted();
    }

}
