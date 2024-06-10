package com.demo.chatbot.models;

import com.demo.chatbot.enums.Pawn;
import lombok.Data;

import java.util.List;

@Data
public class PawnSelectionRequest {
    private String gameId;
    private List<Pawn> availablePawns;
    private List<Integer> board;
    private List<Move> moves;

}
