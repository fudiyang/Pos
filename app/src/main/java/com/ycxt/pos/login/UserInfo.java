package com.ycxt.pos.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class UserInfo {

    private String USER_INFO = "userInfo";

    private Context context;

     public UserInfo() {
           }

     public UserInfo(Context context) {

                 this.context = context;
          }
        // 存放字符串型的值
              public void setUserInfo(String key, String value) {
                SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                          Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = sp.edit();
               editor.remove(key);
                editor.putString(key, value);
                editor.commit();
            }

    // 获得用户信息中某项字符串型的值
         public String getStringInfo(String key) {
              SharedPreferences sp = context.getSharedPreferences(USER_INFO,
                                Context.MODE_PRIVATE);
                return sp.getString(key, "");
            }
}
