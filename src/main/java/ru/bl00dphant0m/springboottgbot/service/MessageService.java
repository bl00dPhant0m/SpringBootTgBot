package ru.bl00dphant0m.springboottgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bl00dphant0m.springboottgbot.model.CryptoData;
import ru.bl00dphant0m.springboottgbot.model.CurrencyData;

import java.util.List;

@Service
public class MessageService {
    private boolean flagCBR;
    private CBRService cbrService;
    private CryptoService cryptoService;

    public MessageService(@Autowired CBRService cbrService, @Autowired CryptoService cryptoService) {
        this.cbrService = cbrService;
        this.cryptoService = cryptoService;
    }


    public   String buildMessage( String textFromUser) {
//        CurrencyData currencyData;
//        if (flagCBR) {
//            currencyData = cbrService.getCurrencies()
//                    .stream().filter(e -> e.getName().equalsIgnoreCase(textFromUser))
//                    .findFirst()
//                    .orElse(null);
//
//        }
//

        if (textFromUser == null || textFromUser.isBlank()) {
            return "Неверный формат команды.";
        }
        textFromUser = textFromUser.trim();
        StringBuilder stringBuilder = new StringBuilder();
        if ("currency".equals(textFromUser)) {
            List<CurrencyData> currencies = cbrService.getCurrencies();
            currencies.forEach(currency -> stringBuilder.append(currency + "\n\n"));
        } else if ("crypto".equals(textFromUser)) {
            List<CryptoData> cryptos = cryptoService.getCryptos();
            cryptos.stream().limit(25)
                    .forEach(crypto -> stringBuilder.append(crypto + "\n\n"));
        } else if (textFromUser.startsWith("currency_")) {
            String message = messageWithOneCurrency(textFromUser);
            stringBuilder.append(message);
        } else if (textFromUser.startsWith("crypto_")) {
            String message = messageWithOneCrypto(textFromUser);
            stringBuilder.append(message);
        } else {
            return "Неверный формат команды.";
        }
        return stringBuilder.toString();
    }

    private String messageWithOneCurrency(String textFromUser) {
        String[] split = textFromUser.split("_");
        if (split.length == 2) {
            String symbol = split[1];
            System.out.println(symbol);
            CurrencyData currencyData = cbrService.getOneCurrency(symbol);
            if (currencyData == null) {
                return "Валюты с кодом " + symbol + " не найдено.";
            } else {
                return currencyData.toString();
            }
        } else {
            return "Неверный формат команды.";
        }
    }

    private String messageWithOneCrypto(String textFromUser) {
        String[] split = textFromUser.split("_");
        if (split.length == 2) {
            String symbol = split[1];
            CryptoData cryptoData = cryptoService.getOneCrypto(symbol);
            if (cryptoData == null) {
                return "Криптовалюты с кодом " + symbol + " не найдено.";
            } else {
                return cryptoData.toString();
            }
        } else {
            return "Неверный формат команды.";
        }
    }


}
