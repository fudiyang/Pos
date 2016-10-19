package com.ycxt.pos.activity;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;


/**
 * Base Activity.
 * Each code sample activity extends it.
 * @author chenwei
 *
 */
public abstract class BaseActivity extends Activity {
	private Handler handler = new Handler();
	private boolean isDeviceServiceLogined = false;
	
	protected boolean isDeviceServiceLogined() {
		return isDeviceServiceLogined;
	}

	/**
	 * Run something on ui thread after milliseconds.
	 * @param r
	 * @param
	 */
	public void runOnUiThreadDelayed(Runnable r, int delayMillis) {
		handler.postDelayed(r, delayMillis);
	}
	
	/**
	 * To gain control of the device service,
	 * you need invoke this method before any device operation.
	 */
	public void bindDeviceService() {
		try {
			isDeviceServiceLogined = false;
			DeviceService.login(this);
			isDeviceServiceLogined = true;
		} catch (RequestException e) {
			// Rebind after a few milliseconds, 
			// If you want this application keep the right of the device service
//			runOnUiThreadDelayed(new Runnable() {
//				@Override
//				public void run() {
//					bindDeviceService();
//				}
//			}, 300);
			e.printStackTrace();
		} catch (ServiceOccupiedException e) {
			e.printStackTrace();
		} catch (ReloginException e) {
			e.printStackTrace();
		} catch (UnsupportMultiProcess e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Release the right of using the device.
	 */
	public void unbindDeviceService() {
		DeviceService.logout();
		isDeviceServiceLogined = false;
	}
	
	/**
	 * Get handler in the ui thread
	 * @return
	 */
	public Handler getUIHandler() {
		return handler;
	}
	/*------------------------------------------------------------------------------------*/

	public void toast(String msg){
    	Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
	/**
	 * 验证密钥长度，一般为8的倍数，看项目需求
	 * @param key
	 * @return
	 */
	public   boolean isValidKey(String key) {
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		return (key.length() % 16 == 0);
	}
	public void log(String msg){
		Log.d(getClass().getSimpleName(),  msg);
	}
}
