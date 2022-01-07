package com.transaction.statistics.controller;

import com.transaction.statistics.exception.BadRequestException;
import com.transaction.statistics.exception.NoContentException;
import com.transaction.statistics.exception.UnprocessibleEntityException;
import com.transaction.statistics.model.Transaction;
import com.transaction.statistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/transactions")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) throws Exception {

        try {
            service.addTransaction(transaction);
            //returns 201 CREATED status code when transaction is successfully added.
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (NoContentException ex) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (UnprocessibleEntityException ex) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
