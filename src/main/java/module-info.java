module c195.task_1.java_c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens c195.task_1.java_c195 to javafx.fxml;
    exports c195.task_1.java_c195;
    exports c195.task_1.java_c195.Controller;
    exports c195.task_1.java_c195.Model;
    exports c195.task_1.java_c195.DAO;
    exports c195.task_1.java_c195.helper;
}