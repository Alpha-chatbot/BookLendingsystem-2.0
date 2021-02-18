package com.wang.book.enums;

public enum ReturningStateEnum {
    //构造枚举值
    SUCCESS(1, "还书成功"),  REPEAT_LENDING(-1, "无效的重复操作"), INNER_ERROR(-2, "系统异常");

    private int state;

    private String stateInfo;

    private ReturningStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ReturningStateEnum stateOf(int index) {
        for (ReturningStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
