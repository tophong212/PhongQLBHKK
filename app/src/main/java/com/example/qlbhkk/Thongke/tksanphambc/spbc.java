package com.example.qlbhkk.Thongke.tksanphambc;

public class spbc {
    String masp,tensp;

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    int soluong;

    public spbc() {
    }

    public spbc(String masp, String tensp, int soluong) {
        this.masp = masp;
        this.tensp = tensp;
        this.soluong = soluong;
    }
}
