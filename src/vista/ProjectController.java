package vista;

import clases.Actividad;
import clases.ActividadProgramada;
import clases.CMP;
import clases.GestorArchivos;
import java.io.File;
import java.net.URL;
import utils.Utils;
import utils.Painter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import utils.Utiles;

public class ProjectController implements Initializable  {
    public static File archivoActual = null;
    private boolean estaGuardado = true;
    
    private ObservableList<Actividad> actividades;
    private ObservableList<ActividadProgramada> actividadesProgramadas;

    @FXML
    private TabPane tabPane;

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

    @FXML
    private TableView<ActividadProgramada> tablaResultado;
    
    @FXML
    private TableColumn<ActividadProgramada, String> columnNombreTarea;
    @FXML
    private TableColumn<ActividadProgramada, Double> columnIniTemp;
    @FXML
    private TableColumn<ActividadProgramada, Double> columnIniTard;
    @FXML
    private TableColumn<ActividadProgramada, Double> columnTermTemp;
    @FXML
    private TableColumn<ActividadProgramada, Double> columnTermTard;
    @FXML
    private TableColumn<ActividadProgramada, Double> columnHolgura;
    @FXML
    private TableColumn<ActividadProgramada, String> columnRutaCrit;
    
    @FXML
    private AnchorPane panelDibujo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //public void initialize(){
        actividades = FXCollections.observableArrayList(new LinkedList<>());
        //aniadirPruebita();
        iniciarTablaActividades();
    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 0*/
    @FXML
    void mostrarHistorialAcademico(ActionEvent event) {
        System.out.println("Pestaña Ingreso de actividades Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);

    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 1*/
    @FXML
    void mostrarTablaResultado(ActionEvent event) {

        System.out.println("Pestaña Tabla resultado Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        actividadesProgramadas = FXCollections.observableList(CMP.programarProyecto(actividades));
        iniciarTablaResultado();
        selectionModel.select(1);

    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 2*/
    @FXML
    void mostrarDiagramaGantt(ActionEvent event) {

        System.out.println("Pestaña Diagrama de Gantt Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        actividadesProgramadas = FXCollections.observableList(CMP.programarProyecto(actividades));
        selectionModel.select(2);
        dibujarGantt();
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
        //    System.out.println("AGREGANDO ACTIVIDAD");
        Actividad nuevaActividad = new Actividad();
        actividades.add(nuevaActividad);
        nuevaActividad.setCombo(obtenerCombo());
        nuevaActividad.setBotonEliminar(obtenerBtnEliminar());
    }

    /*Evento cuando terminas de editar el nombre de una actividad*/
    @FXML
    void finEdicionNombre(CellEditEvent<Actividad, String> event) {
        //    System.out.println("VIEJA LISTA");
        //    actividades.forEach(System.out::println);
        event.getRowValue().setNombreDeTarea(event.getNewValue());
        //    System.out.println("NUEVA LISTA");
        //    actividades.forEach(System.out::println);
    }

    /*Evento cuando terminas de editar la duracion de una actividad*/
    @FXML
    void finEdicionDuracion(CellEditEvent<Actividad, Double> event) {
        //    System.out.println("VIEJA LISTA");
        //    actividades.forEach(System.out::println);
        event.getRowValue().setDuracion(event.getNewValue());
        //    System.out.println("NUEVA LISTA");
        //    actividades.forEach(System.out::println);
    }

    private void iniciarTablaActividades() {
        tableActividades.setItems(actividades);
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
                new StringConverter< List<Actividad>>() {
            @Override
            public String toString(List<Actividad> lista) {
                if (lista == null) {
                    return "";
                }

                List<Integer> listaNumeros = new LinkedList<>();
                try{
                    lista.forEach(act -> listaNumeros.add(act.getNumeroDeActividad()));
                }catch (Exception e){
                    System.out.println("ALGO OCURRE");
                }
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
    
    private void iniciarTablaResultado(){
        tablaResultado.setItems(actividadesProgramadas);
        columnNombreTarea.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreActividad()));
        columnIniTemp.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getInicioMasTemprano()));
        columnIniTard.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getInicioMasTardio()));
        columnTermTemp.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getTerminacionMasTemprana()));
        columnTermTard.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getTerminacionMasTardia()));
        columnHolgura.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getHolgura()));
        columnRutaCrit.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().isRutaCritica() ? "SI" : "NO"));
        
    }

    private ComboBox<Actividad> obtenerCombo() {
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

    private Button obtenerBtnEliminar() {
        Image imageMenos = new Image(getClass().getResourceAsStream("/images/iconoMenos.png"), 50, 55, true, true);

        Button botonEliminar = new Button("", new ImageView(imageMenos));
        //ESTABLECIENDO EL EVENTO CUANDO SE HAGA CLICK
        botonEliminar.setOnAction((evt) -> {
            Actividad estaActividad = actividades.stream()
                    .filter((act) -> act.getBotonEliminar().equals(botonEliminar))
                    .findFirst().get();

            //estaActividad = actividades.filtered((r)->r.getBotonEliminar()==botonEliminar).get(0);
            System.out.println("Eliminando: " + estaActividad.getNumeroDeActividad());
            actividades.forEach((act) -> {
                act.eliminarPredecesora(estaActividad);
                if (act.getNumeroDeActividad() > estaActividad.getNumeroDeActividad()) {
                    act.setNumeroDeActividad(act.getNumeroDeActividad() - 1);
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
    
    private void dibujarGantt(){
        Painter painter = new Painter(panelDibujo);
        
        //LIMPIANDO EL PANEL
        panelDibujo.getChildren().clear();
        
        String estilo = "-fx-background-color: palegreen; " +
                    "-fx-background-insets: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);";
                
        //Margenes en porcentaje 
        double porcMargenIzqDerDibujo = 0.1;
        double porcMargenIzqTexto = 0.03;
        double porcentajeMargenArrAba = 0.06;
        
        double porcentajeAnchoBarra = 0.30;
        
        double anchoBarra;
        double espacioEntreBarras;
        int cantidadLineasEntrecortadas = 15;
        double maximaTerminacion;
        
        double escalaDibujo;
        double xMargenDibujo;
        double xMargenTexto;
        double yMargen;
        
        //Lista de colores para pintar las barras
        Color colorCritico = Color.ORANGE;
        Color colorNoCritico = Color.RED;
        Color colorHolgura = Color.BLUE;

        //Calculando la maxima terminacion
        maximaTerminacion = actividadesProgramadas.stream().max(
                (a1,a2)->Double.compare(
                        a1.getTerminacionMasTardia(), a2.getTerminacionMasTardia()
                )).get().getTerminacionMasTardia();
        
        
        //escala del dilbujo
        escalaDibujo = (panelDibujo.getPrefWidth()
                *(1-(porcMargenIzqDerDibujo+porcMargenIzqTexto)))/maximaTerminacion;
        
        //Ubicacion en el eje x
        xMargenDibujo = panelDibujo.getPrefWidth()*porcMargenIzqDerDibujo;
        xMargenTexto = panelDibujo.getPrefWidth()*porcMargenIzqTexto;
        
        //Ubicacion en el eje y
        yMargen = panelDibujo.getPrefHeight()*porcentajeMargenArrAba;
        
        //Calculando el ancho de cada barra de acuerdo a la cantidad de
        //actividades que existan
        anchoBarra = ((panelDibujo.getPrefHeight()-yMargen)/actividadesProgramadas.size())*porcentajeAnchoBarra;
        //Estableciendo un minimo de 10 de ancho para la barra
        anchoBarra = anchoBarra > 10 ? anchoBarra : 10;
        
        //Calculando el espacio entre barra y barra
        espacioEntreBarras = ((panelDibujo.getPrefHeight()-yMargen)/actividadesProgramadas.size())*(1-porcentajeAnchoBarra);
        //Estableciendo un minimo de 15 para el espacioEntreBarras
        System.out.println("ESPACIO ENTRE BARRAS: "+espacioEntreBarras);
        espacioEntreBarras = espacioEntreBarras > 15 ? espacioEntreBarras : 15;
        
        double y = yMargen;
        double x;
        System.out.println("ESPACIO ENTRE BARRAS: "+espacioEntreBarras);
        System.out.println("ANCHO BARRA: "+anchoBarra);
        for(int i=0;i<actividadesProgramadas.size();i++){
            x = xMargenDibujo + actividadesProgramadas.get(i).getInicioMasTemprano()*escalaDibujo;
            
            double duracion = actividadesProgramadas.get(i).getTerminacionMasTemprana()-
                    actividadesProgramadas.get(i).getInicioMasTemprano();
                    
            //ESCRIBIENDO NOMBRE DE ACTIVIDAD
            painter.aniadirLabel(actividadesProgramadas.get(i).getNombreActividad()
                    , xMargenTexto, y+5);
            
            //EL TEXTO QUE IRA EN EL TOOLTIP
            String textoTooltip = "Actividad: "+actividadesProgramadas.get(i).getNombreActividad()
                    +"\nInicio: "+Utiles.redondear(actividadesProgramadas.get(i).getInicioMasTemprano())
                    +"\nFin: "+Utiles.redondear(actividadesProgramadas.get(i).getTerminacionMasTemprana())
                    +"\nHolgura: "+Utiles.redondear(actividadesProgramadas.get(i).getHolgura());
            
            //DIBUJANDO EL RECTANGULO DE DURACION
            Color color = actividadesProgramadas.get(i).isRutaCritica() ? 
                    colorCritico : colorNoCritico;
            painter.dibujarRectangulo(x, y, duracion*escalaDibujo
                    , anchoBarra, color, estilo,textoTooltip);
            
            //DIBUJANDO EL RECTANGULO DE HOLGURA
            x += duracion*escalaDibujo;
            painter.dibujarRectangulo(x, y
                    ,actividadesProgramadas.get(i).getHolgura()*escalaDibujo
                    ,anchoBarra,colorHolgura, estilo,textoTooltip);
            
            //EL SALTO PARA DIBUJAR LA SIGUIENTE BARRA
            y += espacioEntreBarras;
        }
        //DIBUJANDO LA LINEA VERTICAL DE LOS EJES
        x = xMargenDibujo;
        painter.dibujarLinea(x, y, x, yMargen, false);
        
        //DIBUJANDO LA LINEA HORIZONTAL DE LOS EJES
        painter.dibujarLinea(x, y, maximaTerminacion*escalaDibujo+x, y, false);
        
        //Dibujando las lineas entrecortadas
        double valorAct = 0;
        for(int i=1;i<=cantidadLineasEntrecortadas;i++){
            valorAct += (maximaTerminacion/cantidadLineasEntrecortadas);
            x += (maximaTerminacion/cantidadLineasEntrecortadas)*escalaDibujo;
            painter.dibujarLinea(x, y+anchoBarra, x, yMargen, true);
            painter.aniadirLabel(Utiles.redondear(valorAct)+"", x-8, y+10);
        }
        
        //ESSCRIBIENDO LA LEYENDA
        y = y + 45;
        x = xMargenDibujo;
        painter.dibujarRectangulo(x, y, 10, 10, colorCritico, estilo);
        x += 20;
        painter.aniadirLabel("Actividad critica", x, y-5);
        
        x += 300;
        painter.dibujarRectangulo(x, y, 10, 10, colorNoCritico, estilo);
        x += 20;
        painter.aniadirLabel("Actividad no critica", x, y-5);
        
        x += 300;
        painter.dibujarRectangulo(x, y, 10, 10, colorHolgura, estilo);
        x += 20;
        painter.aniadirLabel("Holgura", x, y-5);
    }

    private void aniadirPruebita() {
        Actividad act0 = new Actividad(0, "A", 5.0);
        Actividad act1 = new Actividad(1, "B", 6.0);
        Actividad act2 = new Actividad(2, "C", 4.0);
        Actividad act3 = new Actividad(3, "D", 3.0);
        Actividad act4 = new Actividad(4, "E", 1.0);
        Actividad act5 = new Actividad(5, "F", 4.0);
        Actividad act6 = new Actividad(6, "G", 14.0);
        Actividad act7 = new Actividad(7, "H", 12.0);
        Actividad act8 = new Actividad(8, "I", 2.0);

        act0.getPredecesoras().add(act2);
        act0.getPredecesoras().add(act3);
        act0.getPredecesoras().add(act4);
        act1.getPredecesoras().add(act7);
        act2.getPredecesoras().add(act7);
        act3.getPredecesoras().add(act6);
        act4.getPredecesoras().add(act5);
        act5.getPredecesoras().add(act6);
        act6.getPredecesoras().add(act8);
        act7.getPredecesoras().add(act8);
        actividades.add(act0);
        actividades.add(act1);
        actividades.add(act2);
        actividades.add(act3);
        actividades.add(act4);
        actividades.add(act5);
        actividades.add(act6);
        actividades.add(act7);
        actividades.add(act8);
    }
    
    @FXML
    void actionNuevo(ActionEvent event) {

        if (!estaGuardado) {
            int resp = JOptionPane.showConfirmDialog(null,
                    "El arhivo actual no se ha guardado\n ¿Desea abrir uno nuevo?",
                    "Advertencia",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (resp == JOptionPane.YES_OPTION) {
                actividades.clear();
                Actividad.resetearIndice();
                estaGuardado = true;
                archivoActual = null;
            }
        } else {
            actividades.clear();
            Actividad.resetearIndice();
            archivoActual = null;
            tableActividades.refresh();
        }
    }

    @FXML
    void actionAbrir(ActionEvent event) {
        List<Actividad> lst = GestorArchivos.abrir();
//        System.out.println("IMPrIMIENDO LISTA ABIERTA");
//        lst.forEach(System.out::println);

        if (lst != null) {
            actividades.clear();
            Actividad.resetearIndice();
            lst.forEach(x -> {
                x.setCombo(obtenerCombo());
                x.setBotonEliminar(obtenerBtnEliminar());
                actividades.add(x);
            });
            tableActividades.refresh();
        }

    }

    @FXML
    void actionGuardar(ActionEvent event) {
        if (archivoActual != null) {
            GestorArchivos.guardar(actividades, archivoActual);
        } else {
            GestorArchivos.guardarComo(actividades);
        }
    }

    @FXML
    void actionGuardarComo(ActionEvent event) {
        GestorArchivos.guardarComo(actividades);
    }
}
