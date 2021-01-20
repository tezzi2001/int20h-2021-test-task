package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import com.int20h2021.testtask.domain.json.zakaz.Result;
import com.int20h2021.testtask.domain.json.rozetka.Good;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataCollectionService {
    private final RestTemplate restTemplate;

    public RozetkaResponse getRozetkaData() {
        String url = "https://search.rozetka.com.ua/search/api/v4/?front-type=xl&text=крупа+гречана&lang=ua";
        return this.restTemplate.getForObject(url, RozetkaResponse.class);
    }

    public ZakazResponse getMetroData() {
        return performZakazRequest(48215611);
    }

    public ZakazResponse getEcoMarketData() {
        return performZakazRequest(48280214);
    }

    private ZakazResponse performZakazRequest(long storeId) {
        String url = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q=крупа+гречана";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("accept-language", "uk");
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ZakazResponse> response = this.restTemplate.exchange(url, HttpMethod.GET, request, ZakazResponse.class, storeId);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Items getAllData() {
        List<Item> items = new ArrayList<>();

        RozetkaResponse rozetkaData = getRozetkaData();
        Good[] goods = rozetkaData.getData().getGoods();
        for (Good good : goods) {
            items.add(good.toItem());
        }

        ZakazResponse metroData = getMetroData();
        Result[] metroResults = metroData.getResults();
        for (Result result : metroResults) {
            items.add(result.toItem());
        }

        ZakazResponse ecoMarketData = getEcoMarketData();
        Result[] ecoMarketResults = ecoMarketData.getResults();
        for (Result result : ecoMarketResults) {
            items.add(result.toItem());
        }

        return new Items(items.toArray(new Item[0]));
    }
}
