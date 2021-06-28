/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrassi"
    private Button btnGrassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int porzioni;
    	try {
    		porzioni= Integer.parseInt(txtPorzioni.getText());
   
    	}catch(NumberFormatException e) {
    		txtResult.appendText("scrivere un numero di porzioni");
    		return;
    	}
    	model.creaGrafo(porzioni);
    	
    	txtResult.appendText("Creazione grafo...\n");
    	txtResult.appendText("#vertici: "+model.numVertici()+"\n");
    	txtResult.appendText("#archi: "+model.numArchi()+"\n");
    	
    	boxFood.getItems().clear();
    	boxFood.getItems().addAll(model.getVertici());
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	Food f= boxFood.getValue();
    	if(f==null) {
    		txtResult.appendText("selezioanre un cibo");
    		return;
    	}
    	List<Vicino> vicini= model.getVicini(f);
    	if(vicini==null) {
    		txtResult.appendText("creare prima il grafo");
    		return;
    	}
    	txtResult.appendText("Analisi grassi...\n");
    	for(int i=0; i<5; i++) {
    		txtResult.appendText(vicini.get(i).getFood()+"  "+ vicini.get(i).getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Food f= boxFood.getValue();
    	if(f==null) {
    		txtResult.appendText("selezioanre un cibo");
    		return;
    	}
    	int porzioni;
    	try {
    		porzioni= Integer.parseInt(txtK.getText());
   
    	}catch(NumberFormatException e) {
    		txtResult.appendText("scrivere un numero di stazioni");
    		return;
    	}
    	txtResult.appendText("Simulazione...\n");
    	List<Food> prep=model.simula(porzioni, f);
    	for(Food d: prep) {
    		txtResult.appendText(d+"\n");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
