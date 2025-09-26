import GUI.NotepadGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                    new NotepadGUI().setVisible(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}