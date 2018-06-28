package vista;

import clases.Actividad;
import java.net.URL;
import utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

public class ProjectController implements Initializable  {
    private ObservableList<Actividad> actividades;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label outputCodigo;

    @FXML
    private Label outputNombres;

    @FXML
    private Label outputApellidos;

    @FXML
    private Label outputAnioIngreso;

    @FXML
    private Label outputPromedio;

    @FXML
    private TableView<Actividad> tableActividades;

    @FXML
    private TableColumn<Actividad, Integer> columnN;

    @FXML
    private TableColumn<Actividad, String> columnNombre;

    @FXML
    private TableColumn<Actividad, Double> columnDuracion;

    @FXML
    private TableColumn<Actividad, List<Actividad>> columnPredecesores;

    @FXML
    private TableColumn<Actividad, ComboBox> columnAgregar;

    @FXML
    private TableColumn<Actividad, Button> columnEliminar;
    
    @FXML
    private Button btnAgregar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //public void initialize(){
        actividades = FXCollections.observableArrayList(new LinkedList<>());
        tableActividades.setItems(actividades);
        iniciarTablaActividades();
    }
    
    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 0*/
    @FXML
    void mostrarPaginaPersonal(ActionEvent event) {

        System.out.println("Pestaña Pagina Personal Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);

    }
    
    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 1*/
    @FXML
    void mostrarHistorialAcademico(ActionEvent event) {
        System.out.println("Pestaña Historial Academico Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(1);

    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 2*/
    @FXML
    void mostrarTablaResultado(ActionEvent event) {

        System.out.println("Pestaña Pagina Personal Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(2);

    }
    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 3*/
    @FXML
    void mostrarDiagramaGantt(ActionEvent event) {

        System.out.println("Pestaña Pagina Personal Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(3);

    }

    /*EVENTO DEL BOTON PARA CERRAR APLICACION*/
    @FXML
    void salir(ActionEvent event) {
        System.out.println("Cerra Sesion");
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    /*EVENTO DEL BOTON PARA AGREGAR UNA ACTIVIDAD*/
    @FXML
    void actionAgregarActividad(ActionEvent event) {
        System.out.println("AGREGANDO ACTIVIDAD");
        Actividad nuevaActividad = new Actividad();
        actividades.add(nuevaActividad);
        nuevaActividad.setCombo(obtenerCombo());
        nuevaActividad.setBotonEliminar(obtenerBtnEliminar());
    }
    
    /*Evento cuando terminas de editar el nombre de una actividad*/
    @FXML
    void finEdicionNombre(CellEditEvent<Actividad, String> event) {
        System.out.println("VIEJA LISTA");
        actividades.forEach(System.out::println);
        event.getRowValue().setNombreDeTarea(event.getNewValue());
        System.out.println("NUEVA LISTA");
        actividades.forEach(System.out::println);
    }
    
    /*Evento cuando terminas de editar la duracion de una actividad*/
    @FXML
    void finEdicionDuracion(CellEditEvent<Actividad, Double> event) {
        System.out.println("VIEJA LISTA");
        actividades.forEach(System.out::println);
        event.getRowValue().setDuracion(event.getNewValue());
        System.out.println("NUEVA LISTA");
        actividades.forEach(System.out::println);
    }
    
    
    private void cargarInformacionAlumno(){

    }

    private void iniciarTablaActividades() {
        columnN.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNumeroDeActividad()));
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreDeTarea()));
        columnNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        
        columnDuracion.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDuracion()));
        
        columnDuracion.setCellFactory(TextFieldTableCell.forTableColumn(
                new StringConverter<Double>() {
            @Override
            public String toString(Double o) {
                return o.toString();
            }

            @Override
            public Double fromString(String string) {
                return Double.valueOf(string);
            }
        }
        ));
        
        columnPredecesores.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getPredecesoras()));
        columnPredecesores.setCellFactory(TextFieldTableCell.forTableColumn(
                new StringConverter< List<Actividad> >() {
            @Override
            public String toString(List<Actividad> lista) {
                if(lista==null) return "";
                
                List<Integer> listaNumeros = new LinkedList<>();
                lista.forEach(act -> listaNumeros.add(act.getNumeroDeActividad()));
                return listaNumeros.toString();
            }

            @Override
            public List<Actividad> fromString(String string) {
                //CREO Q NO ES NECESARIO ESTA CONVERSION
                return null;
            }
        }
        ));
        
        columnAgregar.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCombo()));
        columnEliminar.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBotonEliminar()));
    }
    
    private ComboBox<Actividad> obtenerCombo(){
        ComboBox<Actividad> combo = new ComboBox<>(actividades);
        
        //Para cambiar lo que va a mostrar el comboBox i.e.
        //solo el numeroDeActividad enves de toda la actividad
        combo.setConverter(new StringConverter<Actividad>() {
            @Override
            public String toString(Actividad o) {
                return "Actividad " + o.getNumeroDeActividad();
            }

            @Override
            public Actividad fromString(String numeroDeActividad) {
                return null;
            }
        });
        
        combo.setOnAction((evt)->{
                Actividad seleccionado = combo.getValue();
                //Si no esta nada seleccionado en el combo box interrumpe la ejecucion del evento
                if(seleccionado==null) return;
                
                //BUSCANDO LA ACTIVIDAD A LA QUE PERTENCE ESTE COMBO BOX
                Actividad estaActividad = actividades.stream()
                        .filter((act)->act.getCombo() == combo).findFirst().get();
                
                if(estaActividad == seleccionado){
                    System.out.println("ESTAS SELECCIONANDO LA MISMA ACTIVIDAD");
                }else if(estaActividad.getPredecesoras().contains(seleccionado)){
                    System.out.println("YA CONTENIA ESTA PREDECESORA");
                    estaActividad.eliminarPredecesora(seleccionado);
                }else{
                    System.out.println("ANIADIENDO UNA PREDECESORA");
                    estaActividad.agregarPredecesora(seleccionado);
                }
                combo.getSelectionModel().clearSelection();
               
                tableActividades.refresh();
        });
        return combo;
    }
    
    private Button obtenerBtnEliminar(){
        Image imageMenos = new Image(getClass().getResourceAsStream("/images/iconoMenos.png"),50,55,true,true);
        
        Button botonEliminar = new Button("",new ImageView(imageMenos));
        //ESTABLECIENDO EL EVENTO CUANDO SE HAGA CLICK
        botonEliminar.setOnAction((evt)->{
            Actividad estaActividad = actividades.stream()
                    .filter((act)->act.getBotonEliminar().equals(botonEliminar))
                    .findFirst().get();
            
            //estaActividad = actividades.filtered((r)->r.getBotonEliminar()==botonEliminar).get(0);
            
            System.out.println("Eliminando: "+estaActividad.getNumeroDeActividad());
            actividades.forEach((act)->{
                act.eliminarPredecesora(estaActividad);
                if(act.getNumeroDeActividad()>estaActividad.getNumeroDeActividad()){
                    act.setNumeroDeActividad(act.getNumeroDeActividad()-1);
                }
            });
            
            //QUIZA HAY QUE MEJORAR
            Actividad.reducirIndice();
            actividades.remove(estaActividad);
            tableActividades.refresh();
        
        });
        botonEliminar.getStyleClass().add("btnCentrado");
        return botonEliminar;
    }
}
