package com.example.appraise.model;

import java.util.List;

public class ArPlanPackage {
    private ArPlan meta;
    private List<ArPlanIndex> indexes;

    public ArPlan getMeta() {
        return meta;
    }

    public void setMeta(ArPlan meta) {
        this.meta = meta;
    }

    public List<ArPlanIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<ArPlanIndex> indexes) {
        this.indexes = indexes;
    }
}
