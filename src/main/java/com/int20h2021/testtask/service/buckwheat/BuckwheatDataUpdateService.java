package com.int20h2021.testtask.service.buckwheat;

import com.int20h2021.testtask.domain.entity.UpdateRequest;
import com.int20h2021.testtask.repository.UpdateRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class BuckwheatDataUpdateService {
    private static final short SCAN_PERIOD_IN_HOURS = 4;
    private static final String DATA_COLLECT_ENDPOINT = "/service/collect-data";

    private final UpdateRequestRepository updateRequestRepository;
    private final RestTemplate restTemplate;

    @Value("${host}")
    private String host;

    public boolean needForUpdate() {
        List<UpdateRequest> updateRequests = updateRequestRepository.findAll();
        if (updateRequests.isEmpty()) {
            return true;
        }

        UpdateRequest updateRequest = updateRequests.get(0);
        Duration scanPeriod = Duration.ofHours(updateRequest.getScanPeriod());
        LocalDateTime recentScanDate = updateRequest.getRecentScanDate();
        return recentScanDate.plus(scanPeriod).isBefore(LocalDateTime.now());
    }

    @SneakyThrows
    public void update() {
        new Thread(() -> {
            String uri = host + DATA_COLLECT_ENDPOINT;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && requireNonNull(responseEntity.getBody()).contains(Boolean.TRUE.toString())) {
                List<UpdateRequest> updateRequests = updateRequestRepository.findAll();
                UpdateRequest updateRequest;
                if (!updateRequests.isEmpty()) {
                    updateRequest = updateRequests.get(0);
                    updateRequest.setRecentScanDate(LocalDateTime.now());
                    updateRequest.setScanPeriod(SCAN_PERIOD_IN_HOURS);
                } else {
                    updateRequest = new UpdateRequest(LocalDateTime.now(), SCAN_PERIOD_IN_HOURS);
                }
                updateRequestRepository.save(updateRequest);
            }
        }).start();
    }
}
