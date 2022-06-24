package com.simar.transaction_type.payload;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OutputPayload {
    String jenis_transaksi;
    ArrayList<Map<String, String>> data;

    public OutputPayload(String jenis_transaksi, ArrayList<Map<String, String>> data) {
        this.jenis_transaksi = jenis_transaksi;
        this.data = data;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }

    public ArrayList<Map<String, String>> getData() {
        return data;
    }

    public void setData(ArrayList<Map<String, String>> data) {
        this.data = data;
    }
}
