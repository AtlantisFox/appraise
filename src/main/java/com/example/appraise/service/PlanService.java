package com.example.appraise.service;

import com.example.appraise.model.ArPlan;
import com.example.appraise.model.ArPlanPackage;
import com.example.appraise.model.RestApiException;

import java.util.List;

public interface PlanService {
    ArPlanPackage saveOrUpdate(ArPlanPackage pack) throws RestApiException;

    void delete(int planId) throws RestApiException;

    List<ArPlan> list();

    ArPlanPackage findById(int planId);
}
