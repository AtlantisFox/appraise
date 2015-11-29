package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "plan_index", schema = "appraise", catalog = "")
@IdClass(ArPlanIndexPK.class)
public class ArPlanIndex {
    private int planId;
    private int indexId;
    private double weight;

    @Id
    @Column(name = "plan_id")
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @Id
    @Column(name = "index_id")
    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    @Basic
    @Column(name = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArPlanIndex that = (ArPlanIndex) o;

        if (planId != that.planId) return false;
        if (indexId != that.indexId) return false;
        if (Double.compare(that.weight, weight) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = planId;
        result = 31 * result + indexId;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
