package com.example.appraise.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ArPlanIndexPK implements Serializable {
    private int planId;
    private int indexId;

    @Column(name = "plan_id")
    @Id
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @Column(name = "index_id")
    @Id
    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArPlanIndexPK that = (ArPlanIndexPK) o;

        if (planId != that.planId) return false;
        if (indexId != that.indexId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planId;
        result = 31 * result + indexId;
        return result;
    }
}
