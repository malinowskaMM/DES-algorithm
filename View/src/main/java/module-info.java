module project.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.view to javafx.fxml;
    exports project.view;
}