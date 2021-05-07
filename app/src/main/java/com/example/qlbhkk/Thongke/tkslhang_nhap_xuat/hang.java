package com.example.qlbhkk.Thongke.tkslhang_nhap_xuat;

public class hang {
    int thang,slnhap,slban;

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getSlnhap() {
        return slnhap;
    }

    public void setSlnhap(int slnhap) {
        this.slnhap = slnhap;
    }

    public int getSlban() {
        return slban;
    }

    public void setSlban(int slban) {
        this.slban = slban;
    }

    public hang(int thang, int slnhap, int slban) {
        this.thang = thang;
        this.slnhap = slnhap;
        this.slban = slban;
    }

    public hang() {
    }
}
