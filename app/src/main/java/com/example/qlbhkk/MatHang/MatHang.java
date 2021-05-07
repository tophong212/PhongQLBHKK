package com.example.qlbhkk.MatHang;

import java.io.Serializable;

public class MatHang  implements Serializable {
public String maMH,tenMH,moTa;

    public MatHang(String maMH, String tenMH, String moTa) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return  maMH + "\n" + tenMH ;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public MatHang() {
    }
}
