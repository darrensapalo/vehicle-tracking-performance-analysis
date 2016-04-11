import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Created by Darren on 4/9/2016.
 */
public class Classification {

    Path raw, seg, uhi, xml;
    private int trueClassification;
    private String size, linearity, classif;

    public void setTrueClassification(int trueClassification) {
        this.trueClassification = trueClassification;
        save();
    }

    public int getTrueClassification() {
        return trueClassification;
    }

    public int id() {
        String withoutClass = raw.getFileName().toString().substring(2);
        String id = withoutClass.substring(0, withoutClass.length() - 4);
        return Integer.parseInt(id);
    }

    public void save() {
        try {
            FileWriter fw = new FileWriter(new File(xml.toString()));
            fw.write("<?xml version=\"1.0\"?>\n" +
                    "<opencv_storage>\n");

            fw.write("<size>" + size + "</size>\n");
            fw.write("<linearity>" + linearity + "</linearity>\n");
            fw.write("<class>" + classif + "</class>\n");
            fw.write("<trueclass>" + trueClassification + "</trueclass>\n");
            fw.write("</opencv_storage>\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String get(String str) {
        File f = new File(xml.toString());

        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains(str)) {
                    line = line.replace("<" + str + ">", "");
                    line = line.replace("</" + str + ">", "");
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Classification(Path filePath) throws IOException {

        Files.walk(filePath)
                .filter(Files::isRegularFile)
                .forEach(s -> {
                    String f = s.toString();
                    int length = f.length();
                    String fileExt = f.substring(length - 3, length);
                    String fileExtMore = f.substring(length - 7, length - 4);

                    if (fileExt.equals("png")) {
                        switch (fileExtMore) {
                            case "seg":
                                seg = s;
                                break;
                            case "uhi":
                                uhi = s;
                                break;
                            default:
                                raw = s;
                                break;
                        }
                    } else if (fileExt.equals("xml")) {
                        xml = s;
                    }
                });

        trueClassification = -1;

        size = get("size").trim();
        linearity = get("linearity").trim();
        classif = get("class").trim();
        String trueclass = get("trueclass");
        if (trueclass != null)
            trueClassification = Integer.parseInt(trueclass.trim());
        else
            trueClassification = -1;
    }

    public int getClassification() {
        File f = new File(xml.toString());
        int classif = -1;
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains("<class")) {
                    line = line.replace("<class>", "");
                    line = line.replace("</class>", "");
                    classif = Integer.parseInt(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return classif;
    }

    public static String getReadableClassification(int i) {
        switch (i) {
            case 1:
                return "Sedan";
            case 2:
                return "Bus";
            case 3:
                return "Jeep";
            case 4:
                return "Truck";
            case 5:
                return "SUV";
            case 6:
                return "Others";
            case 100:
                return "Occlusion";
            case 200:
                return "Incorrect seg";
        }
        return "N/A";
    }

    public String getReadableClassification() {
        switch (getClassification()) {
            case 1:
                return "Sedan";
            case 2:
                return "Bus";
            case 3:
                return "Jeep";
            case 4:
                return "Truck";
            case 5:
                return "SUV";
            case 6:
                return "Others";
        }
        return "N/A";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Classification) {
            Classification o = (Classification) obj;
            return this.raw.toString().equals(o.raw.toString());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return raw.hashCode() + seg.hashCode() + uhi.hashCode() + xml.hashCode();
    }
}
