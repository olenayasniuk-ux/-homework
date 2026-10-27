package com.shpp.p2p.cs.oyasniuk.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A console application that implements a word puzzle game (often called the Road Trip Game).
 * The program prompts the user for a three-letter combination and searches through a dictionary
 * file to find all words that contain those three letters in the exact specified order,
 * though not necessarily consecutively.
 */
public class Assignment5Part3 extends TextProgram {
    /**
     * The relative path to the English dictionary file used for looking up valid words.
     */
    private static final String EN_DICTIONARY = "assets/en-dictionary.txt";

    public void run() {
        try {
            while (true) {
                BufferedReader br = new BufferedReader(new FileReader(EN_DICTIONARY));
                String userWord = readLine("Enter three letters:  ");
                userWord = userWord.toLowerCase();
                while (true) {
                    String word = br.readLine();
                    if (word == null) {
                        br.close();
                        break;
                    }
                    if (compare(userWord, word)) {
                        println(word);
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * Checks whether a dictionary word contains the three letters entered by the user
     * in the correct relative order.
     *
     * @param userWord A 3-character string entered by the user.
     * @param word     A dictionary word being checked against the sequence.
     * @return {@code true} if the word contains the sequence of characters in order;
     * {@code false} otherwise.
     */
    private boolean compare(String userWord, String word) {
        int j = 0;
        for (int i = 0; i < word.length(); i++) {
            if (userWord.charAt(j) == word.charAt(i)) {
                if (j == userWord.length() - 1) {
                    return true;
                }
                j++;
            }
        }
        return false;

        /*
        for (int i = 0; i < word.length(); i++) {
            if (userWord.charAt(0) == word.charAt(i)) {
                for (int j = i + 1; j < word.length(); j++) {
                    if (userWord.charAt(1) == word.charAt(j)) {
                        for (int k = j + 1; k < word.length(); k++) {
                            if (userWord.charAt(2) == word.charAt(k)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;*/
    }
}
