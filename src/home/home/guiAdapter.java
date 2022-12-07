package home;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class guiAdapter implements myInterface {

    advancedInterface advancedInterface;
    // CREATES new instances for vbox and stage
    public guiAdapter(String instanceType){

        if(instanceType.equalsIgnoreCase("vbox")){
            advancedInterface = new vboxInstance();

        }else if (instanceType.equalsIgnoreCase("stage")){
            advancedInterface = new stageInstance();
        }
    }

    @Override
    public void display(String instanceType, VBox box, Stage stage) {

        if(instanceType.equalsIgnoreCase("vbox")){
            advancedInterface.switchToStage(box);
        }
        else if(instanceType.equalsIgnoreCase("stage")){
            advancedInterface.switchToVbox(stage);
        }
    }

}
