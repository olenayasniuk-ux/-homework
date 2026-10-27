package com.shpp.p2p.cs.oyasniuk.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * A console application that estimates the number of syllables in a given word.
 */
public class SyllableCounting extends TextProgram {

    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesInWord(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        int syllables = 0;
        word = word.toLowerCase();

        if (isVowel(word.charAt(0))) {
            syllables++;
        }

        if (word.length() > 1) {
            for (int i = 1; i < word.length(); i++) {
                if (isVowel(word.charAt(i)) && !isVowel(word.charAt(i - 1))) {
                    syllables++;
                }
            }
            if (word.endsWith("e")) {
                syllables--;
            }
            if (word.endsWith("e") && isVowel(word.charAt(word.length() - 2))) {
                syllables++;
            }
        }

        if (syllables == 0) {
            syllables = 1;
        }
        return syllables;
    }

    /**
     * Determines whether the specified character is an English vowel.
     * Characters recognized as vowels include standard lowercase English vowels:
     * 'a', 'e', 'i', 'o', 'u', and 'y'.
     *
     * @param letter The character to check.
     * @return {@code true} if the character is a vowel; {@code false} otherwise.
     */
    private boolean isVowel(char letter) {
        char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};
        for (int i = 0; i < vowels.length; i++) {
            if (letter == vowels[i]) {
                return true;
            }
        }
        return false;
    }
}
