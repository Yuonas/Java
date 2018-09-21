import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class FkjSVG extends WritableImage{
  FkjSVG(String file, int imgWidth, int imgHeight){
    super(imgWidth, imgHeight);
    Group SVGGroup = new Group();
    int svgWidth = 0;
    int svgHeight = 0;
    try {
		File f = new File(file);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String line;
		Pattern r = Pattern.compile("(\\w+-*\\w*=\"[^\"]*\")");

      	while ((line = b.readLine()) != null) {
	      	if (!Pattern.compile("^\\s*<!--").matcher(line).find()) {
	        	Matcher m = r.matcher(line);
		        if (line.contains("<svg")) {
		          while (m.find()) {
		            if (find(m.group(), "^width=")) {
		              svgWidth = Integer.parseInt(val(m.group()));
		            } else if (find(m.group(), "^height=")) {
		              svgHeight = Integer.parseInt(val(m.group()));
		            }
		          }
		          SVGPath path = new SVGPath();
		          path.setContent("M0,0 H"+svgWidth+"V"+svgHeight+"H 0");
		          path.setFill(Color.TRANSPARENT);
		          SVGGroup.getChildren().add(path);
		        } else if (line.contains("<path")) {
		          SVGPath path = new SVGPath();
		          path.setSmooth(true);
		          path.setStrokeLineCap(StrokeLineCap.BUTT);
		          while (m.find()) {
		            if (find(m.group(), "^d=")) {
		              path.setContent(val(m.group()));
		            } else if (find(m.group(), "^fill=")) {
		              path.setFill(Color.web(val(m.group())));
		            } else if (find(m.group(), "^fill-rule=")) {
		              path.setFillRule(FillRule.NON_ZERO);
		            } else if (find(m.group(), "^stroke=")) {
		              path.setStroke(Color.web(val(m.group())));
		            } else if (find(m.group(), "^stroke-width=")) {
		              path.setStrokeWidth(Float.parseFloat(val(m.group())));
		            } else if (find(m.group(), "^stroke-linejoin=")) {
		              path.setStrokeLineJoin(StrokeLineJoin.MITER);
		            } else if (find(m.group(), "^stroke-linecap=")) {
		              path.setStrokeLineCap(StrokeLineCap.BUTT);
		            } else if (find(m.group(), "^stroke-dashoffset=")) {
		              path.setStrokeDashOffset(Double.parseDouble(val(m.group())));
		            } else if (find(m.group(), "^stroke-miterlimit=")) {
		              path.setStrokeMiterLimit(Double.parseDouble(val(m.group())));
		            }
		          }
		          SVGGroup.getChildren().add(path);
		        }
	      	}
  		}
      	SVGGroup.setScaleX((float) imgWidth/svgWidth);
      	SVGGroup.setScaleY((float) imgHeight/svgHeight);
    } catch (Exception e) {}
    SnapshotParameters sp = new SnapshotParameters();
    sp.setFill(Color.TRANSPARENT);
    PixelReader reader = SVGGroup.snapshot(sp, new WritableImage(imgWidth, imgHeight)).getPixelReader();
    PixelWriter writer = getPixelWriter();
    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        writer.setColor(x, y, reader.getColor(x, y));
      }
    }
  }

  public String val(String s){
    return s.substring(s.indexOf("=")+2, s.length()-1);
  }

  public boolean find(String s, String needle){
    Pattern r = Pattern.compile(needle);
    Matcher m = r.matcher(s);
    return m.find() ? true:false;
  }
}