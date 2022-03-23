package com.runload.pojo;

public class FilePojo {
    private String newtime;
    private String name;
    private String serious;
    private String priority;
    private String state;
    private String title;
    private String iteration;
    private String platform;
    private String solvetime;
    private String design;

    public String getNewtime() {
        return newtime;
    }

    public void setNewtime(String newtime) {
        this.newtime = newtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerious() {
        return serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSolvetime() {
        return solvetime;
    }

    public void setSolvetime(String solvetime) {
        this.solvetime = solvetime;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    @Override
    public String toString() {
        return "FilePojo{" +
                "newtime='" + newtime + '\'' +
                ", name='" + name + '\'' +
                ", serious='" + serious + '\'' +
                ", priority='" + priority + '\'' +
                ", state='" + state + '\'' +
                ", title='" + title + '\'' +
                ", iteration='" + iteration + '\'' +
                ", platform='" + platform + '\'' +
                ", solvetime='" + solvetime + '\'' +
                ", design='" + design + '\'' +
                '}';
    }
}
