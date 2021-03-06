package com.vinay.imagesearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vinay.imagesearch.adapters.CommentsAdapter;
import com.vinay.imagesearch.db.DatabaseHandler;
import com.vinay.imagesearch.models.ImageModel;
import com.vinay.imagesearch.utils.ConverterKt;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CommentsActivity.
 *
 * User can add comments for the image.
 * Comments are stored locally on device in Sqlite DB
 *
 * When ever screen opens existing comments are checked in db and displyed in a list
 * along with time of adding
 *
 * @author vinay
 */
public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageModel imageModel;
    ImageView imageView;
    EditText et_comment;
    RecyclerView rv_comments;

    DatabaseHandler dh;
    ArrayList<HashMap<String, String>> commentsList;
    CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Setting custom icon for Actionbar back
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dh = new DatabaseHandler(this);

        initViews();
    }

    private void initViews() {

        // Get image details from intent
        imageModel = (ImageModel) getIntent().getSerializableExtra("data");

        //setting activity title as image title
        getSupportActionBar().setTitle(ConverterKt.toTitleCase(imageModel.getTitle()));

        imageView = findViewById(R.id.imageView);
        et_comment = findViewById(R.id.et_comment);
        rv_comments = findViewById(R.id.rv_comments);

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

        Glide.with(getApplicationContext())
                .load(imageModel.getLink())
                .into(imageView);

        commentsList = dh.getComments(imageModel.getId());
        commentsAdapter = new CommentsAdapter(commentsList);
        rv_comments.setAdapter(commentsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // trigger onBackPress on actionbar back press
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                // Adding to list and instantly displaying comment in recycleview
                commentsList.add(0, new HashMap<String, String>() {{
                    put("comment_text", et_comment.getText().toString());
                    put("time_stamp", System.currentTimeMillis() + "");
                }});
                commentsAdapter.notifyDataSetChanged();

                // Actual storing of comment in sqlite db call
                long res = dh.addComment(imageModel.getId(), et_comment.getText().toString());
//                Toast.makeText(getApplicationContext(), res + "", Toast.LENGTH_SHORT).show();
                et_comment.setText(""); //clear comment box
                break;
            default:
        }
    }
}