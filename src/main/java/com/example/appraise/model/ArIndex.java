package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "index", schema = "appraise", catalog = "")
public class ArIndex {
    private int id;
    private String name;
    private String remark;
    private int point;
    private String appraiser;
    private String appraisee;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Basic
    @Column(name = "appraiser")
    public String getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser;
    }

    @Basic
    @Column(name = "appraisee")
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

        ArIndex arIndex = (ArIndex) o;

        if (id != arIndex.id) return false;
        if (point != arIndex.point) return false;
        if (name != null ? !name.equals(arIndex.name) : arIndex.name != null)
            return false;
        if (remark != null ? !remark.equals(arIndex.remark) : arIndex.remark != null)
            return false;
        if (appraiser != null ? !appraiser.equals(arIndex.appraiser) : arIndex.appraiser != null)
            return false;
        if (appraisee != null ? !appraisee.equals(arIndex.appraisee) : arIndex.appraisee != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + point;
        result = 31 * result + (appraiser != null ? appraiser.hashCode() : 0);
        result = 31 * result + (appraisee != null ? appraisee.hashCode() : 0);
        return result;
    }
}
