package com.example.qlbhkk.SanPham;

import java.io.Serializable;

public class SanPham implements Serializable {
    public String maSP, maMH, tenSP, nhanHieu,  kichCo;
    public int soluong, giaBan;

    public SanPham(String maSP, String maMH, String tenSP, String nhanHieu, String kichCo, int soluong, int giaBan) {
        this.maSP = maSP;
        this.maMH = maMH;
        this.tenSP = tenSP;
        this.nhanHieu = nhanHieu;
        this.kichCo = kichCo;
        this.soluong = soluong;
        this.giaBan = giaBan;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getNhanHieu() {
        return nhanHieu;
    }

    public void setNhanHieu(String nhanHieu) {
        this.nhanHieu = nhanHieu;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public SanPham() {
    }
}
