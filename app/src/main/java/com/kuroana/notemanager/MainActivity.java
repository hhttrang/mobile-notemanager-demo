package com.kuroana.notemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import serverRepository.NoteRepository;
import sqliteHelper.Note;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private NoteRepository mRespository;
    private EditText mTextMoney;
    private  EditText mTextNote;
    private EditText mTextImgLink;
    private Button mButtonDate;
    private Button mButtonAdd;
    private Button mButtonList;
    private RadioGroup mRadioGrp;
    private RadioButton mRadiobtnThu;
    private RadioButton mRadiobtnChi;
 //   int year_x, month_x, day_x;
    private boolean thu;
  //  static final int DIALOG_ID = 0;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
    }
    private void initialView(){
        mRespository = new NoteRepository(getApplication());
        mTextMoney = findViewById(R.id.txt_money);
        mTextNote = findViewById(R.id.txt_ghichu);
        mRadioGrp = findViewById(R.id.rdg_thuchi);
        mRadiobtnThu = findViewById(R.id.radiobtn_thu);
        mRadiobtnChi = findViewById(R.id.radiobtn_chi);
        mTextImgLink = findViewById(R.id.txt_imagelink);
        mRadioGrp.check(R.id.radiobtn_chi);
        thu = false;

        mRadiobtnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadioGrp.check(R.id.radiobtn_chi);
                thu = false;
            }
        });
        mRadiobtnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadioGrp.check(R.id.radiobtn_thu);
                thu = true;
            }
        });
        mButtonAdd = findViewById(R.id.button_add);
        mButtonList = findViewById(R.id.button_viewlist);
        mButtonDate = findViewById(R.id.button_viewdate);
        mButtonList.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonDate.setOnClickListener(this);

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                mButtonDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add:
                try{
                    buttonAdder();
                }catch(Exception e){
                    Log.e("Add has error: " , e.getMessage()+"");
                }
                break;

            case R.id.button_viewlist:
                intentToSecondActivity();
                break;
            case R.id.button_viewdate:
                pickTime();
                break;
        }
    }
    private void pickTime(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,mOnDateSetListener,year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
   /* @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID) return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            mButtonDate.setText(day_x+"/"+month_x+"/"+year_x);
        }
    };*/

    private void intentToSecondActivity(){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        //
        startActivity(intent);
    }
    private void buttonAdder() throws ParseException {
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = format.parse(mButtonDate.getText().toString());*/

        if(mRadiobtnThu.isChecked()){
            thu = true;
        }

        mRespository.maxId(new NoteRepository.OnGetIdListener() {

            @Override
            public void onGetData(int id) {
                int newId = id + 1;
                Log.e("max id is: ",id+"");
                Log.e("new id is: ",newId+"");
                insertNote(newId);

            }
        });
    }
    private void insertNote(int newId){
        Note note = new Note();
        note.setId(newId);

        note.setGhiChu(mTextNote.getText().toString());
        note.setNgayThuChi(mButtonDate.getText().toString());
        note.setThuChi(thu);
        note.setSoTien(Float.parseFloat(mTextMoney.getText().toString()));
        note.setImageLink(mTextImgLink.getText().toString());
        mRespository.insertNote(note);
        Log.e("ghi chu: ",mTextNote.getText().toString()+"");

        Toast.makeText(MainActivity.this, "Add Note Successful", Toast.LENGTH_SHORT).show();
        mButtonDate.setText("Pick a date");
        mRadioGrp.check(R.id.radiobtn_chi);
        thu = false;
        mTextMoney.setText(null);
        mTextNote.setText(null);
    }
}
