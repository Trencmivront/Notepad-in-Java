package GUI;

import javax.swing.*;

public class FontMenu extends JDialog {

    private NotepadGUI source;

    public FontMenu(NotepadGUI source){
        this.source = source;

        setTitle("Font Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //disposes used resources once closed
        setSize(425, 350);

        setLocationRelativeTo(source); // launches the menu at the center of our notepad GUI
        setModal(true); //can't interact with the notepad until the menu is closed

        //removes layout management, giving us more control on the placement of out gui components
        setLayout(null);

        addMenuComponents();

    }

    private void addMenuComponents(){
        addFontChooser();
    }

    private void addFontChooser(){

        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10, 15, 125, 10);
        add(fontLabel);

        //font panel will display the current font and the list of fonts available
        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        //display current font
        JTextField currentFontField = new JTextField();

    }





}
