package GUI;

import javax.swing.*;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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
        addStyleChooser();
    }

    private void addFontChooser(){

        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10, 15, 125, 10);
        add(fontLabel);

        //font panel will display the current font and the list of fonts available
        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        //display current font
        JTextField currentFontField = new JTextField(source.getTextArea().getFont().getFontName());
        currentFontField.setEditable(false);
        fontPanel.add(currentFontField);

        //display list of available fonts
        JPanel listOfFontsPanel = new JPanel();

        // changes our layout to only have one column to display each font properly
        listOfFontsPanel.setLayout(new BoxLayout(listOfFontsPanel, BoxLayout.Y_AXIS));

        // change the background color to white
        listOfFontsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listOfFontsPanel);
        scrollPane.setPreferredSize(new Dimension (125, 125));

        // retrieve all of the possible fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        String[] fontNames = ge.getAvailableFontFamilyNames();

        for(String fontName : fontNames){
            JLabel fontNameLabel = new JLabel(fontName);
            fontNameLabel.setFont(new Font(fontName, 0, 15));

            fontNameLabel.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   // when clicked set current font of textArea to the font selected
                   source.getTextArea().setFont(new Font(fontNameLabel.getText(),
                           source.getTextArea().getFont().getStyle(),
                           source.getTextArea().getFont().getSize()));
                   // when clicked set currentFontField to font name
                   currentFontField.setText(fontNameLabel.getText());
                   fontNameLabel.setBackground(Color.GRAY);
               }

               @Override
               public void mouseEntered(MouseEvent e) {
                   // add highlight when mouse on the label
                   fontNameLabel.setOpaque(true);
                   fontNameLabel.setBackground(Color.LIGHT_GRAY);
               }

               @Override
               public void mouseExited(MouseEvent e) {
                   fontNameLabel.setBackground(null);
               }

           });

                    // add to panel
                    listOfFontsPanel.add(fontNameLabel);
        }

        fontPanel.add(scrollPane);
        add(fontPanel);

    }

    private void addStyleChooser(){

        JLabel fontStyleLabel = new JLabel("Font Style:");
        fontStyleLabel.setBounds(145, 5, 125, 15);
        add(fontStyleLabel);

        // style panel will show the current font style and font styles available
        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145, 15, 125, 160);

        // current font style
        int currentFontStyle = source.getTextArea().getFont().getStyle();

        String currentFontStyleText;

        switch(currentFontStyle){
            case Font.PLAIN:
                currentFontStyleText = "Plain";
                break;
            case Font.BOLD:
                currentFontStyleText = "Bold";
                break;
            case Font.ITALIC:
                currentFontStyleText = "Italic";
                break;
            default:
                currentFontStyleText = "Bold Italic";
                break;
        }

        JTextField currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(60, 15));
        currentFontStyleField.setEditable(false);
        fontStylePanel.add(currentFontStyleField);

        // display list of all font style available
        JPanel listOfFontStylesPanel = new JPanel();

        // make the layout have only one column like we did earlier with names
        listOfFontStylesPanel.setLayout(new BoxLayout(listOfFontStylesPanel, BoxLayout.Y_AXIS));
        listOfFontStylesPanel.setBackground(Color.WHITE);

        //list of font styles
        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog", Font.PLAIN, 12));
        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                currentFontStyleField.setText(plainStyle.getText());
                plainStyle.setBackground(Color.GRAY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                plainStyle.setOpaque(true);
                plainStyle.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plainStyle.setBackground(null);
            }
        });
        listOfFontStylesPanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog", Font.BOLD, 12));

        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                currentFontStyleField.setText(boldStyle.getText());

                boldStyle.setBackground(Color.GRAY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boldStyle.setOpaque(true);
                boldStyle.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldStyle.setBackground(null);
            }
        });
        listOfFontStylesPanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog", Font.ITALIC, 12));

        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                currentFontStyleField.setText(italicStyle.getText());
                italicStyle.setBackground(Color.GRAY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                italicStyle.setOpaque(true);
                italicStyle.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                italicStyle.setBackground(null);
            }
        });
        listOfFontStylesPanel.add(italicStyle);

        JLabel boldItalicStyle = new JLabel("Bold Italic");
        boldItalicStyle.setFont(new Font("Dialog", Font.ITALIC | Font.BOLD, 12));

        boldItalicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                currentFontStyleField.setText(boldItalicStyle.getText());
                boldItalicStyle.setBackground(Color.GRAY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boldItalicStyle.setOpaque(true);
                boldItalicStyle.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldItalicStyle.setBackground(null);
            }
        });
        listOfFontStylesPanel.add(boldItalicStyle);

        JScrollPane scrollPane = new JScrollPane(listOfFontStylesPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));

        fontStylePanel.add(scrollPane);
        add(fontStylePanel);

    }


}
