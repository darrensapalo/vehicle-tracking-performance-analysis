import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by Darren on 4/9/2016.
 */
public class GUI extends JFrame implements KeyListener {


    private JLabel classificationValue;
    private JLabel id;
    private JLabel segImage;
    private JLabel rawImage;
    private JLabel uhiImage;

    private int index = 0;
    private List<Classification> classifications;

    public GUI(List<Classification> classifications){
        super("Classification analysis");
        this.classifications = classifications;
        setLayout(new GridLayout(5, 2, 5, 5));
        setPreferredSize(new Dimension(300, 700));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        addKeyListener(this);

        if (classifications.size() > 0) {
            Classification classification = classifications.get(index);

            JLabel label = new JLabel("ID");
            add(label);

            id = new JLabel();
            id.setText("1");
            add(id);


            label = new JLabel("Raw image");
            add(label);

            ImageIcon image = new ImageIcon(classification.raw.toString());
            rawImage = new JLabel(image);
            rawImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
            add(rawImage);

            label = new JLabel("Segmented image");
            add(label);

            image = new ImageIcon(classification.seg.toString());
            segImage = new JLabel(image);
            segImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
            add(segImage);

            label = new JLabel("Raw with Uhi image");
            add(label);

            image = new ImageIcon(classification.uhi.toString());
            uhiImage = new JLabel(image);
            uhiImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
            add(uhiImage);

            label = new JLabel("Classification");
            add(label);


            classificationValue = new JLabel("Value");
            add(classificationValue);
            pack();
            loadClassification(classification);
        }

    }

    private void loadClassification(Classification classification) {

        id.setText(String.valueOf(index + 1));

        ImageIcon image = new ImageIcon(classification.raw.toString());
        rawImage.setIcon(image);
        rawImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        image = new ImageIcon(classification.seg.toString());
        segImage.setIcon(image);
        segImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        image = new ImageIcon(classification.uhi.toString());
        uhiImage.setIcon(image);
        uhiImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        classificationValue.setText(getClassification(classification));
    }

    private String getClassification(Classification classification) {
        File f = new File(classification.xml.toString());
        int classif = -1;
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()){
                String line = s.nextLine();
                if (line.contains("class"))
                {
                    line = line.replace("<class>", "");
                    line = line.replace("</class>", "");
                    classif = Integer.parseInt(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        switch (classif){
            case 1: return "Sedan";
            case 2: return "Bus";
            case 3: return "Jeep";
            case 4: return "Truck";
            case 5: return "SUV";
            case 6: return "Others";
        }
        return "N/A";
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            if (index < classifications.size() - 1) {
                index++;
                loadClassification(classifications.get(index));
            }
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if (index > 0) {
                index--;
                loadClassification(classifications.get(index));
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
