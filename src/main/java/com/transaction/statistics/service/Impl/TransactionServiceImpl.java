package com.transaction.statistics.service.Impl;

import com.transaction.statistics.exception.BadRequestException;
import com.transaction.statistics.exception.NoContentException;
import com.transaction.statistics.exception.UnprocessibleEntityException;
import com.transaction.statistics.model.Transaction;
import com.transaction.statistics.model.TransactionReport;
import com.transaction.statistics.service.TransactionService;
import com.transaction.statistics.utilities.CustomResponseCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private Queue<Transaction> transactions;

    private final int TRANSACTION_TIME_LIMIT = 30;

    public TransactionServiceImpl() {
        super();
        this.transactions = new ConcurrentLinkedQueue<Transaction>();
    }

    /**
     *
     * @param transaction
     * @throws BadRequestException
     * @throws NoContentException
     * @throws UnprocessibleEntityException
     * A synchronized add transaction method to add transaction and ensure the method is thread safe
     * hence the use of the synchronized key word.
     */
    @Override
    public synchronized void addTransaction(Transaction transaction) throws BadRequestException, NoContentException, UnprocessibleEntityException {

        if (transaction.getAmount() == null || transaction.getAmount().isEmpty()){
            throw new BadRequestException(CustomResponseCode.Bad_Request);
        }

        if (transaction.getTimestamp() == null || transaction.getTimestamp().isEmpty()){
            throw new BadRequestException(CustomResponseCode.Bad_Request);
        }

        //logic to convert transaction time in the ISO 8601 format received as a string to zone date time in utc
        ZonedDateTime transactionTime = Instant.parse(transaction.getTimestamp()).atZone(ZoneOffset.UTC);

        //logic to get current time in zone date time with utc zone off set
        ZonedDateTime currentTime = Instant.now().atZone(ZoneOffset.UTC);

        //logic to return 204 status code when transaction is older than 30 seconds
        if (transactionTime.isAfter(currentTime.plusSeconds(TRANSACTION_TIME_LIMIT))) {
            throw new NoContentException(CustomResponseCode.No_Content);
        }

        //logic to return 422 when transaction time is in the future(transaction time is ahead of current time
        if (transactionTime.isAfter(currentTime)){
            throw new UnprocessibleEntityException(CustomResponseCode.Unprocessable_Entity);
        }

        transactions.add(transaction);
    }

    /**
     * A method to generate statistical report from saved transactions made withing 3o seconds
     */
    @Override
    public TransactionReport getTransactionReport() {
        if (!transactions.isEmpty()) {
            DoubleSummaryStatistics summary = transactions.stream()
                    .filter(t -> Instant.parse(t.getTimestamp()).atZone(ZoneOffset.UTC).isAfter(Instant.now().atZone(ZoneOffset.UTC).minusSeconds(TRANSACTION_TIME_LIMIT)))
                    .collect(Collectors.summarizingDouble(value -> Double.parseDouble(value.getAmount())));
            return new TransactionReport(roundUpToTwoDecimalPlace(summary.getSum()), roundUpToTwoDecimalPlace(summary.getAverage()), roundUpToTwoDecimalPlace(summary.getMax()),
                    roundUpToTwoDecimalPlace(summary.getMin()), summary.getCount());
        } else {
            return new TransactionReport(null, null, null, null, 0L);
        }
    }

    /**
     * @param object
     * A simple logic to delete all transactions in the queue which accepts an empty request object
     */

    @Override
    public void deleteTransaction(Object object) {
            transactions.removeAll(transactions);
    }

    /**
     * A method that enable half round up of Double values returned from statistic summary
     * on saved transactions to two (2) decimal place and returns a string for response to client.
     */

    private String roundUpToTwoDecimalPlace(Double value){
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
    }


}
