package com.example.bookdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<DataModel> {
    ImageView book_image;
    TextView book_title,author_name;
    public DataAdapter(Context context, int resource, ArrayList<DataModel> modelArrayList) {
        super(context, resource,modelArrayList);
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View view=convertView;
        if (view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        DataModel dataModel=getItem(position);
        author_name=(TextView)view.findViewById(R.id.author_name);
        book_title=(TextView)view.findViewById(R.id.book_title);
        book_image=(ImageView)view.findViewById(R.id.book_image);

        author_name.setText(dataModel.getAuthor_name());
        book_title.setText(dataModel.getBook_title());
        Picasso.get().load(dataModel.getImage_url()).placeholder(R.drawable.book).into(book_image);

        return view;
    }
}
