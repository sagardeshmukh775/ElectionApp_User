package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelationVO implements Serializable {

    public String relationname, relationcontact, relationaddress, relationid, leedid;

    public Long createdDateTime;

    public RelationVO() {

    }

    public RelationVO(int id) {
    }

    public RelationVO(String relationname, String relationcontact, String relationaddress,
                      String relationid, String leedid) {

        this.createdDateTime = createdDateTime;
        this.relationname = relationname;
        this.relationcontact = relationcontact;
        this.relationaddress = relationaddress;
        this.relationid = relationid;
        this.leedid = leedid;

    }

    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = (Long) createdDateTime;
    }

    public String getRelationname() {
        return relationname;
    }

    public void setRelationname(String relationname) {
        this.relationname = relationname;
    }

    public String getRelationcontact() {
        return relationcontact;
    }

    public void setRelationcontact(String relationcontact) {
        this.relationcontact = relationcontact;
    }

    public String getRelationaddress() {
        return relationaddress;
    }

    public void setRelationaddress(String relationaddress) {
        this.relationaddress = relationaddress;
    }

    public String getRelationid() {
        return relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid;
    }

    public String getLeedid() {
        return leedid;
    }

    public void setLeedid(String leedid) {
        this.leedid = leedid;
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
        leedMap.put("relationname", getRelationname());
        leedMap.put("relationcontact", getRelationcontact());
        leedMap.put("relationaddress", getRelationaddress());
        leedMap.put("relationid", getRelationid());
        leedMap.put("leedid", getLeedid());

        return leedMap;

    }
}



