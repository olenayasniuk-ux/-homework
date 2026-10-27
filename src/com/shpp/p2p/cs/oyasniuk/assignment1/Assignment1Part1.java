package com.shpp.p2p.cs.oyasniuk.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part1 extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        // Karel prepares to make column
        turnLeft();

        while (frontIsClear()) {
            // move up and put beepers if necessary
            moveUpBeepers();

            // move down
            moveDown();

            // move to the other column
            moveRight();

            //prepare to move up
            turnLeft();
        }
    }

    // build column begin in the cell where Karel stands
    private void moveUpBeepers() throws Exception {
        if (noBeepersPresent()) {
            putBeeper();
        }

        while (frontIsClear()) {
            move();
            if (noBeepersPresent()) {
                putBeeper();
            }
        }
    }

    // Karel turns around and
    // goes back to the beginning of the column
    // and turn to the left
    private void moveDown() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
        turnLeft();
    }

    // Karel do 4 steps to the beginning of next column
    // if there is the last column Karel turns to the right
    private void moveRight() throws Exception {
        if (frontIsClear()) {
            move();
            move();
            move();
            move();
        }else {
            turnRight();
        }
    }


    // Turn Karel`s face to the right
    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    // Turn Karel`s face to the opposite side
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
}


