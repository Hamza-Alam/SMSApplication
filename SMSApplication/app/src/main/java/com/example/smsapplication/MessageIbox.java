package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.ArrayList;
import java.util.List;

public class MessageIbox extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<MessageModel> messageModels;
    private LinearLayoutManager  layoutManager;
    SearchView searchView;
    MyHelper myHelper;
    String id;
    Button back;
    MessagAdapter messagAdapter;
    ArrayList<MessageModel> messageGet;

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MessageIbox.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_ibox);
        myHelper=new MyHelper(this);

        recyclerView=findViewById(R.id.message_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        searchView=findViewById(R.id.messageSearch);
        back=findViewById(R.id.backBtn);
        messageModels=new ArrayList<>();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        );



//        Uri uriSms = Uri.parse("content://sms/");
//        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "thread_id", "address", "date", "body"},null,null,null);
//        cursor.moveToFirst();
//
         messageGet= new ArrayList<MessageModel>();
//        while  (cursor.moveToNext())
//        {
//            MessageModel codes =new MessageModel();
//
//            codes.setId( cursor.getString(0));
//            codes.setMessage( cursor.getString(2));
//            codes.setDate(cursor.getString(3));
//            codes.setBody(cursor.getString(4));
//            messageGet.add(codes);
//        }

        mAdapter=new MessagAdapter((ArrayList<MessageModel>) messageGet,getApplicationContext());
        messagAdapter=new MessagAdapter(messageModels,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        searchView.setEnabled(false);



        GetAllMessages("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                GetAllMessages(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0)
                {
                    ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
                    params.height=3000;
                    recyclerView.setLayoutParams(params);
                    GetAllMessages(s);

                }
                else {
                    ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
                    params.height=3000;
                    recyclerView.setLayoutParams(params);
                    GetAllMessages("");
                }
                return false;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                GetAllMessages("");
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MessageIbox.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        FloatingActionButton fab = findViewById(R.id.fabSend);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MessageIbox.this,Send_Pre_Built_Message.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });

    }

//    private void closeKeyboard() {
//        View view=this.getCurrentFocus();
//        if(view!=null)
//        {
//            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
//        }
//    }


    private void GetAllMessages(String s){
        Uri uriSms = Uri.parse("content://sms/");
        messageGet.clear();
        //messagAdapter.notifyDataSetChanged();
        recyclerView.removeAllViewsInLayout();

        Cursor cursor = s == null || s.equals("") ? this.getContentResolver().query(uriSms, new String[]{"address", "body","type","read"},null,null,null) : this.getContentResolver().query(uriSms, new String[]{"address", "body","type"},"body like ? or address like ?",new String[]{"%" + s + "%","%" + s + "%"},null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();

    do {
        MessageModel codes = new MessageModel();
        if(cursor.getString(3).equals("0"))
        {
            codes.setMessage(cursor.getString(0));
            codes.setBody(cursor.getString(1));
            if (cursor.getString(2).equals("1")) {
                codes.setType("ic_call_received_black_24dp");
            } else {
                codes.setType("ic_call_made_black_24dp");
            }
            messageGet.add(codes);
        }
        else
        {

        }
        // System.out.println( "Id >> " + cursor.getString(0) + " Message >> " + cursor.getString(1) + " Date >> " + cursor.getString(2) + " Body >> " + cursor.getString(3) + " Type >> " + cursor.getString(4));

    } while (cursor.moveToNext());

        }
        cursor.close();
        messagAdapter.notifyDataSetChanged();

//        for(MessageModel sr : messageGet){
//            System.out.println( "Id >> " + sr.getId() + " Message >> " + sr.getMessage() + " Date >> " + sr.getDate() + " Body >> " + sr.getBody());
//
//        }
    }


    


}
