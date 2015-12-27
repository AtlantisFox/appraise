package com.example.appraise.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ArResultPK implements Serializable {
    private int planId;
    private int indexId;
    private String appraisee;

    @Column(name = "plan_id")
    @Id
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int plan) {
        this.planId = plan;
    }

    @Column(name = "index_id")
    @Id
    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int index) {
        this.indexId = index;
    }

    @Column(name = "appraisee")
    @Id
    public String getAppraisee() {
        return appraisee;
    }

    public void setAppraisee(String appraisee) {
        this.appraisee = appraisee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArResultPK that = (ArResultPK) o;

        if (planId != that.planId) return false;
        if (indexId != that.indexId) return false;
        if (appraisee != null ? !appraisee.equals(that.appraisee) : that.appraisee != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planId;
        result = 31 * result + indexId;
        result = 31 * result + (appraisee != null ? appraisee.hashCode() : 0);
        return result;
    }
}
