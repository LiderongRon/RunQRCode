package com.lixz.zxing;

/**
 * @author Ron
 * 创建日期：2023年6月1日
 * version：
 * 描述：
 */
public interface CallBackScanQRCodes {

    /**
     * 扫码返回来的数据
     * @param data
     */
    void canQRCodesSuccess(String data);
}
