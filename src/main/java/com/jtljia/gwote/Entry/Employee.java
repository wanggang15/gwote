package com.jtljia.gwote.Entry;

import org.apache.poi.hpsf.Decimal;

import java.util.Arrays;
import java.util.Date;

/**
 * 员工
 */
public class Employee {
    /** id */
    private String id;

    /** 姓名 */
    private String name;

    /** 性别 */
    private Sex sex;

    /** 出生年月 */
    private Date brithday;

    /** 年龄 */
    private int age;

    /** 工号 */
    private String jobNumber;

    /** 电话 */
    private String phone;

    /** 身份证号 */
    private String idCardNo;

    /** 职务 */
    private Position position;

    /** 入职日期 */
    private Date entryDate;

    /** 薪资 */
    private Decimal salary;

    /** 身高 */
    private double height;

    /** 学历 */
    private String edu;

    /** 毕业学校 */
    private String graduateSchool;

    /** 专业 */
    private String major;

    /** 籍贯 */
    private String birthPlace;

    /** 政治面貌 */
    private String polity;

    /** 婚姻状态 */
    private String married;

    /** 下级人员 */
    private Employee[] subordinate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee[] getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Employee[] subordinate) {
        this.subordinate = subordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Decimal getSalary() {
        return salary;
    }

    public void setSalary(Decimal salary) {
        this.salary = salary;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getPolity() {
        return polity;
    }

    public void setPolity(String polity) {
        this.polity = polity;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", brithday=" + brithday +
                ", age=" + age +
                ", jobNumber='" + jobNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", position=" + position +
                ", entryDate=" + entryDate +
                ", salary=" + salary +
                ", height=" + height +
                ", edu='" + edu + '\'' +
                ", graduateSchool='" + graduateSchool + '\'' +
                ", major='" + major + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", polity='" + polity + '\'' +
                ", married='" + married + '\'' +
                ", subordinate=" + Arrays.toString(subordinate) +
                '}';
    }
}
