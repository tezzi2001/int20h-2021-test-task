package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.domain.json.prom.Data;
import com.int20h2021.testtask.domain.json.prom.Product;
import com.int20h2021.testtask.domain.json.prom.PromResponse;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import com.int20h2021.testtask.domain.json.zakaz.Result;
import com.int20h2021.testtask.domain.json.rozetka.Good;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    static {
        JSON = init();
    }

    private static final String PROM_REQUEST_BODY_FILE = "promRequestBody.json";
    private static final String JSON;
    private static final int METRO_ID = 48215611;
    private static final int ECO_MARKET_ID = 48280214;
    private static final int NOVUS_ID = 48201070;

    private final RestTemplate restTemplate;

    @SneakyThrows
    private static String init() {
        String promRequestBody;
        Path path = Paths.get(BuckwheatDataCollectionService.class.getClassLoader().getResource(PROM_REQUEST_BODY_FILE).toURI());

        Stream<String> lines = Files.lines(path);
        promRequestBody = lines.collect(Collectors.joining(System.lineSeparator()));
        lines.close();

        return promRequestBody;
    }

    public RozetkaResponse getRozetkaData() {
        String url = "https://search.rozetka.com.ua/search/api/v4/?front-type=xl&text=крупа+гречана&lang=ua";
        return this.restTemplate.getForObject(url, RozetkaResponse.class);
    }

    public ZakazResponse getMetroData() {
        return performZakazRequest(METRO_ID);
    }

    public ZakazResponse getEcoMarketData() {
        return performZakazRequest(ECO_MARKET_ID);
    }

    public ZakazResponse getNovusData() {
        return performZakazRequest(NOVUS_ID);
    }

    public PromResponse getPromData() {
        String url = "https://prom.ua/graphql";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept-language", "en,uk-UA;q=0.9,uk;q=0.8,ru;q=0.7,en-US;q=0.6");
        headers.set("x-language", "uk");

        HttpEntity<String> entity = new HttpEntity<>(JSON, headers);

        ResponseEntity<PromResponse[]> response = this.restTemplate.postForEntity(url, entity, PromResponse[].class);

        return response.getBody()[2];
    }

    private ZakazResponse performZakazRequest(int storeId) {
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
            items.add(good.toItem("Rozetka"));
        }

        ZakazResponse metroData = getMetroData();
        addZakazItems(metroData, items, "Metro");

        ZakazResponse ecoMarketData = getEcoMarketData();
        addZakazItems(ecoMarketData, items, "EcoMarket");

        ZakazResponse novusData = getNovusData();
        addZakazItems(novusData, items, "Novus");

        PromResponse promData = getPromData();
        Data data = promData.getData();
        Product[] productList = data.getProductList();
        for (Product product : productList) {
            items.add(product.toItem("Prom"));
        }

        return new Items(items.toArray(new Item[0]));
    }

    private void addZakazItems(ZakazResponse zakazResponse, List<Item> items, String store) {
        Result[] results = zakazResponse.getResults();
        for (Result result : results) {
            items.add(result.toItem(store));
        }
    }
}
