package com.ycxt.pos.utils;

/**
 * Created by fudiyang on 2016/9/5.
 * Author fudiyang
 * Description :
 */
public interface  Callback {

    void onBefore();

    boolean onRun();

    void onAfter(boolean b);
}
