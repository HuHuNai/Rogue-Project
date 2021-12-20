package trial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Trial extends Application {
    @Override
    public void start(Stage stage){
        Text text = new Text("122133");
        text.setLayoutX(100);
        text.setLayoutY(100);
        System.out.println( getClass().getResourceAsStream("/Font/kenvector_future.ttf"));
        text.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),25));
        Pane pane = new Pane();
        pane.setPrefSize(500,500);
        pane.getChildren().add(text);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    public void trial(){
        System.out.println(getClass().getResourceAsStream("/UI.fxml"));
    }

    public static void main(String[] args){
        Trial trial = new Trial();
        trial.trial();
    }
}
