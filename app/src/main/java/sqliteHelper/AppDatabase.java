package sqliteHelper;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
//khi tang version tang so len la 2
@Database(entities = {Note.class}, version = 3)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database-name").build();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
                }
            }
        }
        return INSTANCE;
    }

    public static AppDatabase getINSTANCE(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"database_name").build();
                }
            }
        }
        return INSTANCE;
    }
    //dang alter them bang tang version

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `note` ADD COLUMN check_delete INTEGER default 0");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `note` ADD COLUMN image_link nchar(100)");
        }
    };
}
