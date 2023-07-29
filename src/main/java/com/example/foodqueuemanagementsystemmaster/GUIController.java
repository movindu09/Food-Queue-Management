/**import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable{

        @FXML private TableColumn<Customer, String> fName1;
        @FXML private TableColumn<Customer, String> fName2;
        @FXML private TableColumn<Customer, String> fName3;
        @FXML private TableColumn<Customer, String> fNameWait;
        @FXML private TextField filterField;
        @FXML private TableColumn<Customer, Integer> noOfburgers1;
        @FXML private TableColumn<Customer, Integer> noOfburgers2;
        @FXML private TableColumn<Customer, Integer> noOfburgers3;
        @FXML private TableColumn<Customer, Integer> noOfburgersWait;
        @FXML private TableView<Customer> queueTableView1;
        @FXML private TableView<Customer> queueTableView2;
        @FXML private TableView<Customer> queueTableView3;
        @FXML private TableView<Customer> queueTableViewWait;
        @FXML private TableColumn<Customer, String> sName1;
        @FXML private TableColumn<Customer, String> sName2;
        @FXML private TableColumn<Customer, String> sName3;
        @FXML private TableColumn<Customer, String> sNameWait;


        private final ObservableList<Customer> queues1 = FXCollections.observableArrayList();
        private final ObservableList<Customer> queues2 = FXCollections.observableArrayList();
        private final ObservableList<Customer> queues3 = FXCollections.observableArrayList();
        private final ObservableList<Customer> queueListWait = FXCollections.observableArrayList();

        @Override
        public void initialize(URL location, ResourceBundle resources)
        {
                setCells(fName1,sName1,noOfburgers1); //   queues
                setCells(fName2,sName2,noOfburgers2);
                setCells(fName3,sName3,noOfburgers3);
                setCells(fNameWait,sNameWait,noOfburgersWait);

                addAndSearch(queues1, queueTableView1, 1); //   queues
                addAndSearch(queues2, queueTableView2, 2);
                addAndSearch(queues3, queueTableView3, 3);


                for (int i = Cashier.front; i < Cashier.rear + 1; i++) // waiting list
                {
                        queueListWait.add(Cashier.waitingList[i]);
                }
                FilteredList<Customer> filteredData1 = new FilteredList<>(queueListWait, b -> true); // waiting list
                GUISearch(filteredData1,queueTableViewWait);
        }


        //Assigns the related object properties for the columns
        public void setCells(TableColumn<Customer,String> fName,TableColumn<Customer,String> sName,TableColumn<Customer,Integer> noOfburgers)
        {
                fName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                sName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
                noOfburgers.setCellValueFactory(new PropertyValueFactory<>("burgerCount"));
        }

        //Adds passengers to the respective observable list and Calls the "GUISearch" method
        public void addAndSearch(ObservableList<Customer> obserQueueList, TableView<Customer> queueTableView, int queuNo)
        {
                for (Customer Customer : Cashier.queues[queuNo-1].getLength())
                {
                        obserQueueList.add(Customer);
                }

                FilteredList<Customer> filteredData = new FilteredList<>(obserQueueList, b -> true);
                GUISearch(filteredData,queueTableView);
        }

        /**
         * This method was referenced from https://www.youtube.com/watch?v=FeTrcNBVWtg
         * This method makes the search function of the GUI work
         * The method was edited to suit the program

        public void GUISearch(FilteredList<Customer> filteredData , TableView<Customer> queueTableView)
        {
                filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredData.setPredicate(passenger -> {

                                if (newValue == null || newValue.isEmpty()) {
                                        return true;
                                }
                                String lowerCaseFilter = newValue.toLowerCase();
                                if (Customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                                        return true; // Filter matches first name.
                                } else if (Customer.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                                        return true; // Filter matches last name.
                                } else if (String.valueOf(Customer.getBurgerCount()).contains(lowerCaseFilter))
                                        return true;
                                else
                                        return false;
                        });
                });
                SortedList<Customer> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(queueTableView.comparatorProperty());
                queueTableView.setItems(sortedData);
        }
}*/
