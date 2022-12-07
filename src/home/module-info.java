module home{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;


    opens home to javafx.fxml;
    exports home;
}