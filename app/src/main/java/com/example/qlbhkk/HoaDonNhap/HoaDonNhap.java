package com.example.qlbhkk.HoaDonNhap;

import java.io.Serializable;
//import java.util.Date;

public class HoaDonNhap implements Serializable {
    public String maHDN,maNV,maNCC,ngaynhap;

    @Override
    public String toString() {
        return "HoaDonNhap{" +
                "maHDN='" + maHDN + '\'' +
                ", maNV='" + maNV + '\'' +
                ", maNCC='" + maNCC + '\'' +
                ", ngaynhap='" + ngaynhap + '\'' +
                '}';
    }

    public String getMaHDN() {
        return maHDN;
    }

    public void setMaHDN(String maHDN) {
        this.maHDN = maHDN;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public HoaDonNhap(String maHDN, String maNV, String maNCC, String ngaynhap) {
        this.maHDN = maHDN;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngaynhap = ngaynhap;
    }

    public HoaDonNhap() {
    }
}
