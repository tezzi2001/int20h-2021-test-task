package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.int20h2021.testtask.constant.Store.*;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;

    private final ItemRepository itemRepository;

    public boolean collect() {
        List<Item> items = new ArrayList<>();

        items.addAll(normalizrJsonService.normalize(getEcoMarketData(), ECOMARKET));
        items.addAll(normalizrJsonService.normalize(getMetroData(), METRO));
        items.addAll(normalizrJsonService.normalize(getNovusData(), NOVUS));
        items.addAll(normalizrJsonService.normalize(getAshanData(), ASHAN));
        items.addAll(normalizrJsonService.normalize(getVarusData(), VARUS));
        items.addAll(normalizrJsonService.normalize(getCityMarketData(), CITYMARKET));
        items.addAll(normalizrJsonService.normalize(getMegaMarketData(), MEGAMARKET));
        items.addAll(normalizrJsonService.normalize(getFurshetData(), FURSHET));

        itemRepository.deleteAll();
        itemRepository.saveAll(items);

        return true;
    }

    private ZakazResponse getMetroData() {
        return performZakazRequest(METRO_ID);
    }

    private ZakazResponse getEcoMarketData() {
        return performZakazRequest(ECOMARKET_ID);
    }

    private ZakazResponse getNovusData() {
        return performZakazRequest(NOVUS_ID);
    }

    private ZakazResponse getAshanData() {
        return performZakazRequest(ASHAN_ID);
    }

    private ZakazResponse getVarusData() {
        return performZakazRequest(VARUS_ID);
    }

    private ZakazResponse getCityMarketData() {
        return performZakazRequest(CITYMARKET_ID);
    }

    private ZakazResponse getMegaMarketData() {
        return performZakazRequest(MEGAMARKET_ID);
    }

    private ZakazResponse getFurshetData() {
        return performZakazRequest(FURSHET_ID);
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
