package com.view.service.itf;

import com.view.vo.NCSmartClientVO;
import javafx.collections.ObservableList;

/**
 * @author yinfx
 * @time 15-1-5 下午6:51
 */
public interface INCSmartClient {
    /**
     *查询所有项
     * @return
     */
    ObservableList<NCSmartClientVO> getAll() throws Exception;

    /**
     *保存所有项
     * @param list
     * @return
     */
    boolean saveAll(ObservableList<NCSmartClientVO> list) throws Exception;

    /**
     *打开客户端
     * @param vo 客户端描述对象
     * @return
     */
    boolean openClient(NCSmartClientVO vo) throws Exception;
}
