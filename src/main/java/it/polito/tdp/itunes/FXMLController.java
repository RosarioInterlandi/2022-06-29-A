/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.AdiacenzeBilanciate;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	if (cmbA1.getValue()== null) {
    		txtResult.setText("Scegli il nodo");
    		return;
    	}
    	List<AdiacenzeBilanciate> listaAdiacenti= this.model.calcolaAdiacenze(this.cmbA1.getValue());
    	for (AdiacenzeBilanciate ab: listaAdiacenti) {
    		txtResult.appendText(ab.getA().getTitle()+"bilancio"+ab.getBilancio()+"\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	int pesoMinimo = 0;
    	try {
    		pesoMinimo = Integer.parseInt(txtX.getText());
    	}catch(NumberFormatException n) {
    		txtResult.setText("Inserisci un valore minimo");
    		return;
    	}
    	Album target = this.cmbA2.getValue();
    	if (target == null) {
    		txtResult.setText("Inserisci il target");
    		return;
    	}
    	List<Album> result =this.model.getPath(pesoMinimo, target);
    	if(result.size()==0) {
    		txtResult.setText("Non esiste cammino che unisce i due vertici");
    		return;
    	}
    	//Se sono qui tutti i valori sono validi
    	txtResult.clear();
    	txtResult.setText("Il cammino migliore è :\n");
    	for (Album a: result) {
    		txtResult.appendText(a.getTitle()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String n = this.txtN.getText();
    	if (n!= null) {
    		this.model.BuildGraph(Integer.parseInt(this.txtN.getText()));
    		
    		this.cmbA1.getItems().clear();
    		this.cmbA2.getItems().clear();
    		
        	this.cmbA1.getItems().addAll(this.model.getAllAlbum());
        	this.cmbA2.getItems().addAll(this.model.getAllAlbum());
    	}else {
    		txtResult.setText("Inserire il numero di traccie minimo");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;

    }
}
