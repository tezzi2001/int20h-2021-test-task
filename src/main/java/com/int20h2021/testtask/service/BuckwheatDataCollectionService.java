package com.int20h2021.testtask.service;

import com.int20h2021.testtask.constant.store.Stores;
import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.int20h2021.testtask.constant.Urls.*;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;
    private final Stores stores;

    private final ItemRepository itemRepository;

    public boolean collect() {
        List<Item> items = new ArrayList<>();

        stores.getStoresMap().entrySet()
                .forEach(e -> items.addAll(getItemsForStore(e)));

        itemRepository.deleteAll();
        itemRepository.saveAll(items);

        return true;
    }

    private List<Item> getItemsForStore(Map.Entry<Integer, String> store) {
        return normalizrJsonService.normalize(performZakazRequest(store.getKey()), store.getValue());
    }

    private ZakazResponse performZakazRequest(int storeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptLanguage(Collections.singletonList(new Locale.LanguageRange("uk")));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<ZakazResponse> response = this.restTemplate.exchange(ZAKAZ_REQUEST_URL, HttpMethod.GET, request, ZakazResponse.class, storeId);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
