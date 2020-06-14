package com.example.demo.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName="ride_tracking")
public class RideTracking {

    @DynamoDBHashKey(attributeName="a")
    private String rideId;

    @DynamoDBRangeKey(attributeName="b")
    private Long timestamp;

    @DynamoDBAttribute(attributeName="c")
    private Float lat;

    @DynamoDBAttribute(attributeName="d")
    private Float lon;

}
