package javaFx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Config;
import model.Parameters;
import service.ConfigService;
import service.DubboTestService;
import tools.ExceptionTools;
import tools.PropertyTools;
import tools.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    /**table data */
    private ObservableList<Parameters> personData = FXCollections.observableArrayList();

    @FXML
    JFXButton zkTest;

    @FXML
    JFXButton goTest;

    @FXML
    JFXTextField zk_input;

    @FXML
    JFXTextField interface_input;

    @FXML
    JFXTextField method_input;

    @FXML
    JFXTextField group_input;

    @FXML
    JFXTextField version_input;

    @FXML
    TableView param_table;

    @FXML
    JFXTextArea response_data;

    @FXML
    TableColumn<Parameters, String> paramType;

    @FXML
    TableColumn<Parameters, String> paramValue;

    @FXML
    Pane root_pane;

    @FXML
    public void testDubboAction() {
        response_data.setText("");
        String zk = zk_input.getText();
        String interfaze = interface_input.getText();
        String method = method_input.getText();
        String group = group_input.getText();
        String version = version_input.getText();
        String requestParam = JSON.toJSONString(personData);
        Config config = Config.builder().zooKeeper(zk)
                .interfaze(interfaze)
                .methodName(method)
                .group(group)
                .version(version)
                .requestParam(requestParam)
                .build();
        DubboTestService dubboTestService = new DubboTestService();
        try {
            PropertyTools.saveJFConfig(config);
            Object response = dubboTestService.getResponse(config, personData);
            response_data.setText(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            this.showAlert(alert, "Dubbo Interface Invoke Result", "Dubbo interface invoke SUCCESS!");
        } catch (Exception e) {
            response_data.setText(ExceptionTools.getStackTrace(e));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            this.showAlert(alert, "Dubbo Interface Invoke Result", "Dubbo interface invoke FAILED!");
        }
    }


    @FXML
    public void testZkAction() {
        ConfigService configService = new ConfigService();
        String zk_address = StringUtils.isNotEmpty(zk_input.getText()) ? zk_input.getText().trim() : "";
        boolean isConnenct = configService.checkZooKeeper(zk_address);

        Alert alert = isConnenct ? new Alert(Alert.AlertType.INFORMATION) : new Alert(Alert.AlertType.ERROR);
        alert.setTitle(" Zookeeper Test result");
        alert.setHeaderText(null);
        String contextText = isConnenct ? "SUCCESS: Zookeeper is connected!" : "FAILED: Cannot connect to ZooKeeper!";
        alert.setContentText(contextText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.show();
    }

    @FXML
    public void addParamAction(){
        Parameters parameters = new Parameters("","");
    }

    @FXML
    public void checkParamAction(){

    }

    @FXML
    public void tableDataCommit(TableColumn.CellEditEvent<Parameters,String> value){
        /** 输入的值*/
        String input = value.getNewValue();
        /** 输入值在表格中位置*/
        int columnIndex = value.getTablePosition().getColumn();
        int rowIndex = value.getTablePosition().getRow();
        /** 更新新值到表list中*/
        Parameters updateParam = personData.get(rowIndex);
        if(columnIndex == 0) {
            updateParam.setParamType(input);
        } else {
            updateParam.setParamValue(input);
        }
        System.out.println(rowIndex +"----------" +columnIndex + "---------" +input);

        Iterator<Parameters> iterator = personData.iterator();
        while(iterator.hasNext()){
            Parameters parameters = iterator.next();
            if(StringUtils.isEmpty(parameters.getParamType())
                    && StringUtils.isEmpty(parameters.getParamValue())){
                iterator.remove();
            }
        }
        personData.add(new Parameters("", ""));
        param_table.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        param_table.setEditable(Boolean.TRUE);
        paramType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        paramValue.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        paramValue.setEditable(Boolean.TRUE);
        paramType.setEditable(Boolean.TRUE);
        paramType.setCellFactory(TextFieldTableCell.forTableColumn());
        paramValue.setCellFactory(TextFieldTableCell.forTableColumn());

        //绑定数据到TableView
        param_table.setItems(personData);

        try {
            Config config = PropertyTools.getJFConfig();
            zk_input.setText(config.getZooKeeper());
            interface_input.setText(config.getInterfaze());
            method_input.setText(config.getMethodName());
            version_input.setText(config.getVersion());
            group_input.setText(config.getGroup());
            String tableList = config.getRequestParam();
            if(tableList != null){
                List<Parameters> parametersList = JSON.parseArray(tableList, Parameters.class);
                personData.addAll(parametersList);
            } else{
                personData.add(new Parameters("",""));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            this.showAlert(alert, "Config init Result", "Config init FAILED!");
        }

    }

    private void showAlert(Alert alert, String alertTitle, String alertContext){
        alert.setTitle(alertTitle);
        alert.setContentText(alertContext);
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.show();
    }


    @FXML
    public void testLoadFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(root_pane.getScene().getWindow());

        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}
