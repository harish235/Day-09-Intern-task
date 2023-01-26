package com.quinbay.finance.controller;

import com.quinbay.finance.api.BillService;
import com.quinbay.finance.api.TransactionsService;
import com.quinbay.finance.model.EmailDetails;
import com.quinbay.finance.model.Transaction;
import com.quinbay.finance.service.BillServiceImpl;
import com.quinbay.finance.service.Emailservice;
import com.quinbay.finance.service.TransactionsServiceImpl;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    TransactionsService ts;

    @PutMapping("/payPendingAmount")
    public String payPendingAmount(@RequestParam String txnCode, @RequestParam int amount){
        return ts.payPendingAmount(txnCode, amount);
    }

    @GetMapping("/viewTransactions")
    public List<Transaction> viewTransactions() {
        return ts.viewTransactions();
    }

    @GetMapping("/viewTransaction/{id}")
    public Object getTransaction(@PathVariable int id) {
        return ts.getTransaction(id);
    }


}
