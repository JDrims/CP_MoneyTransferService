package ru.jdrims.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.jdrims.moneytransferservice.model.Amount;
import ru.jdrims.moneytransferservice.model.ConfirmOperation;
import ru.jdrims.moneytransferservice.model.OperationResponse;
import ru.jdrims.moneytransferservice.model.Transfer;
import ru.jdrims.moneytransferservice.repository.MoneyTransferRepository;
import ru.jdrims.moneytransferservice.service.MoneyTransferService;

@SpringBootTest
class MoneytransferserviceApplicationTests {
	static MoneyTransferService moneyTransferService;
	static MoneyTransferRepository moneyTransferRepository;

	@BeforeAll
    static void initional() {
		moneyTransferRepository = new MoneyTransferRepository(null);
		moneyTransferService = new MoneyTransferService(moneyTransferRepository);
	}

	@Test
	void serviceDoTransferTest() {
		Transfer transfer = new Transfer(
				"1111222233334444",
				"12/30",
				"123",
				"5555666677778888",
				new Amount(100.0f, "RUB")
		);
		OperationResponse id = moneyTransferService.doTransfer(transfer);
		Assertions.assertEquals(Integer.parseInt(id.getOperationId()), 1);
	}

	@Test
	void serviceDoConfirmOperationTest() {
		ConfirmOperation confirmOperation = Mockito.spy(ConfirmOperation.class);
		Mockito.when(Integer.parseInt(confirmOperation.getOperationId())).thenReturn(1);
		Mockito.when(confirmOperation.getCode()).thenReturn("0000");
		OperationResponse id = moneyTransferService.doConfirmOperation(confirmOperation);
		Assertions.assertEquals(Integer.parseInt(id.getOperationId()), 1);
	}
}
