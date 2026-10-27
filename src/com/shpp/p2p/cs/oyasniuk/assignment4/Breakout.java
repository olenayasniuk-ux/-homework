package com.shpp.p2p.cs.oyasniuk.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The Breakout class implements the classic arcade game Breakout (Arkanoid).
 * The player controls a paddle using the mouse to bounce a ball and destroy a grid of bricks.
 * The game ends in a win if all bricks are cleared, or a loss if all turns (lives) are used up.
 */
public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;
    /**
     * The text displayed on the screen when the player wins.
     */
    private static final String TEXT_WIN = "YOU WIN!!!";
    /**
     * The text displayed on the screen when the player loses.
     */
    private static final String TEXT_LOST = "You lost(";
    /**
     * The paddle object controlled by the player's mouse.
     */
    GRect paddle = null;
    /**
     * The game ball object.
     */
    GOval ball;
    /**
     * The diameter of the ball calculated from its radius.
     */
    int ballDiameter = BALL_RADIUS * 2;
    /**
     * The horizontal velocity component of the ball.
     */
    double vx;
    /**
     * The vertical velocity component of the ball.
     */
    double vy = 3;
    /**
     * The remaining number of bricks on the game field.
     */
    int bricksCounter = NBRICKS_PER_ROW * NBRICK_ROWS;
    /**
     * The remaining number of turns (lives) for the player.
     */
    int lifeCounter = NTURNS;
    /**
     * The X coordinate representing th center of the game window.
     */
    double centerX;
    /**
     * The Y coordinate representing the center of the game window.
     */
    double centerY;

    /**
     * The main execution entry point for the game. Initializes game window dimensions,
     * builds the brick field, creates the paddle and ball, adds event listeners, and runs
     * the primary game loop until a game-over condition is met.
     */
    public void run() {
        centerX = getWidth() / 2.0;
        centerY = getHeight() / 2.0;
        drawBricks();
        addPaddle(centerX - PADDLE_WIDTH / 2.0, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        drawNewBall();
        addMouseListeners();
        while (!gameOver()) {
            if (ballCanMoveStraight() && collisionObject() == null) {
                drawBall();
            } else {
                ballBouncesOff();
            }
        }
    }

    /**
     * Handles ball bounces off boundaries and object collisions.
     * Reverses horizontal velocity when hitting side walls.
     * Reverses vertical velocity when hitting the ceiling or game objects.
     * Decrements the life counter if the ball falls below the bottom floor.
     * Removes a brick object from the canvas upon a valid brick collision.
     */
    private void ballBouncesOff() {
        if (ball.getX() < 0) {
            vx = -vx;
            drawBall();
        } else if (ball.getX() > getWidth() - ballDiameter) {
            vx = -vx;
            drawBall();
        } else if (ball.getY() < 0) {
            vy = -vy;
            drawBall();
        } else if (ball.getY() > getHeight() - ballDiameter) {
            lifeCounter--;
            drawNewBall();
        } else if (collisionObject() != null) {
            if (collisionObject().getY() != paddle.getY()) {
                remove(collisionObject());
                bricksCounter--;
            }
            vy = -vy;
            drawBall();
        }
    }

    /**
     * Evaluates whether a game-over state has occurred.
     * Displays a win message if all bricks are gone, or a loss message if zero turns remain.
     *
     * @return true if the game is over (win or loss), false otherwise
     */
    private boolean gameOver() {
        if (bricksCounter == 0) {
            GLabel win = new GLabel(TEXT_WIN);
            win.setColor(Color.RED);
            win.setFont("Verdana-50");
            win.setVisible(true);
            win.setLocation(centerX - win.getWidth() / 2, centerY - win.getHeight() / 2);
            add(win);
            return true;
        }
        if (lifeCounter == 0) {
            GLabel gameOver = new GLabel(TEXT_LOST);
            gameOver.setColor(Color.BLACK);
            gameOver.setFont("Verdana-50");
            gameOver.setVisible(true);
            gameOver.setLocation(centerX - gameOver.getWidth() / 2, centerY - gameOver.getHeight() / 2);
            add(gameOver);
            return true;
        }
        return false;
    }

    /**
     * Renders a single brick on the canvas at specified coordinates.
     *
     * @param x     The X-coordinate of the upper-left corner of the brick
     * @param y     The Y-coordinate of the upper-left corner of the brick
     * @param w     The width of the brick in pixels
     * @param h     The height of the brick in pixels
     * @param color The fill and border color of the brick
     */
    private void drawBrick(double x, double y, double w, double h, Color color) {
        GRect brick = new GRect(x, y, w, h);
        brick.setFilled(true);
        brick.setColor(color);
        add(brick);
    }

    /**
     * Renders the complete multi-row grid of bricks at the top of the window.
     * Calculates the width of bricks dynamically to fit the window exactly and
     * assigns row colors in pairs according to standard Breakout game rules.
     */
    private void drawBricks() {
        int x = 0;
        int y = BRICK_Y_OFFSET;
        double brickWidth = (getWidth() - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
        int k = 0;
        Color[] setColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
        Color[] bricksRowsColors = new Color[NBRICK_ROWS];
        for (int i = 0; i < NBRICK_ROWS; i = i + 2) {
            bricksRowsColors[i] = setColors[k];
            if (i + 1 < NBRICK_ROWS) {
                bricksRowsColors[i + 1] = setColors[k];
            }
            k++;
            if (k == setColors.length) {
                k = 0;
            }
        }
        for (int i = 0; i < NBRICK_ROWS; i++) {
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                drawBrick(x + j * (brickWidth + BRICK_SEP), y + i * (BRICK_HEIGHT + BRICK_SEP), brickWidth,
                        BRICK_HEIGHT, bricksRowsColors[i]);
            }
        }
    }

    /**
     * Checks if the ball is fully located within the inner bounds of the application window.
     *
     * @return true if the ball has not breached any application boundary, false otherwise
     */
    private boolean ballCanMoveStraight() {
        if (ball.getX() >= 0 && ball.getY() >= 0 &&
                ball.getX() <= getWidth() - ballDiameter
                && ball.getY() <= getHeight() - ballDiameter) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prepares and initializes a new ball at the center of the window after a user click.
     * Randomizes the initial horizontal velocity parameter and direction.
     */
    private void drawNewBall() {
        if (lifeCounter > 0) {
            waitForClick();
            ball = new GOval(centerX - BALL_RADIUS, centerY - BALL_RADIUS,
                    ballDiameter, ballDiameter);
            RandomGenerator rgen = RandomGenerator.getInstance();
            vx = rgen.nextDouble(1.0, 3.0);
            if (rgen.nextBoolean(0.5)) {
                vx = -vx;
            }
            drawBall();
        }
    }

    /**
     * Moves the ball to its updated coordinates, applying current velocity values.
     * Includes a small programmatic pause for frame rate regulation and cleans up the prior frame instance.
     */
    private void drawBall() {
        ball = new GOval(ball.getX() + vx, ball.getY() + vy, ballDiameter, ballDiameter);
        ball.setFilled(true);
        ball.setColor(Color.BLACK);
        add((ball));
        pause(10);
        remove(ball);
    }

    /**
     * Instantiates the rectangular paddle at specified layout coordinates.
     *
     * @param x The initial X-coordinate of the paddle
     * @param y The initial Y-coordinate of the paddle
     */
    private void addPaddle(double x, double y) {
        paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setColor(Color.BLACK);
        add(paddle);
    }

    /**
     * Listens for mouse track movements. Updates the horizontal anchor position of the paddle
     * to trace the cursor position while clamping its margins inside the visible frame bounds.
     *
     * @param mouseEvent The native event package container containing structural layout data
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getX() >= PADDLE_WIDTH / 2 && mouseEvent.getX() <= getWidth() - PADDLE_WIDTH / 2) {
            paddle.setLocation(mouseEvent.getX() - PADDLE_WIDTH / 2,
                    getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        }
    }

    /**
     * Checks the four peripheral corners of the bounding square surrounding the ball
     * to look for any active graphical element overlaps on the canvas.
     *
     * @return The canvas element instance collided with, or null if no active overlap exists
     */
    /*private GObject collisionObject() {
        GObject[] obj = new GObject[]{getElementAt(ball.getX(), ball.getY()),
                getElementAt(ball.getX(), ball.getY() + ballDiameter),
                getElementAt(ball.getX() + ballDiameter, ball.getY()),
                getElementAt(ball.getX() + ballDiameter, ball.getY() + ballDiameter)
        };
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                return obj[i];
            }
        }
        return null;
    }*/
    /*private GObject collisionObject() {
        GObject obj;
        int[] delta = {0, 0, 0, ballDiameter, ballDiameter, 0, ballDiameter, ballDiameter};
        for (int i = 0; i < delta.length; i = i + 2) {
            if ((obj = getElementAt(ball.getX() + delta[i], ball.getY() + delta[i + 1])) != null) {
                return obj;
            }
        }
        return null;
    }*/

    private GObject collisionObject() {
        GObject obj;
        int[] delta = {0, ballDiameter};
        for (int i = 0; i < delta.length; i++) {
            for (int j = 0; j < delta.length; j++) {
                if ((obj = getElementAt(ball.getX() + delta[i],
                        ball.getY() + delta[j])) != null) {
                    return obj;
                }
            }
        }
        return null;
    }




        /*private GObject collisionObject() {
        if (getElementAt(ball.getX(), ball.getY()) != null) {
            return getElementAt(ball.getX(), ball.getY());
        } else if (getElementAt(ball.getX(), ball.getY() + ballDiameter) != null) {
            return getElementAt(ball.getX(), ball.getY() + ballDiameter);
        } else if (getElementAt(ball.getX() + ballDiameter, ball.getY()) != null) {
            return getElementAt(ball.getX() + ballDiameter, ball.getY());
        } else if (getElementAt(ball.getX() + ballDiameter, ball.getY() + ballDiameter) != null) {
            return getElementAt(ball.getX() + ballDiameter, ball.getY() + ballDiameter);
        } else {
            return null;
        }
        }*/


}
