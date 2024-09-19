package com.ufabc.api.client;

import com.ufabc.app.grpc.*;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DhtClient {
    private static final String target = "127.0.0.1:";
    private static final Logger logger = Logger.getLogger(DhtClient.class.getName());

    public static messageReceived store(Item item, String port) {
        ManagedChannel channel = Grpc.newChannelBuilder(target+port, InsecureChannelCredentials.create())
                .build();
        DHTGrpc.DHTBlockingStub dhtBlockingStub = DHTGrpc.newBlockingStub(channel);
        messageReceived messageReply = null;
        try {
            messageReply= dhtBlockingStub.store(item);
            logger.log(Level.INFO,"Item stored with sucess.");
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage());
        }
        return messageReply;
    }

    public static Item retrive(Item item, String port) {
        ManagedChannel channel = Grpc.newChannelBuilder(target+port, InsecureChannelCredentials.create())
                .build();
        DHTGrpc.DHTBlockingStub dhtBlockingStub = DHTGrpc.newBlockingStub(channel);
        Item itemResponse = null;
        try {
            itemResponse= dhtBlockingStub.retrive(item);
            logger.log(Level.INFO,"Item retrived with sucess.");
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage());
        }
        return itemResponse;
    }

}
