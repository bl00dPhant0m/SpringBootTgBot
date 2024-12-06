package ru.bl00dphant0m.springboottgbot.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.bl00dphant0m.springboottgbot.model.CurrencyData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class CBRService {
    private String url = "https://www.cbr-xml-daily.ru/daily_json.js";

    public List<CurrencyData> getCurrencies(){
        List<CurrencyData> currencies = new ArrayList<>();
        try {
            URL CBRurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) CBRurl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

             currencies =  parseCurrencies(response.toString());



            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return currencies;
    }



    private List<CurrencyData> parseCurrencies(String jsonResponse) {
        List<CurrencyData> currencies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject jsonObjectValute = jsonObject.getJSONObject("Valute");

            jsonObjectValute.keys().forEachRemaining(key->{
                try {
                    JSONObject jsonObjectCurrency = jsonObjectValute.getJSONObject(key.toString());


                    CurrencyData currency = new CurrencyData();
                    currency.setName(jsonObjectCurrency.getString("Name"));
                    currency.setCharCode(jsonObjectCurrency.getString("CharCode"));
                    currency.setValue(jsonObjectCurrency.getDouble("Value"));
                    currency.setPrevious(jsonObjectCurrency.getDouble("Previous"));

                    currencies.add(currency);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            return currencies;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    public CurrencyData getOneCurrency(String symbol){
        List<CurrencyData> currencies = getCurrencies();
        CurrencyData currencyData = currencies.stream().filter(currency -> currency.getCharCode().equals(symbol))
                .findFirst().orElse(null);
        return currencyData;
    }


}
