package com.demo.chatbot.models;

import com.demo.chatbot.enums.Pawn;
import com.demo.chatbot.enums.Position;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Board {
    @Builder.Default
    private List<Pair> pairs = List.of(
            Pair.builder().position(Position.A1).build(),
            Pair.builder().position(Position.A2).build(),
            Pair.builder().position(Position.A3).build(),
            Pair.builder().position(Position.A4).build(),
            Pair.builder().position(Position.B1).build(),
            Pair.builder().position(Position.B2).build(),
            Pair.builder().position(Position.B3).build(),
            Pair.builder().position(Position.B4).build(),
            Pair.builder().position(Position.C1).build(),
            Pair.builder().position(Position.C2).build(),
            Pair.builder().position(Position.C3).build(),
            Pair.builder().position(Position.C4).build(),
            Pair.builder().position(Position.D1).build(),
            Pair.builder().position(Position.D2).build(),
            Pair.builder().position(Position.D3).build(),
            Pair.builder().position(Position.D4).build());

    public static void placePawnAtPosition(Board board, Pawn pawn, Position position) {
        board.getPairs().get(position.ordinal()).setPawn(pawn);
    }

    public static Pawn lookupPawnAtPosition(Board board, Position position) {
        return board.getPairs().get(position.ordinal()).getPawn();
    }

}
