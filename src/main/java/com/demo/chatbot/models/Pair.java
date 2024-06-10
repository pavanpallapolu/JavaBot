package com.demo.chatbot.models;

import com.demo.chatbot.enums.Pawn;
import com.demo.chatbot.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pair {
    private Position position;
    private Pawn pawn;

}
