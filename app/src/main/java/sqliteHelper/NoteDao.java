package sqliteHelper;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAll();


    @Query("SELECT * FROM note order by ngay_thu_chi")
    List<Note> getAllNote();

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM note WHERE ngay_thu_chi LIKE :date")
    List<Note> findByNgayThu(String date);

    @Query("SELECT MAX(id) FROM note")
    int maxId();

    @Insert
    void insertAll(Note... notes);
    @Insert
    void insert(Note note);
    @Delete
    void delete(Note... user);

    @Update
    void update(Note... user);
}
