package home;

public class SettingsHandler {
    public settingOption boldFont, highContrast, darkMode, textToSpeach;
    private boolean saved;


    public void SettingsHandler(settingOption boldFont, settingOption highContrast, settingOption darkMode, settingOption textToSpeach)
    {
        this.boldFont = boldFont;
        this.highContrast = highContrast;
        this.darkMode = darkMode;
        this.textToSpeach = textToSpeach;
    }
    public void update_setting(settingOption curr_option)
    {
        if (curr_option.is_default){curr_option.is_default = false;}
        else{curr_option.is_default = true;}
    }
    public void update_all()
    {
        boldFont.change_setting();
        highContrast.change_setting();
        darkMode.change_setting();
        textToSpeach.change_setting();
    }
    public void reset_all()
    {
        boldFont.is_default = true;
        highContrast.is_default = true;
        darkMode.is_default = true;
        textToSpeach.is_default = true;
    }
}
