package com.example.postcovidtransport.about;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postcovidtransport.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<aboutmodel> titlename;
    onClick onClick;

    public Adapter(ArrayList<aboutmodel> titlename, onClick onClick) {
        this.titlename = titlename;
        this.onClick = onClick;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.itemView.setTag(titlename.get(position));
      //  Log.e("a",position+" ");
        //Log.e("a",titlename.get(position).getTitle()+" ");
        holder.title.setText(titlename.get(position).getTitle());
    holder.imageView.setImageResource(titlename.get(position).getImageid());

    }

    @Override
    public int getItemCount() {
        return titlename.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView imageView;
        onClick onClick;
        public ViewHolder(@NonNull View itemView,onClick onClick) {
            super(itemView);
            title = itemView.findViewById(R.id.titletxt);
            imageView = itemView.findViewById(R.id.imageabout);
            this.onClick = onClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        onClick.onClick(getAdapterPosition());
        }
    }
    public interface onClick{
        void onClick(int position);
    }

}
