package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "plan", schema = "appraise", catalog = "")
public class ArPlan {
    private int id;
    private String name;
    private String remark;
    private int deadline;
    private int status;

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
    @Column(name = "deadline")
    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArPlan arPlan = (ArPlan) o;

        if (id != arPlan.id) return false;
        if (deadline != arPlan.deadline) return false;
        if (status != arPlan.status) return false;
        if (name != null ? !name.equals(arPlan.name) : arPlan.name != null)
            return false;
        if (remark != null ? !remark.equals(arPlan.remark) : arPlan.remark != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + deadline;
        result = 31 * result + status;
        return result;
    }
}
