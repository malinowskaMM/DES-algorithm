module project.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires Model;
    exports project.view;
    opens project.view to javafx.fxml;
}