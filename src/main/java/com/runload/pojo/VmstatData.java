package com.runload.pojo;

public class VmstatData {
    public String r = "运行队列";
    public String b = "阻塞的进程";
    public String swpd = "虚拟内存已使用的大小";
    public String free = "空闲的物理内存的大小";
    public String buff = "Linux/Unix系统是用来存储，目录里面有什么内容，权限等的缓存";
    public String cache = "直接用来记忆我们打开的文件,给文件做缓冲";
    public String si = "每秒从磁盘读入虚拟内存的大小，如果这个值大于0，表示物理内存不够用或者内存泄露了";
    public String so = "每秒虚拟内存写入磁盘的大小，如果这个值大于0，同上";
    public String bi = "IO块设备每秒接收的块数量";
    public String bo = "块设备每秒发送的块数量";
    public String ins = "每秒CPU的中断次数，包括时间中断";
    public String cs = "每秒上下文切换次数";
    public String us = "用户CPU时间";
    public String sy = "系统CPU时间";
    public String id = "空闲 CPU时间";
    public String wa = "等待IO CPU时间";
    public String st = "等待IO CPU时间";

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getSwpd() {
        return swpd;
    }

    public void setSwpd(String swpd) {
        this.swpd = swpd;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getBuff() {
        return buff;
    }

    public void setBuff(String buff) {
        this.buff = buff;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getBo() {
        return bo;
    }

    public void setBo(String bo) {
        this.bo = bo;
    }

    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public String getSy() {
        return sy;
    }

    public void setSy(String sy) {
        this.sy = sy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    @Override
    public String toString() {
        return "CmdVmstatPojo{" +
                "r='" + r + '\'' +
                ", b='" + b + '\'' +
                ", swpd='" + swpd + '\'' +
                ", free='" + free + '\'' +
                ", buff='" + buff + '\'' +
                ", cache='" + cache + '\'' +
                ", si='" + si + '\'' +
                ", so='" + so + '\'' +
                ", bi='" + bi + '\'' +
                ", bo='" + bo + '\'' +
                ", ins='" + ins + '\'' +
                ", cs='" + cs + '\'' +
                ", us='" + us + '\'' +
                ", sy='" + sy + '\'' +
                ", id='" + id + '\'' +
                ", wa='" + wa + '\'' +
                ", st='" + st + '\'' +
                '}';
    }
}
