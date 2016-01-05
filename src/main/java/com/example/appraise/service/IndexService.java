package com.example.appraise.service;

import com.example.appraise.model.ArIndex;
import com.example.appraise.model.RestApiException;

import java.util.List;

public interface IndexService {
    ArIndex save(ArIndex entity) throws RestApiException;

    ArIndex update(ArIndex entity) throws RestApiException;

    ArIndex delete(int id) throws RestApiException;

    List<ArIndex> findAll();

    List<Integer> findUsedIndexes();
}
