package com.example.appraise.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ArSummaryPK implements Serializable {
    private int plan;
    private String user;

    @Column(name = "plan")
    @Id
    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    @Column(name = "user")
    @Id
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArSummaryPK that = (ArSummaryPK) o;

        if (plan != that.plan) return false;
        if (user != null ? !user.equals(that.user) : that.user != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plan;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
