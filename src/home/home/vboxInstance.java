package home;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class vboxInstance implements advancedInterface {
    @Override
    public void switchToVbox(Stage primaryStage) {
        ////nothing here
    }

    @Override
    public void switchToStage(VBox box) {
        //switches box to stage
        Stage convertedStage = new Stage();
        convertedStage.setMaxHeight(box.getMaxHeight());
        convertedStage.setMinHeight(box.getMinHeight());
        convertedStage.setMinHeight(box.getMinHeight());
        convertedStage.setMinWidth(box.getMinWidth());
        Scene newScene = new Scene(box);
        convertedStage.setScene(newScene);
        convertedStage.setX(box.getLayoutX());
        convertedStage.setX(box.getLayoutY());
    }
}