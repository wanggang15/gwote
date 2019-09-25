package com.jtljia.gwote.Entry;

/**
 * 职位
 */
public class Position {

    /** 职务名称 */
    private String name;

    /** 职责 */
    private String duty;

    /** 职级 */
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Position(String name, String duty) {
        this.name = name;
        this.duty = duty;
    }

    public Position() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Override
    public String toString() {
        return "Position{" +
                "duty='" + duty + '\'' +
                '}';
    }
}
