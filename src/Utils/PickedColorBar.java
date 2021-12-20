package Utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class PickedColorBar extends VBox {
    private final ProgressBar bar;
    private final ColorPicker picker;

    private boolean wasShownCalled = false;

    final ChangeListener<Color> COLOR_LISTENER = new ChangeListener<Color>() {
        @Override public void changed(ObservableValue<? extends Color> value, Color oldColor, Color newColor) {
            setBarColor(bar, newColor);
        }
    };

    public void setProgress(double progress){
        bar.setProgress(progress);
    }

    public PickedColorBar(double progress, Color initColor) {
        bar    = new ProgressBar(progress);
        picker = new ColorPicker(initColor);
        setPrefSize(100,100);
        setSpacing(10);
        setBarColor(bar,initColor);
        setAlignment(Pos.CENTER);
        getChildren().setAll(bar);
    }

    // invoke only after the progress bar has been shown on a stage.
    public void wasShown() {
        if (!wasShownCalled) {
            wasShownCalled = true;
            setBarColor(bar, picker.getValue());
            picker.valueProperty().addListener(COLOR_LISTENER);
        }
    }

    private void setBarColor(ProgressBar bar, Color newColor) {
        bar.lookup(".bar").setStyle("-fx-background-color: -fx-box-border, " + createGradientAttributeValue(newColor));
    }

    private String createGradientAttributeValue(Color newColor) {
        String hsbAttribute = createHsbAttributeValue(newColor);
        return "linear-gradient(to bottom, derive(" + hsbAttribute+ ",30%) 5%, derive(" + hsbAttribute + ",-17%))";
    }

    private String createHsbAttributeValue(Color newColor) {
        return
                "hsb(" +
                        (int)  newColor.getHue()               + "," +
                        (int) (newColor.getSaturation() * 100) + "%," +
                        (int) (newColor.getBrightness() * 100) + "%)";
    }
}
