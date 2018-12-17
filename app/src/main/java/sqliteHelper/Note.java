package sqliteHelper;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Note {
    @PrimaryKey
    private int id;
    public Note() {
    }

    public Note(int id, String ngayThuChi, boolean thuChi, float soTien, String ghiChu,String imageLink) {
        this.id = id;
        this.ngayThuChi = ngayThuChi;
        this.thuChi = thuChi;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
        this.imageLink = imageLink;
    }

    @ColumnInfo(name = "ngay_thu_chi")
    private String ngayThuChi;
    @ColumnInfo(name = "thu_chi")
    private boolean thuChi;
    @ColumnInfo(name = "so_tien")
    private float soTien;
    @ColumnInfo(name = "ghi_chu")
    private String ghiChu;
    @ColumnInfo(name = "check_delete")
    private boolean isChecked;
    @ColumnInfo(name = "image_link")
    private String imageLink;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgayThuChi() {
        return ngayThuChi;
    }

    public void setNgayThuChi(String ngayThuChi) {
        this.ngayThuChi = ngayThuChi;
    }

    public boolean isThuChi() {
        return thuChi;
    }

    public void setThuChi(boolean thuChi) {
        this.thuChi = thuChi;
    }

    public float getSoTien() {
        return soTien;
    }

    public void setSoTien(float soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
