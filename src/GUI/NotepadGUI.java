package GUI;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotepadGUI extends JFrame {
    private JFileChooser fileChooser;

    //we define textArea as a global variable to make it usable by more than one actions
    private JTextArea textArea;
    //because of our textArea is private method, we can't get it directly so we use getTextArea function
    public JTextArea getTextArea(){return textArea;}

    private File currentFile;

    //Swing's built in library to manage undo and redo functionalities
    private UndoManager undoManager;

    public NotepadGUI(){
        setTitle("notepad");

        setSize(500,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));

        undoManager = new UndoManager();

        addGUIComponents();
    }

    public void addGUIComponents(){

        addToolBar();

        //area to text
        textArea = new JTextArea();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                //adds each edit that we do in the text area (either adding or removing text)
                undoManager.addEdit(e.getEdit());
            }
        });

        //moves the page as we write characters
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

    }

    public void addToolBar(){
        JToolBar toolBar = new JToolBar();

        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();

        //add menus
        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());
        menuBar.add(addFormatMenu());

        add(menuBar , BorderLayout.NORTH);

    }

    private JMenu addFileMenu(){
        JMenu file = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        file.add(newMenuItem);

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //reset title header
                setTitle("Notepad");
                //reset text area
                textArea.setText("");
                //reset current file
                currentFile = null;
            }
        });

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //open file explorer
                int result = fileChooser.showOpenDialog(NotepadGUI.this);

                if((result != JFileChooser.APPROVE_OPTION)) return;

                try {
                    //reset notepad
                    //clicks on the button
                    newMenuItem.doClick();

                    //get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    //update the current file
                    currentFile = selectedFile;

                    //read the file
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    //change title of the gui
                    setTitle(selectedFile.getName());

                    //StringBuffer is used to store multiple strings more thread safe
                    StringBuilder fileText = new StringBuilder();
                    String readText;
                    while((readText = bufferedReader.readLine()) != null){
                        //append the line and then go to next line
                        fileText.append(readText + "\n");
                    }

                    textArea.setText(fileText.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(NotepadGUI.this, e.getMessage());
                }

            }
        });
        file.add(openMenuItem);

       JMenuItem saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //open save dialog
                int result = fileChooser.showSaveDialog(NotepadGUI.this);

                //continue to execute code only if user pressed the save button
                if(result != JFileChooser.APPROVE_OPTION) return;
                try{
                    //holding selected file as a file object
                    File selectedFile = fileChooser.getSelectedFile();
                    //we will need to append .txt to the file if it does not have the txt extension

                    //we get the file name from our selected file
                    String fileName = selectedFile.getName();
                    //ignores the lower and upper case equality
                    if(!fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")){
                        //the getAbsoluteFile() method gets abstract pathname of selectedFile
                        selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                    }

                    //create new file
                    selectedFile.createNewFile();

                    //now we will write the user's text into the file we just created
                    //FileWriter is for writing streams of characters
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    //BufferedWriter writes text to a file, one line or one string at a time
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    //will write the text taken from textArea
                    bufferedWriter.write(textArea.getText());
                    //closing streams for saving text
                    bufferedWriter.close();
                    fileWriter.close();

                    //update the title header of gui to save text file
                    setTitle(fileName);

                    //update current file
                    currentFile = selectedFile;

                    //show display dialog
                    //will show a Windows-based message dialog box
                    JOptionPane.showMessageDialog(NotepadGUI.this, "Saved File");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        file.add(saveAsMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //if the current file is null then we have to perform save as functionality
                if(currentFile == null) {
                    saveAsMenuItem.doClick();
                    return;
                }

                //if the user chooses to cancel saving the file this means that current file will still
                //be null, then we want to prevent executing the rest of the code
                if(currentFile == null) return;
                try{
                    //write to current file
                    FileWriter fileWriter = new FileWriter(currentFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    bufferedWriter.write(textArea.getText());

                    bufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(NotepadGUI.this, "Saved Succesfully");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        file.add(saveMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //dispose of this gui

                NotepadGUI.this.dispose();
            }
        });
        file.add(exitMenuItem);

        return file;
    }

    private JMenu addEditMenu(){

        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //means that if there are any edits that we can undo, then we undo them
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        editMenu.add(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });
        editMenu.add(redoMenuItem);

        return editMenu;
    }

    private JMenu addFormatMenu(){
        JMenu formatMenu = new JMenu("Format");

        //wrap word functionality
        JCheckBoxMenuItem wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
        wordWrapMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isChecked = wordWrapMenuItem.getState();

                if(isChecked){
                    //wraps by character
                    textArea.setLineWrap(true);

                    //changes the wrapping style to word
                    textArea.setWrapStyleWord(true);
                }
                else
                    textArea.setLineWrap(false);
            }
        });
        formatMenu.add(wordWrapMenuItem);

        //aligning text
        JMenu alignTextMenu = new JMenu("Align Text");

        //align text to the lef
        JMenuItem alignTextLeftMenuItem = new JMenuItem("Left");
        alignTextLeftMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }
        });
        alignTextMenu.add(alignTextLeftMenuItem);

        //align text to the right
        JMenuItem alignTextRightMenuItem = new JMenuItem("Right");
        alignTextRightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        });
        alignTextMenu.add(alignTextRightMenuItem);

        formatMenu.add(alignTextMenu);

        //font format
        JMenuItem fontMenuItem = new JMenuItem("Font...");
        fontMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //launch font menu
                new FontMenu(NotepadGUI.this).setVisible(true);

            }
        });
        formatMenu.add(fontMenuItem);

        return formatMenu;
    }

}
