package com.quinbay.finance.service;

import com.quinbay.finance.api.TransactionsService;
import com.quinbay.finance.model.Finance;
import com.quinbay.finance.model.Payment;
import com.quinbay.finance.model.Transaction;
import com.quinbay.finance.repository.FinanceRepository;
import com.quinbay.finance.repository.TransactionsRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionsRepository transactionsRepository;
    TransactionsServiceImpl( TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Autowired
    BillServiceImpl billService;

    @Autowired
    FinanceRepository financeRepository;

    @Override
    public String enterPaymentInfo(Payment f) {

        int total = f.getProductprice() * f.getQty();
        int balance = total - f.getAmountpaid();
        Date date = new Date();
        String txnId = f.getBuyercode()+f.getSellercode()+f.getProductcode();
        Finance newFinance = new Finance(2, txnId, f.getBuyertype(), f.getBuyercode(), f.getSellercode(), f.getProductcode(), f.getProductprice(), f.getQty(), total, balance, date);
        Transaction newTransaction = new Transaction(1, txnId, f.getBuyertype(), f.getBuyercode(), f.getSellercode(), f.getProductcode(), f.getProductprice(), f.getQty(), total, balance, date);
        financeRepository.save(newFinance);
        transactionsRepository.save(newTransaction);
        billService.generatePdf(newTransaction);
        System.out.println("\n\tData added to transaction table!!!!!!!!!!!!");
        return "Transaction added";
    }

    @Override
    public String payPendingAmount(String txncode, int amount){
        System.out.println("Reached payPendingAmount!!!!!!!!!!!!!!!");
        Optional<Finance> financeOfCode = financeRepository.findByTxncode(txncode);
        if(financeOfCode.get().getBalance() < amount){
            return "Your trying to pay more than the actual balance, the balance due amount is : "+financeOfCode.get().getBalance();
        }

        Finance f = financeOfCode.get();
        int bal = f.getBalance() - amount;
        f.setBalance(bal);
        financeRepository.save(f);

        Transaction newTxn = new Transaction(1, txncode, f.getBuyertype(), f.getBuyercode(), f.getSellercode(), f.getProductcode(), f.getProductprice(), f.getProductqty(), f.getTotalamount(), bal, f.getDate());
        transactionsRepository.save(newTxn);
        if(bal == 0){
            financeRepository.deleteById(f.getId());
            return "Due cleared Congrats!!!!";
        }
        return "Paid the amount!!!!";
    }

    @Override
    public List<Transaction> viewTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public Object getTransaction(int id) {
        Optional<Transaction> opt = transactionsRepository.findById(id);
        if(opt!=null) {
            return transactionsRepository.findById(id);
        }
        return "Transaction not found";
    }
}
