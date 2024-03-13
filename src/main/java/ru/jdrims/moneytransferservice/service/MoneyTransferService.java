package ru.jdrims.moneytransferservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jdrims.moneytransferservice.exception.ErrorConfirmException;
import ru.jdrims.moneytransferservice.exception.InvalidDataException;
import ru.jdrims.moneytransferservice.logger.Logger;
import ru.jdrims.moneytransferservice.model.ConfirmOperation;
import ru.jdrims.moneytransferservice.model.OperationResponse;
import ru.jdrims.moneytransferservice.model.Transfer;
import ru.jdrims.moneytransferservice.repository.MoneyTransferRepository;

@Service
@AllArgsConstructor
public class MoneyTransferService {
    private final MoneyTransferRepository moneyTransferRepository;
    private final Logger logger = new Logger();

    public OperationResponse doTransfer(Transfer transfer) {
        checkValid(transfer);
        String transferId = moneyTransferRepository.addTransfer(transfer).toString();
        logger.log(transfer + ", operation id: " + transferId);
        return new OperationResponse(transferId);
    }

    public OperationResponse doConfirmOperation(ConfirmOperation confirmOperation) {
        checkValid(confirmOperation);
        Transfer transfer = moneyTransferRepository.doConfirmOperation(Integer.parseInt(confirmOperation.getOperationId()));
        if (transfer == null) {
            throw new ErrorConfirmException("Error confirm operation id: " + confirmOperation.getOperationId());
        } else {
            if (confirmOperation.getCode().equals("0000")) {
                logger.log(transfer + ", operation id: " + confirmOperation.getOperationId());
            } else {
                logger.log(transfer + ", Error confirm operation id: " + confirmOperation.getOperationId());
            }
        }
        return new OperationResponse(confirmOperation.getOperationId());
    }

    public void checkValid(Transfer transfer) {
        StringBuilder message = new StringBuilder();
        if (transfer.getCardFromNumber().isBlank()) {
            message = addTextAndSpace(message,"Invalid CardFromNumber");
        }
        if (transfer.getCardFromCVV().isBlank()) {
            message = addTextAndSpace(message,"Invalid CardFromCVV");
        }
        if (transfer.getCardFromValidTill().isBlank()) {
            message = addTextAndSpace(message,"Invalid CardFromValidTill");
        }
        if (transfer.getCardToNumber().isBlank()) {
            message = addTextAndSpace(message,"Invalid CardToNumber");
        }
        if (transfer.getCardFromNumber().equals(transfer.getCardToNumber())) {
            message = addTextAndSpace(message,"Invalid CardFromNumber is equal to CardToNumber");
        }
        if (!message.isEmpty()) {
            throw new InvalidDataException("Invalid transfer :" + message);
        }
    }

    public void checkValid(ConfirmOperation confirmOperation) {
        StringBuilder message = new StringBuilder();
        if (confirmOperation.getOperationId().isBlank()) {
            message = addTextAndSpace(message,"Invalid OperationId");
        }
        if (confirmOperation.getCode().isBlank()) {
            message = addTextAndSpace(message,"Invalid Code");
        }
        if (!message.isEmpty()) {
            throw new InvalidDataException("Invalid confirm :" + message);
        }
    }

    public StringBuilder addTextAndSpace(StringBuilder stringBuilder, String message) {
        if (!stringBuilder.isEmpty()) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(message);
        return stringBuilder;
    }
}
