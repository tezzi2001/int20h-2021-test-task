package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.int20h2021.testtask.constant.Store.ROZETKA;
import static com.int20h2021.testtask.constant.Store.ROZETKA_COMMON_REQUEST_URL;

@Service
@RequiredArgsConstructor
public class SearchAnyProductService {
    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;

    public Items getRozetkaResponse(String query) {
        RozetkaResponse response = restTemplate.getForObject(ROZETKA_COMMON_REQUEST_URL, RozetkaResponse.class, query);
        if (response == null || !response.hasPayload()) {
            return new Items(null);
        }
        List<Item> itemsList = normalizrJsonService.normalize(response, ROZETKA);
        return toItemsObj(itemsList);
    }

    private Items toItemsObj(List<Item> items) {
        Item[] itemsArray = items.toArray(new Item[0]);
        return new Items(itemsArray);
    }
}
