package home;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class BoldLetters extends settingOption {
    public List<Button> gamma = new ArrayList<Button>();
    boolean is_default;
    public void settingOption(String name, List<Button> alpha) {
        super.settingOption(name);
        is_default = true;
        gamma = alpha;
    }

    @Override
    public void change_setting() {
        if (!is_default)
        {
            for (int x = 0; x < gamma.size(); ++x)
            {
                System.out.println(12);
                Button curr_button = gamma.get(x);
                Font font = Font.font("Courier New", FontWeight.BOLD, 17);
                curr_button.setFont(font);
            }
        }
        else
        {
            for (int x = 0; x < gamma.size(); ++x)
            {
                Button curr_button = gamma.get(x);
                Font font = Font.font("Courier New", 17);
                curr_button.setFont(font);
            }
        }
    }
}
