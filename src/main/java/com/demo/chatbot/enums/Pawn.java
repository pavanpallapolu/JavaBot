package com.demo.chatbot.enums;

public enum Pawn {
    P00(0, 0, 0, 0),
    P01(0, 0, 0, 1),
    P02(0, 0, 1, 0),
    P03(0, 0, 1, 1),
    P04(0, 1, 0, 0),
    P05(0, 1, 0, 1),
    P06(0, 1, 1, 0),
    P07(0, 1, 1, 1),
    P08(1, 0, 0, 0),
    P09(1, 0, 0, 1),
    P10(1, 0, 1, 0),
    P11(1, 0, 1, 1),
    P12(1, 1, 0, 0),
    P13(1, 1, 0, 1),
    P14(1, 1, 1, 0),
    P15(1, 1, 1, 1);

    private final int bit3;
    private final int bit2;
    private final int bit1;
    private final int bit0;

    Pawn(int bit3, int bit2, int bit1, int bit0) {
        this.bit3 = bit3;
        this.bit2 = bit2;
        this.bit1 = bit1;
        this.bit0 = bit0;
    }

}
