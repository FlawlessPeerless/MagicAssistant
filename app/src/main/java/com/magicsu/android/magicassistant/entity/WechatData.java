package com.magicsu.android.magicassistant.entity;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.entity
 * file: WechatData
 * author: admin
 * date: 2018/2/5
 * description: 微信精选数据实体
 */

public class WechatData {
    private String title;
    private String source;
    private String firstImg;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WechatData{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
