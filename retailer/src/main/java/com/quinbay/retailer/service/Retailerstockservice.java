package com.quinbay.retailer.service;

import com.quinbay.retailer.functions.Retailerinventoryinterface;
import com.quinbay.retailer.kafka.KafkaPublisherService;
import com.quinbay.retailer.model.Finance;
import com.quinbay.retailer.model.Retailer;
import com.quinbay.retailer.model.Retailerinventory;
import com.quinbay.retailer.repository.Retailerrepository;
import com.quinbay.retailer.repository.Retailerstockrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class Retailerstockservice implements Retailerinventoryinterface {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    Retailerstockrepository rsr;

    @Autowired
    Retailerrepository rr;

    @Autowired
    KafkaPublisherService kafkaPublisherService;

    Finance currFinance = null;

    @Override
    @Cacheable(value = "listofretailersstock")
    public List<Retailerinventory> getAllRetailerStock() {
        System.out.println("\n\tHitting getAllRetailerStock <------------------");
        return rsr.findAll();
    }

    @Override
    public String payPendingAmount(String txnCode, int amount){
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8084/finance/payPendingAmount")
                .queryParam("txnCode", txnCode)
                .queryParam("amount", amount)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String s = restTemplate().exchange(url, HttpMethod.PUT, entity, String.class).getBody();
        return s;
    }

    @Override
    public String updateRetailerStock(Retailerinventory r, int amountpaid) {
//        Retailerinventory newStock = rsr.findByRetaileridAndProductidAndWholesalerid(r.getRetailercode(), r.getProductcode(), r.getWholesalercode());
        Optional<Retailer> tempRetailer = rr.findByRetailercode(r.getRetailercode());

        if (tempRetailer.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            String reduceurl = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/api/reduceStockFromWholesaler")
                    .queryParam("wCode", r.getWholesalercode())
                    .queryParam("pCode", r.getProductcode())
                    .queryParam("stock", r.getStock())
                    .toUriString();

            int result = restTemplate().exchange(reduceurl, HttpMethod.PUT, entity, Integer.class).getBody();

            if(result == 0){
                return "stock not available!!!!";
            }
            Retailerinventory newStock = rsr.findByRetailercodeAndProductcodeAndWholesalercode(r.getRetailercode(), r.getProductcode(), r.getWholesalercode());

            if (newStock != null) {
                newStock.setProductprice(r.getProductprice());
                newStock.setStock(r.getStock() + newStock.getStock());
                rsr.save(newStock);
                currFinance = new Finance("retailer",r.getRetailercode(), r.getWholesalercode(),
                        r.getProductcode(),r.getProductprice(), r.getStock(), amountpaid);
                kafkaPublisherService.sendRetailerForBilling(currFinance);
            } else {
                newStock = new Retailerinventory();
                newStock.setRetailercode(r.getRetailercode());
                newStock.setWholesalercode(r.getWholesalercode());
                newStock.setProductcode(r.getProductcode());
                newStock.setStock(r.getStock());
                newStock.setProductprice(r.getProductprice());
                newStock.setId(r.getId());

                rsr.save(newStock);
                currFinance = new Finance("retailer",r.getRetailercode(), r.getWholesalercode(),
                        r.getProductcode(),r.getProductprice(), r.getStock(), amountpaid);
                kafkaPublisherService.sendRetailerForBilling(currFinance);
            }
            return "Retailer stock updated!!!";
        } else {
            return "Retailer not found with the ID, create a retailer to continue!!!";
        }
    }
}
