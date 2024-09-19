package com.ufabc.api.controller;
import com.ufabc.api.client.DhtClient;
import com.ufabc.app.grpc.Item;
import com.ufabc.app.grpc.messageReceived;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    // GET endpoint to retrieve data by ID
    @GetMapping("/data/{id}")
    public ResponseEntity<String> getDataById(@PathVariable String id, @RequestParam int port) {
        Item itemRequest = Item.newBuilder().setKeyItemHash(id).build();
        Item item = DhtClient.retrive(itemRequest, String.valueOf(port));
        if (item != null) {
            return ResponseEntity.ok(item.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    // POST endpoint to add new data
    @PostMapping("/data")
    public ResponseEntity<String> addData(@RequestParam String id, @RequestParam String value, @RequestParam int port) {
        Item itemRequest = Item.newBuilder().setKeyItemHash(id).setValueItem(value).build();
        messageReceived messageReceived = DhtClient.store(itemRequest, String.valueOf(port));
        if (messageReceived != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Data added successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding data");
    }
}

