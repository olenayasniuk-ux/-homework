package com.shpp.p2p.cs.oyasniuk.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot {
    @Override
    public void run() throws Exception {

        //put Beeper on the cell
        putBeeper();

        // check the last cell on the field
        if (!((frontIsBlocked() && facingWest() && rightIsBlocked()) || (frontIsBlocked() && facingEast() && leftIsBlocked()))) {

            // one step along the "snake"
            moveBySnake();
        }

        // check the last cell on the field
        if (!((frontIsBlocked() && facingWest() && rightIsBlocked()) || (frontIsBlocked() && facingEast() && leftIsBlocked()))) {

            // one step along the "snake"
            moveBySnake();

            //run the current function again
            run();
        }

    }

    // move by "snake"
    // move to the end of row
    // then move to the next row and repeat
    private void moveBySnake() throws Exception {
        // move if clear
        if (frontIsClear()) {
            move();
        } else {    // move on the next row if not

            if (facingEast()) {   //east side
                if (!leftIsBlocked()) {
                    turnLeft();
                    move();
                    turnLeft();
                               }

            } else if (facingWest()) { //west side
                if (!rightIsBlocked()) {
                    turnRight();
                    move();
                    turnRight();
                }

            }
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


