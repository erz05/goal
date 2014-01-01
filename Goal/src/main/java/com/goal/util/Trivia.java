package com.goal.util;

import java.util.Random;

/**
 * Created by erz on 12/14/13.
 */
public class Trivia {
    public int level;

    public Trivia(){

    }

    public static MathProblem genMathProb(int level){
        Random random = new Random();
        int answer =0;
        String problem ="";

        return new MathProblem(answer, problem);
    }
}
