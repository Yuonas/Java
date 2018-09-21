import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;
import java.util.Map;
import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.beans.InvalidationListener;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ParallelTransition;
import java.util.List;

public class Line extends Application {
	int duration = 100;
	int distance = 40;
	int direction = -2;
	int theSize = 3;
	int factor = 3;
	int scaleDelay = 4;
	ParallelTransition movement = new ParallelTransition();
	Map<KeyCode, Integer> keys = Map.of(KeyCode.UP, -1, KeyCode.LEFT, 0, KeyCode.DOWN, 1, KeyCode.RIGHT, 2);

    public static void main(String[] args){ 
    	launch(args);
    }

    public void start(Stage stage){
    	Path path = new Path();
		path.getElements().add(new MoveTo(100.0F, 100.0F));
		path.getElements().add(new LineTo(100.0f, 100.0f));
		path.setStroke(Color.FORESTGREEN);
        path.setStrokeWidth(0);

        Group root = new Group(path);
        Scene scene = new Scene(root, 1000, 578, Color.ALICEBLUE);
        stage.setScene(scene);
        stage.show();

        expand("Head", root).setPath(120.0, 100.0);
		expand("Tail", root).setPath(80.0, 100.0);

        SimpleIntegerProperty lapsCounter = new SimpleIntegerProperty();
        Timeline interval = new Timeline(new KeyFrame(Duration.millis(duration/factor), e -> {
    		
    		path.getElements().add(new LineTo(getLine(path, 0).getX() + getDist(true)/factor, getLine(path, 0).getY() + getDist(false)/factor));
	        if (lapsCounter.get() == scaleDelay) {
	        	for (int i = 0; i < getList().size(); i++) getPart(i).setScaleY(getPart(i).getScaleY() == 1 ? -1:1);
	        	lapsCounter.set(0);
	        }
	        lapsCounter.set(lapsCounter.get()+1);

	        if (path.getElements().size()/factor > getList().size() && getList().size() <= theSize) {
	        	expand("Body", root);
	    	}
        }));
        interval.setCycleCount(Timeline.INDEFINITE);

        movement.setOnFinished(e -> {
        	for (int i = 0; i < getList().size(); i++){
        		double one = i == 0 ? 0.97:0;
        		LineTo end = getLine(path, (int)Math.round(i-1+one)*factor);
        		getPart(i).setPath(end.getX() + getDist(true)*one, end.getY() + getDist(false)*one);
        	}
    		movement.play();
    	});

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> key(e, interval));
    }

    public Fkjtangle expand(String name, Group root){
    	// Fkjtangle a = new Fkjtangle(40, "Resources/Images/snake"+name+".svg", duration);
    	Fkjtangle a = new Fkjtangle(40, duration, Color.FORESTGREEN);
    	int pos = getList().size()-(getList().size() > 1 ? 1:0);
    	int head = getList().size();
		a.setScaleY(head == 0 ? 1:getPart(-1).getScaleY()*-1);
		a.setTranslateX(40+ (head == 0 ? 40:1) );
		a.setTranslateY(80);
		root.getChildren().add(pos, a);
		getList().add(pos, a.pt);
		return a;
    }

    public LineTo getLine(Path path, int i){
    	return (LineTo)path.getElements().get(path.getElements().size()-1-i);
    }

    public List<Animation> getList(){
    	return movement.getChildren();
    }

    public Fkjtangle getPart(int i){
    	return (Fkjtangle)((PathTransition)getList().get(i < 0 ? getList().size()-1:i)).getNode();
    }

    public void key(KeyEvent event, Timeline t) {
    	if (keys.containsKey(event.getCode())){
    		t.play();
    		movement.play();
    		direction = keys.get(event.getCode());
    	}
    }

    public int getDist(Boolean horizontal){
    	if (horizontal) {
    		return direction%2 == 0 ? distance*(direction-1):0;
    	} else {
    		return direction%2 != 0 ? distance*direction:0;
    	}
    }
}