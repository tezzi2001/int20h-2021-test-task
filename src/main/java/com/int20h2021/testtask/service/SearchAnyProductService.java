package com.int20h2021.testtask.service;

import com.int20h2021.testtask.constant.store.Stores;
import com.int20h2021.testtask.domain.PagedBasedOffsetRequest;
import com.int20h2021.testtask.domain.entity.Item;
import com.int20h2021.testtask.domain.json.common.item.Data;
import com.int20h2021.testtask.domain.json.common.item.Filter;
import com.int20h2021.testtask.domain.json.common.item.FilterOption;
import com.int20h2021.testtask.domain.json.common.item.ItemJson;
import com.int20h2021.testtask.domain.json.zakaz.Result;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.int20h2021.testtask.constant.Urls.ZAKAZ_COMMON_REQUEST_URL;

@Service
@RequiredArgsConstructor
public class SearchAnyProductService {
    private final RestTemplate restTemplate;
    private final NormalizrJsonService normalizrJsonService;
    private final Stores stores;

    public Data getData(String query, int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters, int storeId) {
        PagedBasedOffsetRequest pagedBasedOffsetRequest = new PagedBasedOffsetRequest(offset, limit);
        pagedBasedOffsetRequest.convert();
        offset = pagedBasedOffsetRequest.getNewOffset();

        int[] zakazPageNumbers = pagedBasedOffsetRequest.getZakazPageNumbers();

        ZakazResponse[] zakazResponses = getResponsesWithAllPages(zakazPageNumbers, query, filters, storeId);

        List<Item> itemsList = subItemsList(getItemsList(zakazResponses, storeId), offset, limit);
        Data data = toData(itemsList, getTotalCount(zakazResponses));
        setFilters(data, getAnyZakazResponse(zakazResponses));

        return data;
    }

    private ZakazResponse[] getResponsesWithAllPages(int[] zakazPageNumbers, String query, MultiValueMap<String, String> filters, int storeId) {
        ZakazResponse[] zakazResponses = new ZakazResponse[zakazPageNumbers.length];
        for (int i = 0, zakazPageNumbersLength = zakazPageNumbers.length; i < zakazPageNumbersLength; i++) {
            ZakazResponse zakazResponse = performZakazRequest(storeId, query, getFiltersString(filters), "&page=" + zakazPageNumbers[i]);
            if (zakazResponse == null || !zakazResponse.hasPayload()) {
                zakazResponses[i] = null;
            } else {
                zakazResponses[i] = zakazResponse;
            }
        }

        return Arrays.stream(zakazResponses)
                .filter(Objects::nonNull).toArray(ZakazResponse[]::new);
    }

    private ZakazResponse getAnyZakazResponse(ZakazResponse[] zakazResponses) {
        return Arrays.stream(zakazResponses)
                .findAny().orElse(
                        new ZakazResponse(new Result[0],
                                new com.int20h2021.testtask.domain.json.zakaz.Filter[0],
                                0)
                );
    }

    private int getTotalCount(ZakazResponse[] zakazResponses) {
        return Arrays.stream(zakazResponses)
                .mapToInt(ZakazResponse::getCount)
                .sum();
    }

    private List<Item> subItemsList(List<Item> items, int offset, int limit) {
        int toIndex = offset + limit;
        if (toIndex > items.size()) {
            toIndex = items.size();
        }
        return items.subList(offset, toIndex);
    }

    private List<Item> getItemsList(ZakazResponse[] zakazResponses, int storeId) {
        return Arrays.stream(zakazResponses)
                .map(r -> normalizrJsonService.normalize(r, stores.getStoresMap().get(storeId)))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private void setFilters(Data data, ZakazResponse response) {
        data.setFilters(Arrays.stream(response.getFilters()).map(f -> {
            FilterOption[] filterOptions = Arrays.stream(f.getOptions())
                    .map(o -> new FilterOption(o.getId(), o.getName()))
                    .toArray(FilterOption[]::new);
            return new Filter(f.getId(), f.getName(), filterOptions);
        }).toArray(Filter[]::new));
    }

    private String getFiltersString(MultiValueMap<String, String> filters) {
        StringBuilder stringBuilder = new StringBuilder();
        filters.forEach((k, v) -> v.forEach(f -> stringBuilder.append("&")
                .append(k)
                .append("=")
                .append(f)));
        return stringBuilder.toString();
    }

    private ZakazResponse performZakazRequest(int storeId, String query, String paramsString, String pageParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptLanguage(Collections.singletonList(new Locale.LanguageRange("uk")));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<ZakazResponse> response = this.restTemplate.exchange(ZAKAZ_COMMON_REQUEST_URL + paramsString + pageParam, HttpMethod.GET, request, ZakazResponse.class, storeId, query);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    private Data toData(List<Item> items, int count) {
        ItemJson[] itemJsons = items.stream()
                .map(Item::toJson)
                .toArray(ItemJson[]::new);
        return new Data(itemJsons, count);
    }
}
