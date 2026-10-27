package com.shpp.p2p.cs.oyasniuk.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    /** The text field where users can input names to look up */
    JTextField textField;

    /** The reference instance containing the compiled historical name information */
    NameSurferDataBase dataBase;

    /** The central canvas element displaying graphs and layout grid lines */
    NameSurferGraph graph;

    /* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        dataBase = new NameSurferDataBase(NAMES_DATA_FILE);

        this.add(new JLabel("Name"), "North");

        textField = new JTextField(TEXT_LENGTH);
        this.add(textField, "North");

        this.add(new JButton("Graph"), "North");
        this.add(new JButton("Clear"), "North");

        graph = new NameSurferGraph();
        add(graph);

        this.addActionListeners();
    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Graph")) {
            pressGraphButton();
        } else if (e.getActionCommand().equals("Clear")) {
            clearAllGraphs();
            textField.setText("");
        }
    }
    /**
     * Handles the sequence when the 'Graph' button is triggered. Retrieves text,
     * checks for blank input, handles validation exceptions and attaches the valid graph.
     */
    private void pressGraphButton() {
        String name = textField.getText().toLowerCase();

        if (name.isEmpty()){
            // show message "You haven't entered any name."
            JOptionPane.showMessageDialog(
                    this,
                    "Ви не ввели ім'я.",
                    "Попередження",
                    JOptionPane.WARNING_MESSAGE
            );
        }else {
            NameSurferEntry entryData=dataBase.findEntry(name);
            if (entryData!=null){
                graph.addEntry(entryData);
                graph.update();
            }else{
                // show message "This name is not in the database."
                JOptionPane.showMessageDialog(
                        this,
                        "Такого імені немає у базі",
                        "Помилка",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Clears all plotted name entries out of the active graph workspace view.
     */
    private void clearAllGraphs() {
        graph.clear();
        graph.update();
    }
}
