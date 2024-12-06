package ru.bl00dphant0m.springboottgbot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.bl00dphant0m.springboottgbot.model.CryptoData;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {
    private String url = "https://api.coinpaprika.com/v1/tickers";

    public List<CryptoData> getCryptos(){
        List<CryptoData> cryptos = new ArrayList<>();
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

                cryptos =  parseCryptos(response.toString());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cryptos;
    }



    private List<CryptoData> parseCryptos(String jsonResponse) {
        List<CryptoData> cryptos = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonResponse);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject cryptoObject = jsonArray.getJSONObject(i);

            CryptoData crypto = new CryptoData();
            crypto.setName(cryptoObject.getString("name"));
            crypto.setSymbol(cryptoObject.getString("symbol"));
            crypto.setRank(cryptoObject.getInt("rank"));

            JSONObject additionalInfo = cryptoObject.getJSONObject("quotes").getJSONObject("USD");

            crypto.setPrice(additionalInfo.getDouble("price"));
            crypto.setAthPrice(additionalInfo.getDouble("ath_price"));
            crypto.setPercentChange24h(additionalInfo.getDouble("percent_change_24h"));
            crypto.setPercentFromAth(additionalInfo.getDouble("percent_from_price_ath"));

            cryptos.add(crypto);
        }

        return cryptos;

    }

    public CryptoData getOneCrypto(String symbol){
        List<CryptoData> cryptos = getCryptos();
        System.out.println(cryptos.get(0).getSymbol());

        CryptoData cryptoData = cryptos.stream().filter(crypto -> crypto.getSymbol().equals(symbol))
                .findFirst().orElse(null);

        return cryptoData;
    }
}
