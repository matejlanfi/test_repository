package semestralkaa.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;//TODO
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import semestralkaa.Enum.eTypProhl;
import semestralkaa.Enum.enumKraj;
import semestralkaa.Enum.enumPozice;
import semestralkaa.kolekce.AbstrDoubleList;
import semestralkaa.model.Obec;
import semestralkaa.model.Obyvatele;

import semestralkaa.kolekce.AbstrTable;//TODO

/**
 * FXML Controller class
 *
 * @author matej
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ChoiceBox<String> ChoiseKraj;
    @FXML
    private CheckBox chkboxNadPrumer;
    @FXML
    private ChoiceBox<String> ChoiseFunc;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnLoadPrevios;
    @FXML
    private Button btnLoadNext;
    @FXML
    private Button btnLoadPrvni;
    @FXML
    private Button btnLoadPosledni;
    @FXML
    private Label lbObec;
    @FXML
    private Label lbPSC;
    @FXML
    private Label lbPcMuzu;
    @FXML
    private Label lbPcZen;
    @FXML
    private Label lbPcCelkem;
    @FXML
    private AnchorPane paneAdd;
    @FXML
    private CheckBox chkOwnObec;
    @FXML
    private Button btnGenerate;
    @FXML
    private AnchorPane PaneAddInside;
    @FXML
    private TextField txFieldObec;
    @FXML
    private TextField txFieldPSC;
    @FXML
    private TextField txFieldPcMuzu;
    @FXML
    private TextField txFieldPcZen;
    @FXML
    private Button btnAddObec;
    @FXML
    private ChoiceBox<String> ChAddPozice;
    @FXML
    private AnchorPane paneDelete;
    @FXML
    private Button btnDelete;
    @FXML
    private ChoiceBox<String> ChDelete;
    @FXML
    private Button btnDeleteKraj;
    /**
     * Initializes the controller class.
     */
    
    private boolean imported=false,checked=false,checkedObecAdd=false, checkVse = false, disAdd,disDel;
    private Obec obec;
    private AbstrDoubleList<Obec> obce;
    private final String[] funkce = {"Pridej","Odeber"};
    private final String[] mesta = {enumKraj.PRAHA.getNazev(),
        enumKraj.JIHOCESKY.getNazev(),
        enumKraj.JIHOMORAVSKY.getNazev(),
        enumKraj.KARLOVARSKY.getNazev(),
        enumKraj.VYSOCINA.getNazev(),
        enumKraj.KRALOVEHRADECKY.getNazev(),
        enumKraj.LIBERECKY.getNazev(),
        enumKraj.MORAVSKOSLEZKY.getNazev(),
        enumKraj.OLOMOUCKY.getNazev(),
        enumKraj.PARDUBICKY.getNazev(),
        enumKraj.PLZENSKY.getNazev(),
        enumKraj.STREDOCESKY.getNazev(),
        enumKraj.USTECKY.getNazev(),
        enumKraj.ZLINSKY.getNazev()};
    private final String[] pozice = {"Prvni","Predchudce","Aktualni","Naslednik","Posledni"};
    private final String[] nazvy = {"Kolacov","Uzenka","Jitrnicky","Bramburkov","Smetanka","Tresnicky","Jabkov","Vina Reva","Svickovice","Lekornice"};
    private Obyvatele obyv;
    @FXML
    private CheckBox chZobrazVse;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ChoiseKraj.getItems().addAll(mesta);
        ChoiseKraj.setOnAction(event -> {loadKraj();obec = setPosition("Prvni");loadData(obec);});
        ChoiseFunc.getItems().addAll(funkce);
        ChoiseFunc.setOnAction(this::loadFunc);
        
        paneAdd.setVisible(false);
        PaneAddInside.setVisible(false);
        ChAddPozice.getItems().addAll(pozice);
        ChAddPozice.getItems().remove("Aktualni");
        ChDelete.getItems().addAll(pozice);
        obyv = new Obyvatele();
    }    

    @FXML
    private void chkboxNadPrumerHandler(ActionEvent event) {
        checked=!checked;
        if(!checked)
        {paneAdd.setDisable(disAdd);paneDelete.setDisable(disDel);}
        else
        {paneAdd.setDisable(true);paneDelete.setDisable(true);}
        loadKraj();
        obec = obce.zpristupniPrvni();
        loadData(obec);
    }

    @FXML
    private void btnSaveHandle(ActionEvent event) throws IOException {
        if(!imported){Alert("Data not imported", "Nejsou importovaná data");}
        else{
        FileChooser flchoose = new FileChooser();
        FileChooser.ExtensionFilter extfilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        FileChooser.ExtensionFilter extfilter2 = new FileChooser.ExtensionFilter("Other files", "*.*");
        flchoose.getExtensionFilters().addAll(extfilter, extfilter2);
        Stage stage = (Stage) paneAdd.getScene().getWindow();
        File file = flchoose.showSaveDialog(stage);
        BufferedWriter write = new BufferedWriter(new FileWriter(file, true));
        for (int i = 0; i < 14; i++) {
            obce = obyv.zobrazObce(enumKraj.getEnum(mesta[i]));
            {
                boolean full;
                Obec first = null;
                if(obce.jePrazdny()){full=false;}else{full=true;first = obce.zpristupniPrvni();}
                while(full)
                {
                    write.write((i+1)+";"+mesta[i]+";"+obce.zpristupniAktualni().toString());
                    write.newLine();
                    obce.zpristupniNaslednika();
                    if(obce.zpristupniAktualni().equals(first))
                    {break;}
                } 
            }
        }
        write.close();}
    }

    @FXML
    private void btnImportHandler(ActionEvent event) {
        if(!imported){
        int pocet = obyv.importData("kraje.csv");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Import Complete");
        alert.setContentText(pocet + " Obcí načteno.");
        alert.showAndWait();
        imported=true;}
    }

    @FXML
    private void btnLoadPreviosHandler(ActionEvent event) {
        if(imported){
            enumKraj kraj = enumKraj.getEnum(ChoiseKraj.getValue());
            if(checkVse && obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(obyv.zpristupniObec(enumPozice.PRVNI, kraj)))
            {
                if(kraj != enumKraj.PRAHA){
                ChoiseKraj.getSelectionModel().selectPrevious();}
                else{ChoiseKraj.getSelectionModel().selectLast();}
                loadKraj();
                obec = setPosition("Posledni");
                loadData(obec);
            }
            else{
            Obec hl = obce.zpristupniPredchudce();
            while(!obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(hl))
            {
                obyv.zpristupniObec(enumPozice.PREDCHUDCE, kraj);
            }
        obec = obyv.zpristupniObec(enumPozice.AKTUALNI, kraj);
        loadData(obec);}
        }else{Alert("Chyba při načítaní Obce.", "Nebyl nalezeny žadné data");}
    }

    @FXML
    private void btnLoadNextHandler(ActionEvent event) {
        if(imported){
        enumKraj kraj = enumKraj.getEnum(ChoiseKraj.getValue());
        if(checkVse && obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(obyv.zpristupniObec(enumPozice.POSLEDNI, kraj)))
        {
            if(kraj != enumKraj.ZLINSKY){
            ChoiseKraj.getSelectionModel().selectNext();}
            else{ChoiseKraj.getSelectionModel().selectFirst();}
            loadKraj();
            obec = setPosition("Prvni");
            loadData(obec);
        }
        else{
        Obec hl = obce.zpristupniNaslednika();
            
            while(!obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(hl))
            {
                obyv.zpristupniObec(enumPozice.NASLEDNIK, kraj);
            }
        obec = obyv.zpristupniObec(enumPozice.AKTUALNI, kraj);
        loadData(obec);}
        }else{Alert("Chyba při načítaní Obce.", "Nebyl nalezeny žadné data");}
    }

    @FXML
    private void btnLoadPrvniHandler(ActionEvent event) {
        if(imported){
        Obec hl = obce.zpristupniPrvni();
            enumKraj kraj = enumKraj.getEnum(ChoiseKraj.getValue());
            obyv.zpristupniObec(enumPozice.PRVNI, kraj);
            while(!obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(hl))
            {
                obyv.zpristupniObec(enumPozice.NASLEDNIK, kraj);
            }
        obec = obyv.zpristupniObec(enumPozice.AKTUALNI, kraj);
        loadData(obec);
        }else{Alert("Chyba při načítaní Obce.", "Nebyl nalezeny žadné data");}
    }

    @FXML
    private void btnLoadPosledniHandler(ActionEvent event) {
        if(imported){
        Obec hl = obce.zpristupniPosledni();
            enumKraj kraj = enumKraj.getEnum(ChoiseKraj.getValue());
            obyv.zpristupniObec(enumPozice.POSLEDNI, kraj);
            while(!obyv.zpristupniObec(enumPozice.AKTUALNI, kraj).equals(hl))
            {
                obyv.zpristupniObec(enumPozice.PREDCHUDCE, kraj);
            }
        obec = obyv.zpristupniObec(enumPozice.AKTUALNI, kraj);
        loadData(obec);
        }else{Alert("Chyba při načítaní Obce.", "Nebyl nalezeny žadné data");}
    }
    
    private void loadKraj(){
        if(imported && !obyv.jePrazdny(enumKraj.getEnum(ChoiseKraj.getValue())))
        {
            if(checked && !checkVse)
            {
                obce = obyv.zobrazObceNadPrumer(enumKraj.getEnum(ChoiseKraj.getValue()));
            }
            else
            {
                obce = obyv.zobrazObce(enumKraj.getEnum(ChoiseKraj.getValue()));
                
                //TODO
                AbstrTable<String, Obec> table = new AbstrTable<>();
                Iterator<Obec> iterator = obce.iterator();
                while (iterator.hasNext()) {
                    Obec obek = iterator.next();
                    table.vloz(obek.getObec(), obek);
                    System.out.println(obek.toString());
                }
                System.out.println("----------------------------------------------------------");
                Iterator<Obec> iter = table.vytvorIterator(eTypProhl.HLOUBKA);
                while (iter.hasNext()) {
                    Obec obel = iter.next();
                    System.out.println(obel.toString());
                }
                System.out.println("---------------------------------------------------------");
                table.najdi(obce.zpristupniPosledni().toString());
            }
        }
        else
        {
            Alert("Chyba při načítaní kraje.", "Nebyl nalezeny žadné data");
        }
    }
    
    private void loadData(Obec obec)
    {
        lbObec.setText("Obec: " + obec.getObec());
        lbPSC.setText("PSC: " + obec.getCisloobce());
        lbPcMuzu.setText("Počet mužů: " + obec.getPocetmuzu());
        lbPcZen.setText("Počet žen: " + obec.getPocetzen());
        lbPcCelkem.setText("Celkový počet: " + obec.getCelkovypocet());
    }
    
    private void loadFunc(ActionEvent event){
        switch(ChoiseFunc.getValue()){
            case("Pridej"):
                disAdd = false;
                disDel = true;
                paneAdd.setVisible(!disAdd);
                paneAdd.setDisable(disAdd);
                paneDelete.setDisable(disDel);
                paneDelete.setVisible(!disDel);
                break;
                
            case("Odeber"):
                disDel = false;
                disAdd = true;
                paneAdd.setDisable(disAdd);
                paneAdd.setVisible(!disAdd);
                paneDelete.setVisible(!disDel);
                paneDelete.setDisable(disDel);
                break;
        }
    }
    
    private void Alert(String Header,String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    @FXML
    private void chkOwnObecHandelr(ActionEvent event) {
        checkedObecAdd=!checkedObecAdd;
        if(checkedObecAdd){
            PaneAddInside.setVisible(true);
            PaneAddInside.setDisable(false);
            btnGenerate.setDisable(true);
            btnGenerate.setVisible(false);
        }
        else
        {
            btnGenerate.setVisible(true);
            PaneAddInside.setDisable(true);
            btnGenerate.setDisable(false);
            PaneAddInside.setVisible(false);
        }
        
    }

    @FXML
    private void btnGenerateHandler(ActionEvent event) {
        Random rand = new Random();
        int nameIndex = rand.nextInt(nazvy.length);
        Obec ob = new Obec(rand.nextInt(5000)+1000, nazvy[nameIndex], rand.nextInt(500)+1, rand.nextInt(500)+1);
        int posIndex;
        do
        {
            posIndex = rand.nextInt(pozice.length);
        }while(pozice[posIndex].equals("Aktualni"));
        obyv.vlozObec(ob, getEnumPoz(pozice[posIndex]), enumKraj.getEnum(ChoiseKraj.getValue()));
        loadKraj();
        obec = setPosition("Aktualni");
        loadData(obec);
    }

    @FXML
    private void btnAddObec(ActionEvent event) {
        obyv.vlozObec(new Obec(isNum(txFieldPSC.getText()),txFieldObec.getText(),isNum(txFieldPcMuzu.getText()),isNum(txFieldPcZen.getText())), 
                getEnumPoz(ChAddPozice.getValue()), enumKraj.getEnum(ChoiseKraj.getValue()));
        loadKraj();
        obec = setPosition("Aktualni");
        loadData(obec);
    }
    
    private enumPozice getEnumPoz(String poz){
        switch (poz) {
            case "Prvni":
                return enumPozice.PRVNI;
                
            case "Predchudce":
                return enumPozice.PREDCHUDCE;
                
            case "Aktualni":
                return enumPozice.AKTUALNI;
                
            case "Naslednik":
                return enumPozice.NASLEDNIK;
                
            case "Posledni":
                return enumPozice.POSLEDNI;
                
            default:
                Alert("Spatná pozice", "Pozice nebyla nalezena");
                return null;
        }
    }

    private int isNum(String text){
        try{
            int i = Integer.parseInt(text);
            return i ;
        } catch (NumberFormatException e){
            Alert("Špatný formát", "Zadaný text není celé číslo");
            return 0;
        }
    }

    @FXML
    private void btnDeleteHandler(ActionEvent event) {
       if(!obyv.jePrazdny(enumKraj.getEnum(ChoiseKraj.getValue())))
        { 
            obyv.odeberObec(getEnumPoz(ChDelete.getValue()), enumKraj.getEnum(ChoiseKraj.getValue()));
            loadKraj();
            if(ChoiseKraj.getValue().equals("Aktualni"))
            {obec = setPosition("Prvni");}
            else{obec = setPosition("Aktualni");}
            loadData(obec);
        }else{Alert("Kraj je prazdný", "Kraj je prazdný, není co mazat");}
    }

    @FXML
    private void btnDeleteKrajHandler(ActionEvent event) {
        if(!obyv.jePrazdny(enumKraj.getEnum(ChoiseKraj.getValue())))
        {
            obyv.zrus(enumKraj.getEnum(ChoiseKraj.getValue()));
        }else{Alert("Kraj je prazdný", "Kraj je prazdný, není co mazat");}
    }
    
    private Obec setPosition(String poz){
        if(obyv.jePrazdny(enumKraj.getEnum(ChoiseKraj.getValue())))
        {   Obec ob = new Obec(0, "Nejsou Data", 0, 0);
            return ob;}
        
        if(poz == "Prvni")
        {
            obyv.zpristupniObec(enumPozice.PRVNI, enumKraj.getEnum(ChoiseKraj.getValue()));
            obce.zpristupniPrvni();
        }
        else if (poz == "Aktualni")
        {
            Obec hl = obyv.zpristupniObec(enumPozice.AKTUALNI, enumKraj.getEnum(ChoiseKraj.getValue()));
            while(!obce.zpristupniAktualni().equals(hl))
            {
                obce.zpristupniNaslednika();
            }
        }
        else if (poz == "Posledni")
        {
            obyv.zpristupniObec(enumPozice.POSLEDNI, enumKraj.getEnum(ChoiseKraj.getValue()));
            obce.zpristupniPosledni();
        }
        return obyv.zpristupniObec(enumPozice.AKTUALNI, enumKraj.getEnum(ChoiseKraj.getValue()));
    }

    @FXML
    private void chZobrazVseHandler(ActionEvent event) {
        checkVse = !checkVse;
        if(checkVse)
        {
            chkboxNadPrumer.setDisable(true);
            paneAdd.setDisable(true);
            paneDelete.setDisable(true);
            ChoiseFunc.setDisable(true);
        }
        else
        {
            chkboxNadPrumer.setDisable(false);
            paneAdd.setDisable(disAdd);
            paneDelete.setDisable(disDel);
            ChoiseFunc.setDisable(false);
        }
    }
}