package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberVO implements Serializable {

    private String memberward, membername, memberbirthdate, membereducation, memberoccupation, membertemaddress, memberpermanentaddress,
            membercurrentaddress, membercontact, membercast, membergender, membervoteridnumber, memberrelation, memberid, leedid,
            memberimage,memberage,memberzone;


    public Long createdDateTime;

    public MemberVO() {

    }

    public MemberVO(int id) {
    }

    public MemberVO(String memberward, String membername, String memberbirthdate, String membereducation, String memberoccupation, String membertemaddress,
                    String memberpermanentaddress, String membercurrentaddress, String membercontact, String membercast, String membergender,
                    String membervoteridnumber, String memberrelation, String memberid, String leedid, String memberimage,
                    String memberage, String memberzone) {

        this.createdDateTime = createdDateTime;
        this.memberward = memberward;
        this.membername = membername;
        this.memberbirthdate = memberbirthdate;
        this.membereducation = membereducation;
        this.memberoccupation = memberoccupation;
        this.membertemaddress = membertemaddress;
        this.memberpermanentaddress = memberpermanentaddress;
        this.membercurrentaddress = membercurrentaddress;
        this.membercontact = membercontact;
        this.membercast = membercast;
        this.membergender = membergender;
        this.membervoteridnumber = membervoteridnumber;
        this.memberrelation = memberrelation;
        this.memberid = memberid;
        this.leedid = leedid;
        this.memberimage = memberimage;
        this.memberage = memberage;
        this.memberzone = memberzone;
    }


    public String getMemberward() {
        return memberward;
    }

    public void setMemberward(String memberward) {
        this.memberward = memberward;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMemberbirthdate() {
        return memberbirthdate;
    }

    public void setMemberbirthdate(String memberbirthdate) {
        this.memberbirthdate = memberbirthdate;
    }

    public String getMembereducation() {
        return membereducation;
    }

    public void setMembereducation(String membereducation) {
        this.membereducation = membereducation;
    }

    public String getMemberoccupation() {
        return memberoccupation;
    }

    public void setMemberoccupation(String memberoccupation) {
        this.memberoccupation = memberoccupation;
    }

    public String getMembertemaddress() {
        return membertemaddress;
    }

    public void setMembertemaddress(String membertemaddress) {
        this.membertemaddress = membertemaddress;
    }

    public String getMemberpermanentaddress() {
        return memberpermanentaddress;
    }

    public void setMemberpermanentaddress(String memberpermanentaddress) {
        this.memberpermanentaddress = memberpermanentaddress;
    }

    public String getMembercurrentaddress() {
        return membercurrentaddress;
    }

    public void setMembercurrentaddress(String membercurrentaddress) {
        this.membercurrentaddress = membercurrentaddress;
    }

    public String getMembercontact() {
        return membercontact;
    }

    public void setMembercontact(String membercontact) {
        this.membercontact = membercontact;
    }

    public String getMembercast() {
        return membercast;
    }

    public void setMembercast(String membercast) {
        this.membercast = membercast;
    }

    public String getMembergender() {
        return membergender;
    }

    public void setMembergender(String membergender) {
        this.membergender = membergender;
    }

    public String getMembervoteridnumber() {
        return membervoteridnumber;
    }

    public void setMembervoteridnumber(String membervoteridnumber) {
        this.membervoteridnumber = membervoteridnumber;
    }

    public String getMemberrelation() {
        return memberrelation;
    }

    public void setMemberrelation(String memberrelation) {
        this.memberrelation = memberrelation;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getLeedid() {
        return leedid;
    }

    public void setLeedid(String leedid) {
        this.leedid = leedid;
    }

    public String getMemberimage() {
        return memberimage;
    }

    public void setMemberimage(String memberimage) {
        this.memberimage = memberimage;
    }

    public String getMemberage() {
        return memberage;
    }

    public void setMemberage(String memberage) {
        this.memberage = memberage;
    }

    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getMemberzone() {
        return memberzone;
    }

    public void setMemberzone(String memberzone) {
        this.memberzone = memberzone;
    }

    public static ArrayList<RelationVO> getLeeds() {
        ArrayList<RelationVO> leedsModelArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RelationVO reportmodel = new RelationVO(i);
            leedsModelArrayList.add(reportmodel);
        }
        return leedsModelArrayList;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("createdDateTime", getCreatedDateTime());
        leedMap.put("memberward", getMemberward());
        leedMap.put("membername", getMembername());
        leedMap.put("memberbirthdate", getMemberbirthdate());
        leedMap.put("membereducation", getMembereducation());
        leedMap.put("memberoccupation", getMemberoccupation());
        leedMap.put("membertemaddress", getMembertemaddress());
        leedMap.put("memberpermanentaddress", getMemberpermanentaddress());
        leedMap.put("membercurrentaddress", getMembercurrentaddress());
        leedMap.put("membercontact", getMembercontact());
        leedMap.put("membercast", getMembercast());
        leedMap.put("membergender", getMembergender());
        leedMap.put("membervoteridnumber", getMembervoteridnumber());
        leedMap.put("memberrelation", getMemberrelation());
        leedMap.put("memberid", getMemberid());
        leedMap.put("leedid", getLeedid());
        leedMap.put("memberimage",getMemberimage());
        leedMap.put("memberage",getMemberage());
        leedMap.put("memberzone",getMemberzone());

        return leedMap;

    }

}


