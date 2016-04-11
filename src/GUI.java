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

        id.setText(String.valueOf(index + 1) + " [" + classification.id() + "]");

        ImageIcon image = new ImageIcon(classification.raw.toString());
        rawImage.setIcon(image);
        rawImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        image = new ImageIcon(classification.seg.toString());
        segImage.setIcon(image);
        segImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        image = new ImageIcon(classification.uhi.toString());
        uhiImage.setIcon(image);
        uhiImage.setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));

        String result;

        int trueClassification = classification.getTrueClassification();

        boolean correct = classification.getClassification() == trueClassification;

        if (trueClassification >= 0 && trueClassification <= 6) {

            if (correct)
                result = "(correct)";
            else {
                result = "(should be " + Classification.getReadableClassification(trueClassification) + ")";
            }
        }
        else if (trueClassification == 100)
            result = "(Occlusion)";
        else if (trueClassification == 200)
            result = "(Incorrect seg)";
        else
            result = "";

        classificationValue.setText(classification.getReadableClassification() + " " + result);
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
        }else if (e.getKeyCode() == KeyEvent.VK_1){
            Classification c = getCurrentClassification();
            c.setTrueClassification(1);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_2){
            Classification c = getCurrentClassification();
            c.setTrueClassification(2);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_3){
            Classification c = getCurrentClassification();
            c.setTrueClassification(3);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_4){
            Classification c = getCurrentClassification();
            c.setTrueClassification(4);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_5){
            Classification c = getCurrentClassification();
            c.setTrueClassification(5);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_6){
            Classification c = getCurrentClassification();
            c.setTrueClassification(6);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_O){
            Classification c = getCurrentClassification();
            c.setTrueClassification(100);
            loadClassification(classifications.get(index));
        }else if (e.getKeyCode() == KeyEvent.VK_E){
            Classification c = getCurrentClassification();
            c.setTrueClassification(200);
            loadClassification(classifications.get(index));
        }

    }

    private Classification getCurrentClassification() {
        return classifications.get(index);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
