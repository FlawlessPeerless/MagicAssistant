package com.magicsu.android.magicassistant.entity;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.entity
 * file: ExpressData
 * author: admin
 * date: 2018/1/31
 * description: 物流数据实体类
 */

public class ExpressData {
    private String datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "ExpressData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
