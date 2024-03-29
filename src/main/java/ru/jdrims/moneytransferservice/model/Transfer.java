package ru.jdrims.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transfer {
    @NotBlank(message = "неправильный номер карты отправителя")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardFromNumber;

    @NotBlank(message = "неправильный срок действия вашей карты")
    @Size(min = 5, max = 5)
    @Pattern(regexp = "(0[1-9]|1[0-2])[/][0-9]{2}")
    private String cardFromValidTill;

    @NotBlank(message = "CVC2/CVV2 обязателен для вашей карты")
    @Size(min = 3, max = 3, message = "CVC2/CVV2 обязателен для вашей карты")
    private String cardFromCVV;

    @NotBlank(message = "неправильный номер карты получателя")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardToNumber;

    private Amount amount;
}
