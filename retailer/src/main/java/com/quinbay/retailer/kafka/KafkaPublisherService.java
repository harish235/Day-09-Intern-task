package com.quinbay.retailer.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.retailer.model.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void sendRetailerForBilling(Finance finance){
        try{
            kafkaTemplate.send("send.finance", this.objectMapper.writeValueAsString(finance));
            System.out.println("\n\t sent kafka message!!!!!!!!!!!!!!!");
        }catch(Exception e){
            System.out.println("error is building "+ e);
        }
    }
}