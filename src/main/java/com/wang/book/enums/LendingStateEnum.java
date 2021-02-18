package com.wang.book.enums;

import com.wang.book.pojo.Lending;

//使用枚举表述常量数据字典,我们先定义几个预约图书操作返回码的数据字典，也就是我们要返回给客户端的信息。
//和实体类差不多，枚举类也有成员变量，构造方法，get，set方法，不过构造器只能是私有化，这样可以保证外部代码无法新构造枚举类的实例。
public enum LendingStateEnum {
    //构造枚举值
    SUCCESS(1, "预约成功"), NO_NUMBER(0, "库存不足"), REPEAT_LENDING(-1, "重复预约"), INNER_ERROR(-2, "系统异常");

    private int state;

    private String stateInfo;

    private LendingStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static LendingStateEnum stateOf(int index) {
        for (LendingStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
