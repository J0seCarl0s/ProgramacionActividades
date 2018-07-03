package vista;

import clases.Actividad;
import clases.ActividadProgramada;
import clases.CMP;
import clases.GestorArchivos;
import java.io.File;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;

public class ProjectController implements Initializable {

    public static File archivoActual = null;
    private boolean estaGuardado = true;

    private ObservableList<Actividad> actividades;

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
    private AnchorPane panelDibujo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //public void initialize(){
        actividades = FXCollections.observableArrayList(new LinkedList<>());

        aniadirPruebita();

        tableActividades.setItems(actividades);
        iniciarTablaActividades();
    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 0*/
    @FXML
    void mostrarHistorialAcademico(ActionEvent event) {
        System.out.println("Pestaña Historial Academico Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);

    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 1*/
    @FXML
    void mostrarTablaResultado(ActionEvent event) {

        System.out.println("Pestaña Pagina Personal Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(1);

    }

    /*EVENTO DEL BOTON PARA CAMBIAR A PESTAÑA 2*/
    @FXML
    void mostrarDiagramaGantt(ActionEvent event) {

        System.out.println("Pestaña Pagina Personal Seleccionada");
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
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

        combo.setOnAction((evt) -> {
            Actividad seleccionado = combo.getValue();
            //Si no esta nada seleccionado en el combo box interrumpe la ejecucion del evento
            if (seleccionado == null) {
                return;
            }

            //BUSCANDO LA ACTIVIDAD A LA QUE PERTENCE ESTE COMBO BOX
            Actividad estaActividad = actividades.stream()
                    .filter((act) -> act.getCombo() == combo).findFirst().get();

            if (estaActividad == seleccionado) {
                System.out.println("ESTAS SELECCIONANDO LA MISMA ACTIVIDAD");
            } else if (estaActividad.getPredecesoras().contains(seleccionado)) {
                System.out.println("YA CONTENIA ESTA PREDECESORA");
                estaActividad.eliminarPredecesora(seleccionado);
            } else {
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

    private void dibujarGantt() {
        if (panelDibujo == null) {
            System.out.println("ES NULO XD");
        }

        List<ActividadProgramada> listActProg
                = CMP.programarProyecto(actividades);

        String estilo = "-fx-background-color: palegreen; "
                + "-fx-background-insets: 10; "
                + "-fx-background-radius: 10; "
                + "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);";

        double maximaTerminacion = 0;
        double porcMargenIzqDerDibujo = 0.1;
        double porcMargenIzqTexto = 0.05;
        double porcentajeMargenArrAba = 0.1;
        double anchoBarra = 10;
        double espacioEntreBarras = 25;
        int precisionMedida = 10;

        //Calculando la maxima terminacion
        maximaTerminacion = listActProg.stream().max(
                (a1, a2) -> Double.compare(
                        a1.getTerminacionMasTardia(), a2.getTerminacionMasTardia()
                )).get().getTerminacionMasTardia();

        //espacio entre panel y dibujos la quinta parte de la anchura del panel por cada lado
        double escalaDibujo = (panelDibujo.getPrefWidth() * (1 - 2 * porcMargenIzqDerDibujo)) / maximaTerminacion;

        //Ubicacion en el eje x
        double xMargenDibujo = panelDibujo.getPrefWidth() * porcMargenIzqDerDibujo;
        double xMargenTexto = panelDibujo.getPrefWidth() * porcMargenIzqTexto;

        //Ubicacion en el eje y
        double yMargen = panelDibujo.getPrefHeight() * porcentajeMargenArrAba;

        //Lista de colores para pintar las barras
        Color colorCritico = Color.ORANGE;
        Color colorNoCritico = Color.RED;
        Color colorHolgura = Color.BLUE;

        double y = yMargen;
        double x = xMargenDibujo;

        for (int i = 0; i < listActProg.size(); i++) {
            x = xMargenDibujo + listActProg.get(i).getInicioMasTemprano() * escalaDibujo;

            double duracion = listActProg.get(i).getTerminacionMasTemprana()
                    - listActProg.get(i).getInicioMasTemprano();

            System.out.println(i + " DURACION: " + duracion);
            //ESCRIBIENDO NOMBRE DE ACTIVIDAD
            Label labelNombreAct = new Label(listActProg.get(i).getNombreActividad());
            labelNombreAct.setVisible(true);
            labelNombreAct.setLayoutX(xMargenTexto);
            labelNombreAct.setLayoutY(y);
            panelDibujo.getChildren().add(labelNombreAct);

            //DIBUJANDO EL RECTANGULO DE DURACION
            Rectangle rectangulo = new Rectangle(x, y, duracion * escalaDibujo, anchoBarra);
            Color color = listActProg.get(i).isRutaCritica()
                    ? colorCritico : colorNoCritico;

            //Seteo el color del rectangulo
            rectangulo.setFill(color);
            //Bordes negros
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setStyle(estilo);
            panelDibujo.getChildren().add(rectangulo);

            //DIBUJANDO EL RECTANGULO DE HOLGURA
            x += duracion * escalaDibujo;
            System.out.println("LA HOLGURA ES: " + listActProg.get(i).getHolgura());
            Rectangle rectHolgura = new Rectangle(x, y,
                    listActProg.get(i).getHolgura() * escalaDibujo, anchoBarra);

            //Seteo el color del rectangulo
            rectHolgura.setFill(colorHolgura);
            //Bordes negros
            rectHolgura.setStroke(Color.BLACK);
            rectHolgura.setStyle(estilo);
            panelDibujo.getChildren().add(rectHolgura);

            //EL SALTO PARA DIBUJAR LA SIGUIENTE BARRA
            y += espacioEntreBarras;
        }
        x = xMargenDibujo;
        Line lineaVertical = new Line(x, y, x, yMargen);
        lineaVertical.setStroke(Color.BLACK);
        //lineaVertical.getStrokeDashArray().addAll(20d,40d);
        panelDibujo.getChildren().add(lineaVertical);

        Line lineaHorizontal = new Line(x, y, maximaTerminacion * escalaDibujo + x, y);
        lineaHorizontal.setStroke(Color.BLACK);
        //lineaVertical.getStrokeDashArray().addAll(20d,40d);
        panelDibujo.getChildren().add(lineaHorizontal);

        double valorAct = 0;
        for (int i = 1; i <= precisionMedida; i++) {
            valorAct += Math.round((maximaTerminacion / precisionMedida) * 100) / 100;
            x += (maximaTerminacion * escalaDibujo) / precisionMedida;
            Line entreCortada = new Line(x, y, x, yMargen);
            entreCortada.setStroke(Color.BLACK);
            entreCortada.getStrokeDashArray().addAll(20d, 40d);
            panelDibujo.getChildren().add(entreCortada);

            Label labelEjex = new Label(valorAct + "");
            labelEjex.setVisible(true);
            labelEjex.setLayoutX(x);
            labelEjex.setLayoutY(y + 20);
            panelDibujo.getChildren().add(labelEjex);

        }
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
                Actividad.resetearInidice();
                estaGuardado = true;
                archivoActual = null;
            }
        } else {
            actividades.clear();
            Actividad.resetearInidice();
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
            Actividad.resetearInidice();
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
