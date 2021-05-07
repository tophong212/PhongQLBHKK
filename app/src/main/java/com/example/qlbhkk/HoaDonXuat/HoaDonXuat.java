package com.example.qlbhkk.HoaDonXuat;

import java.io.Serializable;

public class HoaDonXuat implements Serializable {
    public String maHDX,maNV,ngayBan;

    public HoaDonXuat(String maHDX, String maNV, String ngayBan) {
        this.maHDX = maHDX;
        this.maNV = maNV;
        this.ngayBan = ngayBan;
    }

    public HoaDonXuat() {
    }

    public String getMaHDX() {
        return maHDX;
    }

    public void setMaHDX(String maHDX) {
        this.maHDX = maHDX;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(String ngayBan) {
        this.ngayBan = ngayBan;
    }
}
