package home;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class stageInstance implements advancedInterface {
    @Override
    public void switchToVbox(Stage primaryStage) {
        //switches stage to Vbox
        VBox convertedbox = new VBox();
        BorderRepeat BorderRepeat1 = null;
        BorderRepeat BorderRepeat2 = null;
        Border newborder = new Border(new BorderImage(new Image(""), new BorderWidths(0.00), new Insets(0.00),
                new BorderWidths(0.0), false, BorderRepeat1, BorderRepeat2));
        convertedbox.setSpacing(convertedbox.getWidth());
        convertedbox.setScaleX(primaryStage.getX());
        convertedbox.setScaleY(primaryStage.getY());
        convertedbox.setStyle(primaryStage.getTitle());
        convertedbox.setBorder(newborder);
    }

    @Override
    public void switchToStage(VBox box) {
        //nothing here
    }
}