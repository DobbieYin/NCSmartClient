package com.view.ctrl;

import com.view.service.impl.NCSmartClientImpl;
import com.view.service.itf.INCSmartClient;
import com.view.utils.SafeObject;
import com.view.vo.NCSmartClientVO;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;

public class NCSmartClientCtrl {

    public TableColumn tblc_name;
    public TableColumn tblc_javahome;
    public TableColumn tblc_ip;
    public TableColumn tblc_port;

    public TextField txt_name;
    public TextField txt_javahome;
    public TextField txt_ip;
    public TextField txt_port;

    public Label lbl_status;

    public Button btn_add;
    public Button btn_save;
    public Button btn_del;
    public Button btn_refresh;

    public TableView<NCSmartClientVO> tbl_clients;


    private INCSmartClient service = new NCSmartClientImpl();
    private NCSmartClientVO currSelectedItem;
    /**
     * onload方法
     */
    public void onload(){
        //绑定列值
        try {
            initTableColumn(this.tblc_name,NCSmartClientVO.NAME);
            initTableColumn(this.tblc_javahome, NCSmartClientVO.JAVAHOME);
            initTableColumn(this.tblc_ip, NCSmartClientVO.IP);
            initTableColumn(this.tblc_port, NCSmartClientVO.PORT);

            this.tbl_clients.getSortOrder().add(this.tblc_name);//排序
            //1.常规实现
//            this.tbl_clients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NCSmartClientVO>() {
//                @Override
//                public void changed(ObservableValue<? extends NCSmartClientVO> observable, NCSmartClientVO oldValue, NCSmartClientVO newValue) {
//                    currSelectedItem = newValue != null ? newValue : oldValue;
//                }
//            });
            //2.Lambda表达式实现
            this.tbl_clients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        currSelectedItem = newValue != null ? newValue : oldValue;
                        if(currSelectedItem != null){
                            this.txt_name.setText(currSelectedItem.getName());
                            this.txt_ip.setText(currSelectedItem.getIp());
                            this.txt_port.setText(currSelectedItem.getPort());
                            this.txt_javahome.setText(currSelectedItem.getJavahome());
                        }
                    }
            );

            this.tbl_clients.setRowFactory((param) -> {
                    return new TableRowControl();
                }
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("初始化列异常："+e.getMessage());
            this.lbl_status.setText("初始化失败！");
        }
        //刷新
        refreshAction();
        this.lbl_status.setText("初始化完成！");
    }

    /**
     * 初始化列
     * @param column 列对象
     * @param propertyName 列对应的属性子段名称
     */
    private void initTableColumn(TableColumn column, final String propertyName) throws Exception{
        if(column == null || propertyName == null) throw new Exception("[列对象]或[列对应的属性子段名称]为空！");
        column.setCellValueFactory(new PropertyValueFactory<NCSmartClientVO, String>(propertyName));//设置列值映射
        column.setCellFactory(TextFieldTableCell.<NCSmartClientVO>forTableColumn());
        column.setSortType(TableColumn.SortType.ASCENDING);
    }
    /**
     * 新增
     */
    public void addAction(){
        this.currSelectedItem = null;
        this.txt_name.setText("请输入名称");
        this.txt_ip.setText("");
        this.txt_port.setText("");
        this.txt_javahome.setText("");

        this.txt_name.requestFocus();//获取光标
    }

    /**
     *保存
     */
    public void saveAction() {
        if (this.tbl_clients.getItems().size() > 0) {
            try {
                if (currSelectedItem != null) {//修改操作
                    currSelectedItem.setName(this.txt_name.getText());
                    currSelectedItem.setIp(this.txt_ip.getText());
                    currSelectedItem.setPort(this.txt_port.getText());
                    currSelectedItem.setJavahome(this.txt_javahome.getText());
                    this.tbl_clients.getItems().add(currSelectedItem);
                    this.reload();
                    this.lbl_status.setText("保存成功！");

                } else {//新增操作
                    if (!SafeObject.isNotNull(this.txt_name.getText())) {
                        this.lbl_status.setText("名称不能为空！");
                        this.txt_name.requestFocus();//获取光标
                    } else if (!SafeObject.isNotNull(this.txt_ip.getText())) {
                        this.lbl_status.setText("IP地址不能为空！");
                        this.txt_ip.requestFocus();//获取光标
                    } else {
                        if (SafeObject.isNull(this.txt_port.getText())) {
                            this.txt_port.setText("80");
                        }
                        this.tbl_clients.getItems().add(new NCSmartClientVO(this.txt_name.getText(), this.txt_ip.getText(), this.txt_port.getText(), this.txt_javahome.getText()));
                        this.reload();
                        this.lbl_status.setText("新增成功！");
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.err.println("保存异常：" + e.getMessage());
                this.lbl_status.setText("保存异常：" + e.getMessage());
            }
        } else {
            this.lbl_status.setText("表中无记录！");
        }
    }

    /**
     *删除
     */
    public void delAction(){
        try {
            NCSmartClientVO selectedItem = getSelectedItem();
            if (selectedItem != null) {
                this.tbl_clients.getItems().remove(selectedItem);
                this.reload();
                this.lbl_status.setText("刪除成功！");
            }else{
                this.lbl_status.setText("请选中一行！");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("刪除异常："+e.getMessage());
            this.lbl_status.setText("刪除异常："+e.getMessage());
        }
    }

    /**
     * 获取TableView选中项
     * @return TableView选中项
     */
    private NCSmartClientVO getSelectedItem() {
        return currSelectedItem;
    }

    /**
     *重新加载
     */
    private void reload() throws Exception{
        ObservableList<NCSmartClientVO> items = this.tbl_clients.getItems();
        this.service.saveAll(items);
        refreshAction();
    }

    /**
     *刷新
     */
    public void refreshAction() {
        try {
            this.tbl_clients.setItems(this.service.getAll());
            this.lbl_status.setText("刷新成功！");
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("刷新异常：" + e.getMessage());
            this.lbl_status.setText("刷新异常：" + e.getMessage());
        }
    }

    /**
     *打开客户端
     * @param vo 客户端描述对象
     * @return 打开客户端是否成功，返回true为打开成功，反之打开失败
     */
    private boolean openClient(NCSmartClientVO vo){
        try {
            this.service.openClient(vo);
            this.lbl_status.setText("打开客户端成功！");
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("打开客户端异常：" + e.getMessage());
            this.lbl_status.setText("打开客户端异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 表格行控制类
     */
    private class TableRowControl extends TableRow<NCSmartClientVO> {
        public TableRowControl() {
            super();
            this.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && TableRowControl.this.getIndex() < NCSmartClientCtrl.this.tbl_clients.getItems().size()) {
                        if(!NCSmartClientCtrl.this.tbl_clients.isEditable())
                            openClient(NCSmartClientCtrl.this.currSelectedItem);
                    }
                }
            );
        }
    }
}
