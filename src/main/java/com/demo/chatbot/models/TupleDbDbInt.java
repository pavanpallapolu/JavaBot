package com.demo.chatbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TupleDbDbInt implements Comparable {
    private Double first;
    private Double second;
    private Integer third;

    @Override
    public int compareTo(Object o) {
        TupleDbDbInt tupleDbDbInt=(TupleDbDbInt) o;
        if(this.first>tupleDbDbInt.first) return 1;
        if(this.first<tupleDbDbInt.first) return -1;
        if(this.second>tupleDbDbInt.second) return -1;
        if(this.second<tupleDbDbInt.second) return 1;
        if(this.third>tupleDbDbInt.third) return 1;
        if(this.third<tupleDbDbInt.third) return -1;
        return 0;
    }
}
