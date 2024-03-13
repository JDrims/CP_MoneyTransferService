package ru.jdrims.moneytransferservice.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.jdrims.moneytransferservice.model.Transfer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class MoneyTransferRepository {
    private final Map<Integer, Transfer> transferList;
    private final AtomicInteger id = new AtomicInteger(0);

    public Integer addTransfer(Transfer transfer) {
        Integer transferId = id.incrementAndGet();
        transferList.put(transferId, transfer);
        return transferId;
    }

    public Transfer doConfirmOperation(Integer transferId) {
        if (transferList.containsKey(transferId)) {
            Transfer transfer = transferList.get(transferId);
            transferList.remove(transferId);
            return transfer;
        }
        return null;
    }
}
