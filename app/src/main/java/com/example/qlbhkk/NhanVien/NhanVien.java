package com.example.qlbhkk.NhanVien;

import java.io.Serializable;

public class NhanVien implements Serializable {
    public String maNV,tenNV,gioiTinh;
    public int namSinh;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public NhanVien() {
    }

    public String diaChi,soDT;
    @Override
    public String toString() {
        return  maNV + "\n"  + tenNV  +"\n"+ soDT+"\n"+gioiTinh ;
    }
    public NhanVien(String maNV, String tenNV, String gioiTinh, int namSinh, String diaChi, String soDT) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
        this.soDT = soDT;
    }
}
