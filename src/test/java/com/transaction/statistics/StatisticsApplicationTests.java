package com.transaction.statistics;

import com.google.gson.Gson;
import com.transaction.statistics.controller.TransactionController;
import com.transaction.statistics.model.Transaction;
import com.transaction.statistics.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StatisticsApplicationTests {

	@Autowired
	TransactionController controller;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@Autowired
	TransactionService transactionService;

	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}


	private List<Transaction> populateTransactionsToList() {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(new Transaction("4.000", Instant.now().toString()));
		transactionList.add(new Transaction("6.000", Instant.now().toString()));
		transactionList.add(new Transaction("5.0", Instant.now().toString()));
		transactionList.add(new Transaction("1.00", Instant.now().toString()));
		return transactionList;
	}

	@Test
	public void addTransactionsSuccessfullyTest() throws Exception {

		for(Transaction transaction : populateTransactionsToList()) {
			String transactionToJson = gson.toJson(transaction);
			mockMvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}
	}


	private List<Transaction> populateTransactionListWithTransactionDateInTheFuture() {
		List<Transaction> transactionsList = new ArrayList<Transaction>();
		transactionsList.add(new Transaction("430.000", Instant.now().plusSeconds(31).toString()));
		return transactionsList;
	}

	@Test
	public void throwExceptionAddingTransactionWithTransactionDateInTheFutureTest() throws Exception {

		for(Transaction transaction : populateTransactionListWithTransactionDateInTheFuture()) {
			String transactionToJson = gson.toJson(transaction);
			mockMvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void throwExceptionAddingTransactionWithTransactionDateAsNullTest() throws Exception {
		List<Transaction> transactionsList = new ArrayList<Transaction>();
		transactionsList.add(new Transaction("430.000", null));
		for(Transaction transaction : transactionsList) {
			String transactionToJson = gson.toJson(transaction);
			mockMvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}
	}

	@Test
	public void throwExceptionAddingTransactionWithTransactionAmountAsNullTest() throws Exception {
		List<Transaction> transactionsList = new ArrayList<Transaction>();
		transactionsList.add(new Transaction(null, Instant.now().toString()));
		for(Transaction transaction : transactionsList) {
			String transactionToJson = gson.toJson(transaction);
			mockMvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}
	}

}
