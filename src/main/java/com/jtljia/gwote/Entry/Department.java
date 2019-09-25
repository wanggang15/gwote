package com.jtljia.gwote.Entry;

import java.util.Arrays;

/**
 * 部门
 */
public class Department {
    /** id */
    private String id;

    /** 部门名称 */
    private String name;

    /** 下级部门 */
    private Department[] subDepartment;

    /** 职能描述 */
    private String description;

    /** 人员 */
    private Employee[] employees;

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department[] getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(Department[] subDepartment) {
        this.subDepartment = subDepartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subDepartment=" + Arrays.toString(subDepartment) +
                ", description='" + description + '\'' +
                ", employees=" + Arrays.toString(employees) +
                '}';
    }
}
