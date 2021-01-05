package com.aotuman.studydemo.wheelview.dialog;


import com.aotuman.studydemo.wheelview.base.IWheel;

public interface WheelDialogInterface<T extends IWheel> {

    boolean onClick(int witch, int selectedIndex, T item);
}