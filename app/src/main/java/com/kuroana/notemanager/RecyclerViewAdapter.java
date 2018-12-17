package com.kuroana.notemanager;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import serverRepository.NoteRepository;
import sqliteHelper.Note;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
   // private Context mContext;
    private List<Note> notelist = new ArrayList<Note>();
    private NoteRepository mNoteRepository;

    public RecyclerViewAdapter(Context mContext, Application app) {
      /*  this.mContext = mContext;
        this.Notelist = Notelist; */
        mNoteRepository = new NoteRepository(app);

        mNoteRepository.getAllNote(new NoteRepository.OnGetDataListener() {

            public void onDataSuccess(List<Note> nserList) {
                notelist.addAll(nserList);
                notifyDataSetChanged();
                Log.e("size Note List ",notelist.size()+"");
            }

            public void onDataFail(String message) {

            }
        });
    }
   /* public RecyclerViewAdapter(List<Note> Notelist) {
        this.Notelist = Notelist;
    }*/

   public void updateData(String searchDate,Application app){
       notelist = new ArrayList<>();
       mNoteRepository = new NoteRepository(app);
       mNoteRepository.getAllNoteByDate(new NoteRepository.OnGetDataListener() {
           @Override
           public void onDataSuccess(List<Note> userList) {
               notelist.addAll(userList);
               notifyDataSetChanged();
           }
           @Override
           public void onDataFail(String message) {

           }
       },searchDate);
   }
   private List<Note> getDeleteList(){
       List<Note> list = new ArrayList<Note>();
       for(int i = 0; i < notelist.size();i++){
           if(notelist.get(i).isChecked()) list.add(notelist.get(i));
       }
       return list;
   }
    public void deleteData(Application app){
        mNoteRepository = new NoteRepository(app);
        List<Note> deleteList = getDeleteList();
        for(int i = 0; i < deleteList.size();i++){
            mNoteRepository.deleteNote(deleteList.get(i));
        }
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.txtNoteDate.setText(notelist.get(position).getNgayThuChi().toString()+"");
        String status = "";
        if(notelist.get(position).isThuChi()){
            status = "Thu";
        }else{
            status = "Chi";
        }
        holder.txtNoteThu.setText("Status: "+status);
        holder.txtNoteMoney.setText(notelist.get(position).getSoTien()+"");
        holder.txtNoteGhichu.setText(notelist.get(position).getGhiChu());
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView txtNoteDate;
        TextView txtNoteThu;
        TextView txtNoteGhichu;
        TextView txtNoteMoney;
        CheckBox cbDelete;
        LinearLayout line;

        void bind(int position) {
          //  mCheckedTextView.setText(String.valueOf(items.get(position).getPosition()));
            cbDelete.setChecked(notelist.get(position).isChecked());
        }
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtNoteDate = (TextView) itemView.findViewById(R.id.note_ngay);
            txtNoteThu = (TextView) itemView.findViewById(R.id.note_thu);
            txtNoteMoney = (TextView) itemView.findViewById(R.id.note_money);
            txtNoteGhichu = (TextView) itemView.findViewById(R.id.note_ghi_chu);
            line = (LinearLayout) itemView.findViewById(R.id.line);
            cbDelete = itemView.findViewById(R.id.cb_delete);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            // toggle the checked view based on the checked field in the model
            int adapterPosition = getAdapterPosition();

            if (notelist.get(adapterPosition).isChecked()) {
                cbDelete.setChecked(false);
                notelist.get(adapterPosition).setChecked(false);
            }
            else {
               // mCheckedTextView.setChecked(true);
                cbDelete.setChecked(true);
                notelist.get(adapterPosition).setChecked(true);
            }
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(int id, String date, boolean chithu, float money, String nd,boolean check, String imgLink);
    }
    private boolean isHadChanged(){
        // ????
        return false;
    }
    private boolean isChange(){
        // ??????
        return false;
    }
    private OnItemClickedListener onItemClickedListener;
    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

  /*  public interface OnItemClickedListener {
        void onItemClick(String username);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    } */
}
