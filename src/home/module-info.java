module home{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens home to javafx.fxml;
    exports home;
}