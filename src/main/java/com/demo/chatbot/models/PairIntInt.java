package com.demo.chatbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PairIntInt implements Comparable {
    private Integer first;
    private Integer second;

    @Override
    public int compareTo(Object o) {
        PairIntInt pairIntInt=(PairIntInt) o;
        if(this.first>pairIntInt.first) return 1;
        if(this.first.equals(pairIntInt.first) &&this.second>pairIntInt.second) return 1;
        if(this.first.equals(pairIntInt.first) && this.second.equals(pairIntInt.second)) return 0;
        return -1;
    }
}
