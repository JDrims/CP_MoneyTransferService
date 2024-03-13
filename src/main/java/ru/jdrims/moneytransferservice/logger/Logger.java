package ru.jdrims.moneytransferservice.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public void log(String messageLog) {
        try {
            FileWriter fileWriter = new FileWriter("transferMoney.log", true);
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.nnn"));
            fileWriter.append("[" + date + " " + time + "] --- " + messageLog + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Log Error: " + e.getMessage());
        }
    }
}
