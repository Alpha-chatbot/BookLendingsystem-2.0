package com.wang.book.dto;


import com.wang.book.enums.LendingStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LendingState {


        // 图书ID
        private int bookId;

        // 预约结果状态
        private int state;

        // 状态标识
        private String stateInfo;

        public LendingState() {
        }

        // 预约失败的构造器
        public LendingState(int bookId, LendingStateEnum stateEnum) {
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

        @Override
        public String toString() {
            return "LendingExecution [bookId=" + bookId + ", state=" + state + ", stateInfo=" + stateInfo+"]";
        }
    }