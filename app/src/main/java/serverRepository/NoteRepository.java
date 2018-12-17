package serverRepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import sqliteHelper.AppDatabase;
import sqliteHelper.Note;
import sqliteHelper.NoteDao;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mNote;
    public NoteRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
    }
    public LiveData<List<Note>> getNote(){
        return mNoteDao.getAll();
    }

    public void getAllNote(OnGetDataListener listener){
        new getAllUserAsyncTask(mNoteDao,listener).execute();
    }
    public void getAllNoteByDate(OnGetDataListener listener, String date){
        new getAllUserByDateAsyncTask(mNoteDao,listener, date).execute();
    }
    public void  insertNote(Note ... notes){
        new insertAsyncTask(mNoteDao).execute(notes);
    }
    public void  deleteNote(Note note){
        new deleteAsyncTask(mNoteDao).execute(note);
    }
    public void maxId(OnGetIdListener listener){
        new maxIdAsyncTask(mNoteDao,listener).execute();
    }
    public void  updateNote(Note note){
        new updateAsyncTask(mNoteDao).execute(note);
    }

    /*  public void  findByNgayThu(Date date){ new findByNgayThuAsyncTask(mNoteDao).execute(date);}
     private static class findByNgayThuAsyncTask extends AsyncTask<Date, Void, Note> {
         private NoteDao dao;

         public findByNgayThuAsyncTask(NoteDao dao) {
             this.dao = dao;
         }
         @Override
         protected Note doInBackground(Date... dates) {
             Note note = dao.findByNgayThu(dates[0]);
             return note;
         }
     } */

    private static class insertAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao mAsyncTaskDao;

        public insertAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Note... note) {
            mAsyncTaskDao.insertAll(note);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao mAsyncTaskDao;

        public deleteAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Note... note) {
            mAsyncTaskDao.delete(note);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao mAsyncTaskDao;

        public updateAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Note... note) {
            mAsyncTaskDao.update(note);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public interface OnGetDataListener{
        void onDataSuccess(List<Note> noteList);
        void onDataFail(String message);
    }
    public interface OnGetIdListener{
        void onGetData(int id);
    }

    private static class getAllUserAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao mAsyncTaskDao;
        private OnGetDataListener mListener;
        private List<Note> mNoteList;

        public getAllUserAsyncTask(NoteDao dao, OnGetDataListener listener){
            mAsyncTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteList = mAsyncTaskDao.getAllNote();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mNoteList !=null){
                mListener.onDataSuccess(mNoteList);
            }else{
                mListener.onDataFail("fail load data");
            }
        }

    }

    private static class getAllUserByDateAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao mAsyncTaskDao;
        private OnGetDataListener mListener;
        private List<Note> mNoteList;
        String sortDate;

        public getAllUserByDateAsyncTask(NoteDao dao, OnGetDataListener listener, String date){
            mAsyncTaskDao = dao;
            mListener = listener;
            sortDate = date;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteList = mAsyncTaskDao.findByNgayThu(sortDate);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mNoteList !=null){
                mListener.onDataSuccess(mNoteList);
            }else{
                mListener.onDataFail("fail load data");
            }
        }

    }

    private static class maxIdAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao mAsyncTaskDao;
        private OnGetIdListener mListener;
        private int maxId;

        public maxIdAsyncTask(NoteDao dao, OnGetIdListener listener){
            mAsyncTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            maxId = mAsyncTaskDao.maxId();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.onGetData(maxId);
        }

    }
}
