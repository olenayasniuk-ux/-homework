package com.shpp.p2p.cs.oyasniuk.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part2 extends KarelTheRobot {
    @Override
    public void run() throws Exception {

        //check if your world consists one cell
        turnLeft();
        if (leftIsBlocked() && rightIsBlocked()) {
            putBeeper();
        }else{
            turnRight();

            //build a wall of beepers around the central cell
            goToWall();
            goToWall();
            goToBeeper();

            // put beeper in the central cell
            putBeeper();

            // go to the wall
            while (frontIsClear()){
                move();
            }

            //go to another wall and pick up useless beepers
            turnAround();
            pickBeeper();
            while (frontIsClear()){
                move();
                pickBeeper();
            }
        }
        }

    // go to a wall, put a beeper, then turn around and take one step
    private void goToWall() throws Exception {
        while(frontIsClear()){
            move();
        }
        putBeeper();
        turnAround();
        move();
    }

    // go to a beeper, take one step back and put a beeper
    // until a wall is built around the central cell
    private void goToBeeper() throws Exception {
        if (noBeepersPresent()){
            move();
            goToBeeper();
        } else {
            turnAround();
            move();
            if (noBeepersPresent()) {
                putBeeper();
                move();
                goToBeeper();
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








