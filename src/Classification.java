import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Darren on 4/9/2016.
 */
public class Classification {

    Path raw, seg, uhi, xml;

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
                    }else if (fileExt.equals("xml")){
                        xml = s;
                    }
                });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Classification){
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
