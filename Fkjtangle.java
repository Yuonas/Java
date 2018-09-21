import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.animation.PathTransition;
import javafx.animation.Interpolator;

public class Fkjtangle extends Rectangle{
    int size;
    int duration;
    PathTransition pt = new PathTransition();
    Path path = new Path();

    Fkjtangle(int size, int duration, Color colour){
        super(size, size, colour);
        this.size = size;
        this.duration = duration;
        init();
    }

    Fkjtangle(int size, String image, int duration){
        super(size, size);
        this.size = size;
        this.duration = duration;
        img(image);
        init();
    }

    public void init(){
        pt.setNode(this);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setDuration(Duration.millis(duration));
        pt.setInterpolator(Interpolator.LINEAR);
    }

    public void setPath(Double x, Double y){
        path.getElements().clear();
        path.getElements().add(new MoveTo(getTranslateX()+size/2, getTranslateY()+size/2));
        path.getElements().add(new LineTo(x, y));
        pt.setPath(path);
    }

    public void img(String s){
        setFill(new ImagePattern(new FkjSVG(s, size, size)));
    }

	public void animate(Double x, Double y){
        KeyFrame kx = new KeyFrame(Duration.millis(duration), new KeyValue(this.xProperty(), x));
        KeyFrame ky = new KeyFrame(Duration.millis(duration), new KeyValue(this.yProperty(), y));
        new Timeline(kx, ky).play();
	}
}