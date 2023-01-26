package com.quinbay.wholesaler.service;


import com.quinbay.wholesaler.functions.WholesalerInventoryInterface;
import com.quinbay.wholesaler.kafka.KafkaPublisherService;
import com.quinbay.wholesaler.model.Finance;
import com.quinbay.wholesaler.model.WarehouseInventory;
import com.quinbay.wholesaler.model.Wholesaler;
import com.quinbay.wholesaler.model.WholesalerInventory;
import com.quinbay.wholesaler.repository.WholesalerInventoryRepository;
import com.quinbay.wholesaler.repository.WholesalerRepository;
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
public class WholesalerInventoryService implements WholesalerInventoryInterface {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    WholesalerInventoryRepository wholesalerInventoryRepository;

    @Autowired
    WholesalerRepository wholesalerRepository;

    @Autowired
    KafkaPublisherService kafkaPublisherService;

    Finance currFinance = null;

    @Override
    @Cacheable(value = "listofwholesalersstock")
    public List<WholesalerInventory> getAllWholesalerStock() {
        System.out.println("\n\t Hitting getAllWholesalerStock");
        return wholesalerInventoryRepository.findAll();
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

//    @Override
//    public List<WholesalerInventory> getStockFromWholesaler(String wcode, String pcode){
//        List<WholesalerInventory> listOfWholesalers = wholesalerInventoryRepository.findByWholesaleridAndProductid(wcode, pcode);
//        int sum=0;
//        for(WholesalerInventory w: listOfWholesalers){
//            sum = sum+w.getStock();
//        }
//        return
//    }

    @Override
    public int reduceStockFromWholesaler(String wCode, String pCode, int stock){
        System.out.println("\n\t Entered reduce Stock from Wholesaler <------------------");
        List<WholesalerInventory> productList = wholesalerInventoryRepository.findByWholesaleridAndProductid(wCode, pCode);
        int availStock=0, tempStock, i=0;
        for(WholesalerInventory w: productList){
            availStock = availStock+ w.getStock();
        }
        System.out.println("\n\t available stock : -------> "+ availStock);
        if(availStock < stock){
            System.out.println("\n\t Stock not available:  <---------");
            return 0;
        }
        while (stock!=0){
            tempStock = productList.get(i).getStock();
            System.out.println("temp stock -------->" + tempStock);
            if(tempStock < stock){
                productList.get(i).setStock(0);
                stock = stock - tempStock;
                wholesalerInventoryRepository.save(productList.get(i));
            }
            else{
                productList.get(i).setStock(productList.get(i).getStock() -  stock);
                wholesalerInventoryRepository.save(productList.get(i));
                stock = 0;
            }
            i++;
        }
        return 1;
    }

    @Override
    public String updateWholesalerStock(WholesalerInventory w, int amountpaid) {
        Optional<Wholesaler> tempWholesaler = wholesalerRepository.findByWholesalercode(w.getWholesalerid());
        if (tempWholesaler.isPresent()) {

            // URL for getting stocking availability from warehouse
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/getStockFromWarehouse")
                    .queryParam("productid", w.getProductid())
                    .queryParam("warehouseid", w.getWarehouseid())
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            WarehouseInventory retrivedWarehouseStock = restTemplate().getForObject(url, WarehouseInventory.class);

            System.out.println("\n\t Product name -------> "+retrivedWarehouseStock.getProductname());
            System.out.println("\n\t Product price ------>"+retrivedWarehouseStock.getPrice());
            //URL for reducing the stocks from warehouse after purchase
            String reduceurl = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/reduceStockFromWarehouse")
                    .queryParam("warehousecode", w.getWarehouseid())
                    .queryParam("productcode", w.getProductid())
                    .queryParam("stock", w.getStock())
                    .toUriString();

            if(retrivedWarehouseStock.getStock() > w.getStock()){
                WholesalerInventory newStock = wholesalerInventoryRepository.findByWholesaleridAndWarehouseidAndProductid(w.getWholesalerid(), w.getWarehouseid(), w.getProductid());

                if (newStock != null) {
                    newStock.setProductprice(w.getProductprice());
                    newStock.setStock(w.getStock() + newStock.getStock());
                    wholesalerInventoryRepository.save(newStock);
                    String s = restTemplate().exchange(reduceurl, HttpMethod.PUT,entity, String.class).getBody();
                    currFinance = new Finance("wholesaler", w.getWholesalerid(), w.getWarehouseid(), w.getProductid(), w.getProductprice(), w.getStock(), amountpaid);
                    kafkaPublisherService.sendWholesalerForBilling(currFinance);
                } else {
                    WholesalerInventory temp = new WholesalerInventory();
                    temp.setWarehouseid(w.getWarehouseid());
                    temp.setWholesalerid(w.getWholesalerid());
                    temp.setProductid(w.getProductid());
                    temp.setStock(w.getStock());
                    temp.setProductprice(w.getProductprice());
                    temp.setId(w.getId());

                    wholesalerInventoryRepository.save(temp);
                    String s = restTemplate().exchange(reduceurl, HttpMethod.PUT, entity, String.class).getBody();
                    currFinance = new Finance("wholesaler", w.getWholesalerid(), w.getWarehouseid(), w.getProductid(), w.getProductprice(), w.getStock(), amountpaid);
                    kafkaPublisherService.sendWholesalerForBilling(currFinance);
                }
                return "Wholesaler stock updated!!!";
            }
            else{
                return "Try lesser product stock";
            }

        } else {
            return "Wholesaler with that ID does not exist, create a wholesaler with that ID!!!";
        }
    }
}
