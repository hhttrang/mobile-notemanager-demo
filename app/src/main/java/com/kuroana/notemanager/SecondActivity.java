package com.kuroana.notemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import serverRepository.NoteRepository;
import sqliteHelper.Note;

public class SecondActivity  extends AppCompatActivity  implements View.OnClickListener{
    RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRcvAdapter;
    LinearLayout mDelLayout;
 //   List<Note> NoteList;
    private Button mButtonDate;
    private Button mButtonSearch;
    private Button mButtonDelete;
   // private NoteRepository mRespository;

    public static final String BUNDLE_ID = "ID";
    public static final String BUNDLE_DATE = "DATE";
    public static final String BUNDLE_CHI = "CHITHU";
    public static final String BUNDLE_MONEY = "MONEY";
    public static final String BUNDLE_ND = "CONTENT";
    public static final String BUNDLE_IMG_LINK = "IMAGE_LINK";

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
 //   public static final String BUNDLE_NOTE = "NOTE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialView();
    }

    private void initialView(){
        mRecyclerView =  findViewById(R.id.recycler_view);
       /* mRespository.getAllNote(new NoteRepository.OnGetDataListener() {

            @Override
            public void onDataSuccess(List<Note> noteList) {
                if(noteList !=null){
                    NoteList = noteList;
                    Log.e("list size: " , noteList.size()+"");
                }
            }
            @Override
            public void onDataFail(String message) {

            }
        });*/
        mRecyclerView.setHasFixedSize(true);

        mRcvAdapter = new RecyclerViewAdapter(this, getApplication());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);

        mRcvAdapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickedListener() {

            @Override
            public void onItemClick(int id, String date, boolean chithu, float money, String nd, boolean check, String imgLink) {
                Toast.makeText(SecondActivity.this, id +" - "+date, Toast.LENGTH_SHORT).show();
                Note note = new Note(id,date,chithu,money,nd, imgLink);
                Intent intent = new Intent(SecondActivity.this, ShowInfoActivity.class);
                intent.putExtra(BUNDLE_ID, id);
                intent.putExtra(BUNDLE_DATE, date);
                intent.putExtra(BUNDLE_CHI, chithu);
                intent.putExtra(BUNDLE_MONEY, money);
                intent.putExtra(BUNDLE_ND, nd);
                intent.putExtra(BUNDLE_IMG_LINK, imgLink);

                ShowInfoActivity.IntentToShowInfo(SecondActivity.this, intent);
            }
        });

        mButtonDate = findViewById(R.id.button_viewdate);
        mButtonSearch = findViewById(R.id.button_search);
        mDelLayout = findViewById(R.id.layout_delete);
        mButtonDelete = findViewById(R.id.button_delete);
        mButtonDate.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                mButtonDate.setText(dayOfMonth+"/"+month+"/"+year);
                mRcvAdapter.updateData(mButtonDate.getText().toString(),getApplication());
                mDelLayout.setVisibility(View.VISIBLE);
            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_viewdate:
                pickTime();
                break;

            case R.id.button_search:
                mRcvAdapter.updateData(mButtonDate.getText().toString(),getApplication());
                break;
            case R.id.button_delete:
                mRcvAdapter.deleteData(getApplication());
                break;
        }
    }
    private void pickTime(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(SecondActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,mOnDateSetListener,year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
