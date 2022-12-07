package home;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class anyGui implements myInterface {
    guiAdapter guiAdapter;

    @Override
    public void display(String instanceType, VBox box, Stage stage) {
        if(instanceType.equalsIgnoreCase("stage") || instanceType.equalsIgnoreCase("box")){
            guiAdapter = new guiAdapter(instanceType);
            guiAdapter.display(instanceType,box,stage);
        }
        else{
            //the case where display is run on an invalid instance type
            System.out.println("Invalid instance. " + instanceType + " format not supported");
        }
    }
}
