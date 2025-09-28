package GUI;

import javax.swing.*;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class FontMenu extends JDialog {

    private static NotepadGUI source = new NotepadGUI();

    // non-static variables makes variables change by each new FontMenu object.
    // because of that font of textArea returns to it's initial values
    // we make them static for when we change variables, other objects will have same values
    private String font = source.getTextArea().getFont().getFontName();
    private int style = source.getTextArea().getFont().getStyle();
    private int size = source.getTextArea().getFont().getSize();

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setFont();
            }
        });
    }

    private void addMenuComponents(){
        addFontChooser();
        addStyleChooser();
        addSizeChooser();
    }

    private void addFontChooser(){

        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10, 5, 125, 15);
        add(fontLabel);

        //font panel will display the current font and the list of fonts available
        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        //display current font
        JTextField currentFontField = new JTextField(source.getTextArea().getFont().getFontName());
        currentFontField.setEditable(false);
        currentFontField.setPreferredSize(new Dimension(125, 20));
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
                   font = fontNameLabel.getText();
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
        currentFontStyleField.setPreferredSize(new Dimension(125, 20));
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
                style = plainStyle.getFont().getStyle();
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
                style = boldStyle.getFont().getStyle();
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
                style = italicStyle.getFont().getStyle();
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
                style = boldItalicStyle.getFont().getStyle();
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

    private void addSizeChooser(){

        JLabel fontSizeLabel = new JLabel("Font Size:");
        fontSizeLabel.setBounds(275, 5, 125, 15);
        add(fontSizeLabel);

        // will show the current font size and list of font sizes to choose from
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBounds(275, 15, 125, 160);


        JTextField currentFontSizeField = new JTextField(Integer.toString(source.getTextArea().getFont().getSize() - source.getZoomValue()));

        currentFontSizeField.setPreferredSize(new Dimension(125, 20));
        currentFontSizeField.setEditable(false);
        fontSizePanel.add(currentFontSizeField);

        JPanel listOfFontSizesPanel = new JPanel();
        listOfFontSizesPanel.setLayout(new BoxLayout(listOfFontSizesPanel, BoxLayout.Y_AXIS));

        listOfFontSizesPanel.setBackground(Color.WHITE);

        for(int i = 1; i <= 30; i++){
            JLabel fontSize = new JLabel(Integer.toString(i));
            int a = i;
            fontSize.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    size = a + source.getZoomValue();
                    currentFontSizeField.setText(fontSize.getText());
                    currentFontSizeField.setBackground(Color.GRAY);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    fontSize.setOpaque(true);
                    fontSize.setBackground(Color.LIGHT_GRAY);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    fontSize.setBackground(null);
                }
            });

            listOfFontSizesPanel.add(fontSize);
        }

        JScrollPane scrollPane = new JScrollPane(listOfFontSizesPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));

        fontSizePanel.add(scrollPane);

        add(fontSizePanel);

    }

    private void setFont(){
        source.getTextArea().setFont(new Font(font, style, size));
    }

}
