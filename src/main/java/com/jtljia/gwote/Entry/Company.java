package com.jtljia.gwote.Entry;

import java.util.Arrays;
import java.util.Date;

/**
 * 子公司
 */
public class Company {
    /** 公司名称 */
    private String name;

    /** 部门 */
    private Department[] departments;

    /** 成立时间 */
    private Date createDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department[] getDepartments() {
        return departments;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", departments=" + Arrays.toString(departments) +
                ", createDate=" + createDate +
                '}';
    }
}
