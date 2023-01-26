package com.quinbay.wholesaler.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.wholesaler.model.Finance;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void sendWholesalerForBilling(Finance finance){
        try{
            kafkaTemplate.send("send.finance", this.objectMapper.writeValueAsString(finance));
            System.out.println("\n\t sent kafka message!!!!!!!!!!!!!!!");
        }catch(Exception e){
            System.out.println("error is building "+ e);
        }
    }
}
