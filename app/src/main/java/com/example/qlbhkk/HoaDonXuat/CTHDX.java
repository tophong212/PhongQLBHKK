package com.example.qlbhkk.HoaDonXuat;

import java.io.Serializable;

public class CTHDX implements Serializable {
    String maHDX,maSP;
    int soluong;

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    int giaban;

    public CTHDX(String maHDX, String maSP, int soluong, int giaban) {
        this.maHDX = maHDX;
        this.maSP = maSP;
        this.soluong = soluong;
        this.giaban = giaban;
    }

    public String getMaHDX() {
        return maHDX;
    }

    public void setMaHDX(String maHDX) {
        this.maHDX = maHDX;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public CTHDX(String maHDX, String maSP, int soluong) {
        this.maHDX = maHDX;
        this.maSP = maSP;
        this.soluong = soluong;
    }

    public CTHDX() {
    }
}
