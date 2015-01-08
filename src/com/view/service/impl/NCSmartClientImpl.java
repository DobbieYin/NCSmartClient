package com.view.service.impl;

import com.view.service.itf.INCSmartClient;
import com.view.vo.NCSmartClientVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author yinfx
 * @time 15-1-5 下午6:57
 */
public class NCSmartClientImpl implements INCSmartClient {
    /**
     *
     */
    public static final String SPLITCHAR = "<";
    public static final String CLIENTRES = "clientres";
    public static final String CLIENTS_PROPERTIES = "clients.properties";

    @Override
    public ObservableList<NCSmartClientVO> getAll()  throws Exception{
        ObservableList<NCSmartClientVO> list = FXCollections.observableArrayList();
        try {
            Properties pro = getProperties();
            Enumeration<Object> keys = pro.keys();
            if(keys != null) {
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String value = key != null ? (String) pro.get(key) : null;
                    NCSmartClientVO vo = getVOByKeyValuePair(key, value);
                    if (vo != null) {
                        list.add(vo);
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        }
        return list;
    }

    /**
     * 封装VO
     * @param key
     * @param value
     * @return VO
     */
    private NCSmartClientVO getVOByKeyValuePair(String key,String value) {
        if(key != null && value != null){
            String[] clientInfo = value.split(SPLITCHAR);
            if(clientInfo != null && clientInfo.length > 2){
                return new NCSmartClientVO(key,clientInfo[0],clientInfo[1],clientInfo[2]);
            }
        }
        return null;
    }

    /**
     * 获取Properties对象
     * @return
     * @throws IOException
     */
    private Properties getProperties() throws Exception {
        Properties pro = new Properties();
        File file = getPropertiesFile();
        pro.load(new FileInputStream(file));
        return pro;
    }

    /**
     * 获取Properties文件对象
     * @return
     * @throws IOException
     */
    private File getPropertiesFile() throws Exception {
        File file = new File(CLIENTS_PROPERTIES);
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    @Override
    public boolean saveAll(ObservableList<NCSmartClientVO> list)  throws Exception{
        if(!(list != null)) return true;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getPropertiesFile());
            Properties pro = getProperties();
            for (NCSmartClientVO vo : list) {
                pro.put(vo.getName(), vo.getJavahome() + SPLITCHAR + vo.getIp() + SPLITCHAR + vo.getPort());
            }
            pro.store(fos, "SmartClient:" + System.currentTimeMillis());
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fos = null;
                }
            }
        }
    }

    @Override
    public boolean openClient(NCSmartClientVO vo) throws Exception {
        if(vo == null) return false;
        try {
            //读取文件
            String clientres = new String(Files.readAllBytes(Paths.get(CLIENTRES)));
            //替换IP地址和端口
            clientres = clientres.replace("[ip]",vo.getIp());
            clientres = clientres.replace("[port]",vo.getPort());
            System.out.println("配置文件信息：\n"+clientres);
            //保存文件
            Path path = Files.write(Paths.get(vo.getName()), clientres.getBytes());
            //执行命令
            String command = "javaws -localfile -J-Djnlp.application.href=http://" + vo.getIp() + ":" + vo.getPort() + "/ncws.jnlp " + path.toString();
            command = (vo.getJavahome() != null && !vo.getJavahome().trim().equals("")) ? vo.getJavahome()+"/bin/"+command : command;
            System.out.println("执行命令：\n"+command);
            Process process = Runtime.getRuntime().exec(command);
            return true;
        } catch (Exception e) {
            throw new Exception("打开客户端异常："+e.getMessage(),e);
        }
    }
}
