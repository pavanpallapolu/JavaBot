package com.demo.chatbot.models;

import com.demo.chatbot.enums.Pawn;
import com.demo.chatbot.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PawnPlacementRequest {
    private String gameId;
    private Integer selectedPawn;
    private List<Integer> availablePositions;
    private List<Integer> board;
    private List<Move> moves;

}
