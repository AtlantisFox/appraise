package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "appraise", catalog = "")
public class ArUser {
    private String username;
    private String remark;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArUser arUser = (ArUser) o;

        if (username != null ? !username.equals(arUser.username) : arUser.username != null)
            return false;
        if (remark != null ? !remark.equals(arUser.remark) : arUser.remark != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
