package com.ycxt.pos.device.comm;

import android.telephony.TelephonyManager;

public interface OnStateChangeListener {
	public static final int STATE_DISCONNECTED = TelephonyManager.DATA_DISCONNECTED;//����Ͽ�
	public static final int STATE_DISCONNECTING =  TelephonyManager.DATA_CONNECTING;//������������		
	public static final int STATE_CONNECTED =  TelephonyManager.DATA_CONNECTED;//����������
	public static final int STATE_SUPENDED =  TelephonyManager.DATA_SUSPENDED;//�������
	void onStateChanged(int state);
	void onStateMessageReceived(String msg);
}
