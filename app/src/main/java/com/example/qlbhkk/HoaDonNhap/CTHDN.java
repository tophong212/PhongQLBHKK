package com.example.qlbhkk.HoaDonNhap;

import java.io.Serializable;

public class CTHDN implements Serializable {
    public String maHDN,maSP;

    public int soluong,gianhap;

    public CTHDN(String maHDN, String maSP, int soluong, int gianhap) {
        this.maHDN = maHDN;
        this.maSP = maSP;
        this.soluong = soluong;
        this.gianhap = gianhap;
    }

    @Override
    public String toString() {
        return "CTHDN{" +
                "maHDN='" + maHDN + '\'' +
                ", maSP='" + maSP + '\'' +
                ", soluong=" + soluong +
                ", gianhap=" + gianhap +
                '}';
    }

    public String getMaHDN() {
        return maHDN;
    }

    public void setMaHDN(String maHDN) {
        this.maHDN = maHDN;
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

    public int getGianhap() {
        return gianhap;
    }

    public void setGianhap(int gianhap) {
        this.gianhap = gianhap;
    }



    public CTHDN() {
    }
}
