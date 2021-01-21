package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.domain.json.prom.PromResponse;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.int20h2021.testtask.constant.Store.*;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private static final String PROM_REQUEST_BODY_FILE = "src/main/resources/promRequestBody.json";
    private static final String JSON;

    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;

    static {
        JSON = init();
    }

    private static String init() {
        try (Scanner scanner = new Scanner(new File(PROM_REQUEST_BODY_FILE)).useDelimiter("\\A");) {
            return scanner.hasNext() ? scanner.next() : "";
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can not read JSON file with request for prom.ua");
        }
    }

    public Items getAllData() {
        List<Item> items = new ArrayList<>();

        items.addAll(normalizrJsonService.normalize(getEcoMarketData(), ECOMARKET));
        items.addAll(normalizrJsonService.normalize(getMetroData(), METRO));
        items.addAll(normalizrJsonService.normalize(getNovusData(), NOVUS));
        items.addAll(normalizrJsonService.normalize(getPromData(), PROM));
        items.addAll(normalizrJsonService.normalize(getRozetkaData(), ROZETKA));

        return new Items(items.toArray(new Item[0]));
    }

    private RozetkaResponse getRozetkaData() {
        return this.restTemplate.getForObject(ROZETKA_REQUEST_URL, RozetkaResponse.class);
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

    private PromResponse getPromData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Locale.LanguageRange> expectedRanges = Arrays.asList(
                new Locale.LanguageRange("en"),
                new Locale.LanguageRange("uk-UA", 0.9),
                new Locale.LanguageRange("uk", 0.8),
                new Locale.LanguageRange("ru", 0.7),
                new Locale.LanguageRange("en-US", 0.6)
        );
        headers.setAcceptLanguage(expectedRanges);
        headers.set("x-language", "uk");

        HttpEntity<String> entity = new HttpEntity<>(JSON, headers);

        ResponseEntity<PromResponse[]> response = this.restTemplate.postForEntity(PROM_REQUEST_URL, entity, PromResponse[].class);
        return response.getBody()[2];
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
