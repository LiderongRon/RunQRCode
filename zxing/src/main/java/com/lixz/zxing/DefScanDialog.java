package com.lixz.zxing;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;
import com.lixz.zxing.qrcode.QRCodeView;

/**
 * @author Ron
 * 创建日期：2023年6月1日
 * version：
 * 描述：
 */
public class DefScanDialog extends Dialog implements QRCodeView.Delegate {

    public CallBackScanQRCodes callBackScanQRCodes;
    public Context mContext;
    private ZXingView mZXingView;

    public DefScanDialog(Context mContext,CallBackScanQRCodes callBackScanQRCodes) {
        super(mContext);
        this.mContext = mContext;
        this.callBackScanQRCodes = callBackScanQRCodes;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_def_scan);
        mZXingView = findViewById(R.id.dialog_zxing_view);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera();
        mZXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }


    /**
     * 扫描成功回调方法
     * @param result 摄像头扫码时只要回调了该方法 result 就一定有值，不会为 null。解析本地图片或 Bitmap 时 result 可能为 null
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
              Toast.makeText(mContext,"解析图片错误,请重新扫描图片",Toast.LENGTH_LONG).show();
        } else {
            callBackScanQRCodes.canQRCodesSuccess(result);
        }
        vibrate();
        dismiss();
    }

    /**
     * 振动方法
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    /**
     *
     * @param isDark 是否变暗
     */
    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(mContext,"打开相机出错",Toast.LENGTH_LONG).show();
    }
}
