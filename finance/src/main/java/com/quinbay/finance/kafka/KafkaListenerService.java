package com.quinbay.finance.kafka;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.finance.api.TransactionsService;
import com.quinbay.finance.model.Payment;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TransactionsService ts;





    @KafkaListener(topics = "send.finance",groupId = "finance")
    public void getInvoiceForBilling(ConsumerRecord<?,String> consumerRecord){
        Payment payment = null;
        try {
            System.out.println("\n\tEntered kafka listener!!!!!!!!!!!!!!!");
            payment = objectMapper.readValue(consumerRecord.value(),
                    new TypeReference<Payment>() {
                    });

            System.out.println("\n\t--------->Entered<----------");
            System.out.println(payment.getProductcode());
            ts.enterPaymentInfo(payment);
        }catch (Exception e){

        }

    }
}
