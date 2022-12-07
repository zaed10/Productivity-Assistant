package home;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class darkMode extends settingOption {
    public String name_of_setting;
    public boolean default_setting;
    public List<Button> gamma = new ArrayList<Button>();
    public BorderPane alpha;


    public void settingOption(String name, List<Button> zeta, BorderPane curr_pane) {
        name_of_setting = name;
        default_setting = true;
        gamma = zeta;
        this.alpha = curr_pane;
    }
    public void change_setting()
    {
        if (default_setting)
        {
            alpha.setStyle("-fx-background-color : #53639F");
            for (int i = 0; i < gamma.size(); ++i)
            {
                Button curr_button = gamma.get(i);
                curr_button.setStyle("-fx-background-color: #FFA500; ");
            }
        }
        else
        {
            alpha.setStyle("-fx-background-color : #5A5A5A");
            for (int i = 0; i < gamma.size(); ++i)
            {
                Button curr_button = gamma.get(i);
                curr_button.setStyle("-fx-background-color: #D3D3D3; ");
            }
        }
    }
}