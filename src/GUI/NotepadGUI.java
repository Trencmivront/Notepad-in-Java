package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotepadGUI extends JFrame {
    private JFileChooser fileChooser;
    private JTextArea textArea;

    private File currentFile;

    public NotepadGUI(){
        setTitle("notepad");

        setSize(500,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));

        addComponents();
    }

    public void addComponents(){

        addToolBar();

        //area to text
        textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);

    }

    public void addToolBar(){
        JToolBar toolBar = new JToolBar();

        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();

        menuBar.add(addFileMenu());

        add(menuBar , BorderLayout.NORTH);

    }

    public JMenu addFileMenu(){
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
                if(currentFile == null) saveAsMenuItem.doClick();

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
            public void actionPerformed(ActionEvent e) {
                //dispose of this gui
                NotepadGUI.this.dispose();
            }
        });
        file.add(exitMenuItem);

        return file;
    }

}
