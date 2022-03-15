package com.homerents.home_rent_billing;

public class Database {

    String userid;
    String username;
    String usermobno;
    String userage;
    String userpin;

    String cb;
    String unrec;
    String totbill;
    String eleunit;
    String eleamt;

    String grandtotal;
    String newreading;
    String oldreading;
    String paid1;
    String paid2;
    String rate;
    String rent;
    String unpaid;
    String diff;
    String clintrate;
    String clintrent;
    String clintno;
    String date;
    String values;


    String name;

    public String getUnrec() {
        return unrec;
    }

    public void setUnrec(String unrec) {
        this.unrec = unrec;
    }

    public String getTotbill() {
        return totbill;
    }

    public void setTotbill(String totbill) {
        this.totbill = totbill;
    }

    public String getEleunit() {
        return eleunit;
    }

    public void setEleunit(String eleunit) {
        this.eleunit = eleunit;
    }

    public String getEleamt() {
        return eleamt;
    }

    public void setEleamt(String eleamt) {
        this.eleamt = eleamt;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobno() {
        return usermobno;
    }

    public void setUsermobno(String usermobno) {
        this.usermobno = usermobno;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getUserpin() {
        return userpin;
    }

    public void setUserpin(String userpin) {
        this.userpin = userpin;
    }

    public Database(String userid, String username, String usermobno, String userage, String userpin) {
        this.userid = userid;
        this.username = username;
        this.usermobno = usermobno;
        this.userage = userage;
        this.userpin = userpin;
    }

    public Database(String grandtotal, String newreading, String oldreading, String paid1, String paid2, String rate, String rent, String unpaid, String diff, String date) {
        this.grandtotal = grandtotal;
        this.newreading = newreading;
        this.oldreading = oldreading;
        this.paid1 = paid1;
        this.paid2 = paid2;
        this.rate = rate;
        this.rent = rent;
        this.unpaid = unpaid;
        this.diff = diff;
        this.date = date;
    }

    public Database(String clintno, String clintrate, String clintrent, String name) {
        this.clintno = clintno;
        this.clintrate = clintrate;
        this.clintrent = clintrent;
        this.name = name;
    }

    public Database(){

    }

    public String getClintno() {
        return clintno;
    }

    public void setClintno(String clintno) {
        this.clintno = clintno;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getClintrate() {
        return clintrate;
    }

    public void setClintrate(String clintrate) {
        this.clintrate = clintrate;
    }

    public String getClintrent() {
        return clintrent;
    }

    public void setClintrent(String clintrent) {
        this.clintrent = clintrent;
    }

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getNewreading() {
        return newreading;
    }

    public void setNewreading(String newreading) {
        this.newreading = newreading;
    }

    public String getOldreading() {
        return oldreading;
    }

    public void setOldreading(String oldreading) {
        this.oldreading = oldreading;
    }

    public String getPaid1() {
        return paid1;
    }

    public void setPaid1(String paid1) {
        this.paid1 = paid1;
    }

    public String getPaid2() {
        return paid2;
    }

    public void setPaid2(String paid2) {
        this.paid2 = paid2;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(String unpaid) {
        this.unpaid = unpaid;
    }
}
