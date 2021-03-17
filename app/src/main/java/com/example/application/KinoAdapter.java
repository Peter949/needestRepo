package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.api.KinoParam;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KinoAdapter extends RecyclerView.Adapter<KinoAdapter.ViewHolder>
{
    LinearLayout linearLayout;
    private final static String PHOTO_URL = "http://cinema.areas.su/up/images/";
    private List<KinoParam> mKinos;
    private Context mContext;

    public KinoAdapter(List<KinoParam> kinos)
    {
        this.mKinos = kinos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        KinoParam kinoParam = mKinos.get(position);
        holder.kinoText.setText(kinoParam.getName());
        Picasso.with(mContext).load(PHOTO_URL + kinoParam.getPoster()).resize(200, 150).into(holder.kinoImage);

        linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(mKinos == null)
        {
            return 0;
        }
        return mKinos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView kinoText;
        ImageView kinoImage;
        ViewHolder(View itemView)
        {
            super(itemView);
            kinoText = itemView.findViewById(R.id.nameTextView);
            kinoImage = itemView.findViewById(R.id.itemImageView);
        }
    }
}
