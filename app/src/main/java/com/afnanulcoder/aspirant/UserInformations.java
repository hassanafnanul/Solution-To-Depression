package com.afnanulcoder.aspirant;

public class UserInformations
{
    public String type;

    public String wName, wAge, wEmail, wGender;

    public String nName, nAge, nEmail, nSomething, nGender, nPeachRoom;
    public int nLevel;

    public String memberName, memberKey;



    public UserInformations() {
    }

    public UserInformations(String type, String wName, String wAge, String wEmail, String wGender) {
        this.type = type;
        this.wName = wName;
        this.wAge = wAge;
        this.wEmail = wEmail;
        this.wGender = wGender;
    }

    public UserInformations(String type, String nName, String nAge, String nEmail, String nSomething, String nGender, String nPeachRoom, int nLevel) {
        this.type = type;
        this.nName = nName;
        this.nAge = nAge;
        this.nEmail = nEmail;
        this.nSomething = nSomething;
        this.nGender = nGender;
        this.nPeachRoom = nPeachRoom;
        this.nLevel = nLevel;
    }


    public UserInformations(String memberName, String memberKey) {
        this.memberName = memberName;
        this.memberKey = memberKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public String getwAge() {
        return wAge;
    }

    public void setwAge(String wAge) {
        this.wAge = wAge;
    }

    public String getwEmail() {
        return wEmail;
    }

    public void setwEmail(String wEmail) {
        this.wEmail = wEmail;
    }

    public String getwGender() {
        return wGender;
    }

    public void setwGender(String wGender) {
        this.wGender = wGender;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getnAge() {
        return nAge;
    }

    public void setnAge(String nAge) {
        this.nAge = nAge;
    }

    public String getnEmail() {
        return nEmail;
    }

    public void setnEmail(String nEmail) {
        this.nEmail = nEmail;
    }

    public String getnSomething() {
        return nSomething;
    }

    public void setnSomething(String nSomething) {
        this.nSomething = nSomething;
    }

    public String getnGender() {
        return nGender;
    }

    public void setnGender(String nGender) {
        this.nGender = nGender;
    }

    public String getnPeachRoom() {
        return nPeachRoom;
    }

    public void setnPeachRoom(String nPeachRoom) {
        this.nPeachRoom = nPeachRoom;
    }

    public int getnLevel() {
        return nLevel;
    }

    public void setnLevel(int nLevel) {
        this.nLevel = nLevel;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }
}
