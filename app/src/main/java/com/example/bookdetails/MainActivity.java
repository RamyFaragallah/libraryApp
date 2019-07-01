package com.example.bookdetails;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<DataModel>> {
private Button btnsearch;
private ListView lstbooks;
private ImageView imgbook;
private static  ProgressBar prgbooks;

    String GOOGLE_BOOKS_API;
    String s;
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final EditText editsearch=(EditText)findViewById(R.id.edit_txt_book);
        btnsearch=(Button)findViewById(R.id.search_btn);
        lstbooks=(ListView) findViewById(R.id.listview);
        imgbook=(ImageView)findViewById(R.id.img);
        prgbooks=(ProgressBar)findViewById(R.id.progeress_bar);

    prgbooks.setVisibility(View.INVISIBLE);

btnsearch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        s = editsearch.getText().toString();

        if (s.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Please Enter Book Title", Toast.LENGTH_SHORT).show();
        } else
        {
            prgbooks.setVisibility(View.VISIBLE);

            imgbook.setVisibility(View.GONE);

            GOOGLE_BOOKS_API =
                    "https://www.googleapis.com/books/v1/volumes?q=" + s;




            editsearch.setText("");
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(0, null,MainActivity.this );
        }
    }
});
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null,MainActivity.this );
        adapter=new DataAdapter(MainActivity.this,0,new ArrayList<DataModel>());
        lstbooks.setAdapter(adapter);
        lstbooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DataModel currentbook = adapter.getItem(position);

                String title = currentbook.getBook_title();
                String author = currentbook.getAuthor_name();
                String publisher = currentbook.getPublisher();
                String Date = currentbook.getDate();
                String description = currentbook.getDescription();
                String url = currentbook.getImage_url();

                Intent intent = new Intent(getApplicationContext(), BookDetails.class);

                intent.putExtra("title", title);
                intent.putExtra("author", author);
                intent.putExtra("pub", publisher);
                intent.putExtra("date", Date);
                intent.putExtra("desc", description);
                intent.putExtra("url", url);

                startActivity(intent);
            }
        });

    }

    @Override
    public Loader<ArrayList<DataModel>> onCreateLoader(int id, Bundle args) {

            return new bookloader(this, GOOGLE_BOOKS_API);


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DataModel>> loader, ArrayList<DataModel> data) {
        imgbook.setVisibility(View.GONE);
        prgbooks.setVisibility(View.GONE);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        adapter.clear();

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DataModel>> loader) {
        adapter.clear();

    }
}
