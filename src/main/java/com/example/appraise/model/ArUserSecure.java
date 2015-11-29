package com.example.appraise.model;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "appraise", catalog = "")
public class ArUserSecure {
    private String username;
    private String password;
    private String remark;
    private int isAccountAdmin;
    private int isAppraisalAdmin;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "is_account_admin")
    public int getIsAccountAdmin() {
        return isAccountAdmin;
    }

    public void setIsAccountAdmin(int isAccountAdmin) {
        this.isAccountAdmin = isAccountAdmin;
    }

    @Basic
    @Column(name = "is_appraisal_admin")
    public int getIsAppraisalAdmin() {
        return isAppraisalAdmin;
    }

    public void setIsAppraisalAdmin(int isAppraisalAdmin) {
        this.isAppraisalAdmin = isAppraisalAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArUserSecure that = (ArUserSecure) o;

        if (isAccountAdmin != that.isAccountAdmin) return false;
        if (isAppraisalAdmin != that.isAppraisalAdmin) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + isAccountAdmin;
        result = 31 * result + isAppraisalAdmin;
        return result;
    }

    public ArUser toArUser() {
        ArUser user = new ArUser();
        user.setUsername(username);
        user.setRemark(remark);
        return user;
    }
}
