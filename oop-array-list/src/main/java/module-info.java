module ru.vsu.cs.ooparraylist {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    exports ru.vsu.cs.graphics;
    opens ru.vsu.cs.graphics to javafx.fxml;
}