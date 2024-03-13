package ru.jdrims.moneytransferservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.jdrims.moneytransferservice.model.ConfirmOperation;
import ru.jdrims.moneytransferservice.model.OperationResponse;
import ru.jdrims.moneytransferservice.model.Transfer;
import ru.jdrims.moneytransferservice.service.MoneyTransferService;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;

    @PostMapping("transfer")
    public OperationResponse doTransfer(@RequestBody Transfer transfer) {
        return moneyTransferService.doTransfer(transfer);
    }

    @PostMapping("confirmOperation")
    public OperationResponse doConfirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return moneyTransferService.doConfirmOperation(confirmOperation);
    }
}
