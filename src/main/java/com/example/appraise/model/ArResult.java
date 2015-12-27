package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "result", schema = "appraise", catalog = "")
@IdClass(ArResultPK.class)
public class ArResult {
    private int planId;
    private int indexId;
    private String appraisee;
    private int point;

    @Id
    @Column(name = "plan_id")
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int plan) {
        this.planId = plan;
    }

    @Id
    @Column(name = "index_id")
    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int index) {
        this.indexId = index;
    }

    @Id
    @Column(name = "appraisee")
    public String getAppraisee() {
        return appraisee;
    }

    public void setAppraisee(String appraisee) {
        this.appraisee = appraisee;
    }

    @Basic
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArResult arResult = (ArResult) o;

        if (planId != arResult.planId) return false;
        if (indexId != arResult.indexId) return false;
        if (point != arResult.point) return false;
        if (appraisee != null ? !appraisee.equals(arResult.appraisee) : arResult.appraisee != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planId;
        result = 31 * result + indexId;
        result = 31 * result + (appraisee != null ? appraisee.hashCode() : 0);
        result = 31 * result + point;
        return result;
    }
}
