module project.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires Model;
    requires java.desktop;
    requires org.apache.commons.io;
    exports project.view;
    opens project.view to javafx.fxml;
}