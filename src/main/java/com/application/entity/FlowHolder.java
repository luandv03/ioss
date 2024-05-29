package com.application.entity;


import java.util.ArrayList;

//Hàm nắm giữ luồng hoạt động của Bách
public class FlowHolder {
    public static FlowHolder flowHolder = new FlowHolder();

    ///level 0: danh sách mặt hàng cần đặt
    ///level 1: mặt hàng cần đặt
    private String[] id_level;
    private String[] status_level;
    public FlowHolder() {
        id_level = new String[3];
        status_level = new String[3];
    }

    public void UpdateId(String ID, String status, int level)
    {
        System.out.println("Cập nhật level " + level + " có mã " + ID);
        this.id_level[level] = ID;
        this.status_level[level] = status;
        for (int i = level + 1; i<3; i++)
        {
            this.id_level[i] = "";
            this.status_level[i] = "";
        }
    }

    public String getOrderListItemId(){return id_level[0];}
    public String getItemId(){return id_level[1];}
    public String getStatus(){ return status_level[0];};
}
