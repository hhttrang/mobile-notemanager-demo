package com.kuroana.notemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

import serverRepository.NoteRepository;
import sqliteHelper.Note;

public class ShowInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private NoteRepository mRespository;
    private ImageView mViewImage;
    private TextView mViewID;
    private TextView mViewDate;
    private EditText mTxtEditMoney;
    private EditText mTxtEditContent;
    private Button mButtonUpdate;
    private RadioGroup mRadioGrp;
    private RadioButton mRadiobtnThu;
    private RadioButton mRadiobtnChi;
    private boolean thu;
    private String imgLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        initialView();
        initialIntent();
    }
    private void initialView(){
        mRespository = new NoteRepository(getApplication());
        mTxtEditMoney = findViewById(R.id.txt_editMoney);
        mTxtEditContent = findViewById(R.id.txt_editContent);
        mRadioGrp = findViewById(R.id.rdg_UpdateThuchi);
        mRadiobtnThu = findViewById(R.id.radiobtn_UpdateThu);
        mRadiobtnChi = findViewById(R.id.radiobtn_UpdateChi);
        mViewImage = findViewById(R.id.ViewImage);
        mViewID = findViewById(R.id.ViewID);
        mViewDate = findViewById(R.id.ViewDate);

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
        mButtonUpdate = findViewById(R.id.btn_Update);
        mButtonUpdate.setOnClickListener(this);
    }
    private void initialIntent(){
        String id = getIntent().getExtras().getString(SecondActivity.BUNDLE_ID);
        mViewID.setText(id);
        String name = getIntent().getExtras().getString(SecondActivity.BUNDLE_DATE);
        mViewDate.setText(name);
        String chithu = getIntent().getExtras().getString(SecondActivity.BUNDLE_CHI);
        if(chithu.equals("true")){
            mRadioGrp.check(R.id.radiobtn_thu);
            thu = true;
        }else{
            mRadioGrp.check(R.id.radiobtn_chi);
            thu = false;
        }
        String money = getIntent().getExtras().getString(SecondActivity.BUNDLE_MONEY);
        mTxtEditMoney.setText(money);
        String content = getIntent().getExtras().getString(SecondActivity.BUNDLE_ND);
        mTxtEditContent.setText(content);
        imgLink = getIntent().getExtras().getString(SecondActivity.BUNDLE_IMG_LINK);
        Picasso.get().load(imgLink).into(mViewImage);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Update:
                try{
                    updateInfo();
                }catch(Exception e){
                    Log.e("Add has error: " , e.getMessage()+"");
                }
                break;
        }
    }
    private void updateInfo() throws ParseException {
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = format.parse(mButtonDate.getText().toString());*/

        if(mRadiobtnThu.isChecked()){
            thu = true;
        }
        Note note = new Note();
        note.setId(Integer.parseInt(mViewID.getText().toString()));

        note.setGhiChu(mTxtEditContent.getText().toString());
        note.setNgayThuChi(mViewDate.getText().toString());
        note.setThuChi(thu);
        note.setSoTien(Float.parseFloat(mTxtEditMoney.getText().toString()));
        note.setImageLink(imgLink);
        mRespository.updateNote(note);

        Toast.makeText(ShowInfoActivity.this, "Add Note Successful", Toast.LENGTH_SHORT).show();
        intentToSecondActivity();
    }
    private void intentToSecondActivity(){
        Intent intent = new Intent(ShowInfoActivity.this, SecondActivity.class);
        //
        startActivity(intent);
    }
    public static void IntentToShowInfo(Activity activity, Intent intent)
    {
        // Intent intent = new Intent(activity, ShowInfoActivity.class);

        activity.startActivity(intent);
    }
}
