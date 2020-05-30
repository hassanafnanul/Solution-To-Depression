package com.afnanulcoder.aspirant;

public class PeachRoomInfo {

    public String prName, prCategory, prSDescription, prAdmin;
    public int prMember, prLevel;


    public PeachRoomInfo() {
    }

    public PeachRoomInfo(String prName, String prCategory, String prSDescription, int prMember, String prAdmin, int prLevel) {
        this.prName = prName;
        this.prCategory = prCategory;
        this.prSDescription = prSDescription;
        this.prMember = prMember;
        this.prAdmin = prAdmin;
        this.prLevel = prLevel;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrCategory() {
        return prCategory;
    }

    public void setPrCategory(String prCategory) {
        this.prCategory = prCategory;
    }

    public String getPrSDescription() {
        return prSDescription;
    }

    public void setPrSDescription(String prSDescription) {
        this.prSDescription = prSDescription;
    }

    public String getPrAdmin() {
        return prAdmin;
    }

    public void setPrAdmin(String prAdmin) {
        this.prAdmin = prAdmin;
    }

    public int getPrMember() {
        return prMember;
    }

    public void setPrMember(int prMember) {
        this.prMember = prMember;
    }

    public int getPrLevel() {
        return prLevel;
    }

    public void setPrLevel(int prLevel) {
        this.prLevel = prLevel;
    }
}
