package com.int20h2021.testtask.service;

import com.int20h2021.testtask.constant.store.Stores;
import com.int20h2021.testtask.domain.json.common.Data;
import com.int20h2021.testtask.domain.json.common.entity.Item;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.int20h2021.testtask.constant.Urls.ZAKAZ_COMMON_REQUEST_URL;

@Service
@RequiredArgsConstructor
public class SearchAnyProductService {
    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;
    private final Stores stores;

    public Data getRozetkaResponse(String query) {
        ZakazResponse response = restTemplate.getForObject(ZAKAZ_COMMON_REQUEST_URL, ZakazResponse.class, 48215611, query);
        if (response == null || !response.hasPayload()) {
            return new Data(null, 0);
        }
        List<Item> itemsList = normalizrJsonService.normalize(response, stores.getStoresMap().get(48215611));
        return toItemsObj(itemsList);
    }

    private Data toItemsObj(List<Item> items) {
        Item[] itemsArray = items.toArray(new Item[0]);
        return new Data(itemsArray, 0);
    }
}
