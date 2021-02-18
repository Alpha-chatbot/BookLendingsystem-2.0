package com.wang.book.dto;

import com.wang.book.enums.LendingStateEnum;
import com.wang.book.enums.ReturningStateEnum;

public class ReturningState {


        // 图书ID
        private int bookId;

        // 预约结果状态
        private int state;

        // 状态标识
        private String stateInfo;

        public ReturningState() {
        }

        // 预约失败的构造器
        public ReturningState(int bookId, ReturningStateEnum stateEnum) {
            this.bookId = bookId;
            this.state = stateEnum.getState();
            this.stateInfo = stateEnum.getStateInfo();
        }

        //set get 方法！
        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }


    }

