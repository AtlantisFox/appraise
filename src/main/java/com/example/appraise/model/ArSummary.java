package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "summary", schema = "appraise", catalog = "")
@IdClass(ArSummaryPK.class)
public class ArSummary {
    private int plan;
    private String user;
    private int finished;
    private double pointCommon;
    private double pointUnique;

    @Id
    @Column(name = "plan")
    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    @Id
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "finished")
    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "point_common")
    public double getPointCommon() {
        return pointCommon;
    }

    public void setPointCommon(double pointCommon) {
        this.pointCommon = pointCommon;
    }

    @Basic
    @Column(name = "point_unique")
    public double getPointUnique() {
        return pointUnique;
    }

    public void setPointUnique(double pointUnique) {
        this.pointUnique = pointUnique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArSummary arSummary = (ArSummary) o;

        if (plan != arSummary.plan) return false;
        if (finished != arSummary.finished) return false;
        if (Double.compare(arSummary.pointCommon, pointCommon) != 0)
            return false;
        if (Double.compare(arSummary.pointUnique, pointUnique) != 0)
            return false;
        if (user != null ? !user.equals(arSummary.user) : arSummary.user != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = plan;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + finished;
        temp = Double.doubleToLongBits(pointCommon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pointUnique);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
