package jp.niro.jimcon.tutorial;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.sql.ConnectionFactory;
import jp.niro.jimcon.sql.SQL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    public MainApp() {
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadPersonDataFromDataBase();

        /*  XMLファイルによるセーブ＆ロード処理をコメントアウト

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFormFile(file);
        }*/

    }

    public void showPersonOverview() {
        try {
            // Load person overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);


            // Show the dialog and wait the user closes it.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {

        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    public void loadPersonDataFormFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n " + file.getPath());

            alert.showAndWait();
        }
    }

    public void loadPersonDataFromDataBase() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jimcondb";
        String user = "fromclient";
        String password = "motpL@26";
        String query = new StringBuilder()
                .append("SELECT ")
                .append("`tutorial_person`.`firstName`, ")
                .append("`tutorial_person`.`lastName`, ")
                .append("`tutorial_person`.`street`, ")
                .append("`tutorial_person`.`postalCode`, ")
                .append("`tutorial_person`.`city`, ")
                .append("`tutorial_person`.`birthday` ")
                .append("FROM ")
                .append("`jimcondb`.`tutorial_person`;")
                .toString();

        SQL sql = null;

        try {
            sql = new SQL(ConnectionFactory.getConnection(
                    driver,
                    url,
                    user,
                    password));

            sql.preparedStatement(query);
            sql.executeQuery();

            while (sql.next()) {
                Person person = new Person();
                person.setFirstName(sql.getResultSet().getString(1));
                person.setLastName(sql.getResultSet().getString(2));
                person.setStreet(sql.getResultSet().getString(3));
                person.setPostalCode(sql.getResultSet().getInt(4));
                person.setCity(sql.getResultSet().getString(5));
                person.setBirthday(new MyDate(sql.getResultSet().getDate(6)));
                personData.add(person);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void savePersonDataToDataBase() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jimcondb";
        String user = "fromclient";
        String password = "motpL@26";
        String query = new StringBuilder()
                .append("INSERT INTO ")
                .append("`jimcondb`.`tutorial_person` (")
                .append("`tutorial_person`.`firstName`, ")
                .append("`tutorial_person`.`lastName`, ")
                .append("`tutorial_person`.`street`, ")
                .append("`tutorial_person`.`postalCode`, ")
                .append("`tutorial_person`.`city`, ")
                .append("`tutorial_person`.`birthday`)")
                .append("VALUES ")
                .append("(?, ?, ?, ?, ?, ?);")
                .toString();

        SQL sql = null;

        try {
            sql = new SQL(ConnectionFactory.getConnection(
                    driver,
                    url,
                    user,
                    password));

            // Check if there are records.
            sql.preparedStatement("SELECT `tutorial_person`.`firstName` FROM `jimcondb`.`tutorial_person`;");
            sql.executeQuery();
            if (sql.next()) {
                // Clear Data.
                sql.preparedStatement("TRUNCATE `jimcondb`.`tutorial_person`;");
                sql.executeUpdate();
            }

            sql.preparedStatement(query);
            
            for (Person person : personData) {
                sql.getPreparedStatement().setString(1, person.getFirstName());
                sql.getPreparedStatement().setString(2, person.getLastName());
                sql.getPreparedStatement().setString(3, person.getStreet());
                sql.getPreparedStatement().setInt(4, person.getPostalCode());
                sql.getPreparedStatement().setString(5, person.getCity());
                sql.getPreparedStatement().setDate(6,person.getBirthday().toDate());
                sql.executeUpdate();
			}



        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
