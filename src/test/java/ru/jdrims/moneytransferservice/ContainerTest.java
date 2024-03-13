package ru.jdrims.moneytransferservice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.jdrims.moneytransferservice.model.*;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    private static final GenericContainer<?> container = new GenericContainer<>("app:latest").withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @AfterAll
    public static void setDown() {
        container.stop();
    }

    @Test
    void containerDoTransferTest() {
        Transfer transfer = new Transfer(
                "",
                "",
                "",
                "",
                new Amount(100.0f, "RUB")
        );
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .postForEntity("http://localhost:"
                        + container.getMappedPort(5500)
                        + "/transfer",
                        transfer,
                        ErrorResponse.class
                );
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), 1);
    }

    @Test
    void containerDoConfirmOperationTest() {
        ConfirmOperation confirmOperation = new ConfirmOperation(null, null);
        confirmOperation.setOperationId("1");
        confirmOperation.setCode("0000");
        OperationResponse operationResponse = testRestTemplate
                .postForObject("http://localhost:"
                        + container.getMappedPort(5500)
                        + "/confirmOperation",
                        confirmOperation, OperationResponse.class
                );
        Assertions.assertEquals(Integer.parseInt(operationResponse.getOperationId()), 1);
    }
}
