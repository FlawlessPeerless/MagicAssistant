package com.magicsu.android.magicassistant.entity;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.entity
 * file: ChatListData
 * author: admin
 * date: 2018/2/5
 * description: 对话数据实体
 */

public class ChatListData {
    private String text;
    private int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
