package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRcView extends RecyclerView.Adapter<AdapterRcView.MyViewHolder> {

    private final Context context;
    private final List<Note> noteList;
    private final DataBaseHelper db;
    public AdapterRcView(Context context, List<Note> noteList, DataBaseHelper db){
        this.context = context;
        this.noteList = noteList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_rcview,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTittle.setText(noteList.get(position).getTittle());
        holder.tvDes.setText(noteList.get(position).getDescription());

        holder.cardView.setOnLongClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = View.inflate(context,R.layout.custom_dialog,null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);//khong dung back
            dialog.setCanceledOnTouchOutside(false);//ngan chan cham sai
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = 100;
            dialog.getWindow().setAttributes(layoutParams);

            Button btnLogYes = view.findViewById(R.id.btn_yes);
            Button btnLohNo = view.findViewById(R.id.btn_no);

            btnLogYes.setOnClickListener(clickYes ->{
                int currPos = holder.getAdapterPosition();
                if(currPos != RecyclerView.NO_POSITION){
                    deleteNote(currPos);
                }
                dialog.dismiss();
            });
            btnLohNo.setOnClickListener(clickNo -> dialog.dismiss());
            dialog.show();
            return true;
        });

        Note note = noteList.get(holder.getAdapterPosition());

        //item nao co do yeu thich = 1 _> star up
        if(note.getLoveStar() == 1){
            holder.iv_star.setImageResource(R.drawable.ic_star_fill);
        }

        holder.iv_star.setOnClickListener(v ->{
            if(note.getLoveStar() == 0){
                holder.iv_star.setImageResource(R.drawable.ic_star_fill);
                String id = String.valueOf(note.getId());
                db.updateData(id,note.getTittle(),note.getDescription(),1);
            }
            else{
                holder.iv_star.setImageResource(R.drawable.iv_star);
                String id = String.valueOf(note.getId());
                db.updateData(id,note.getTittle(),note.getDescription(),0);
            }
        });

        holder.cardView.setOnClickListener(v ->{
            String title = note.getTittle();
            String des = note.getDescription();
            int id = note.getId();
            String date = note.getDate();
            int loveStar = note.getLoveStar();
            Intent intent = new Intent(context, ActivityDetail.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("NOTE",new Note(id,title,des,date,loveStar));
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    private void deleteNote(int pos){
        int idNote = noteList.get(pos).getId();
        db.deleteNote(idNote);///xoa trong database
        noteList.remove(pos);///xoa trong list display
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return (!noteList.isEmpty()) ? noteList.size() : 0;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTittle,tvDes;
        private CardView cardView;
        private CircleImageView iv_star;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTitleItem);
            tvDes = itemView.findViewById(R.id.tvDesItem);
            cardView = itemView.findViewById(R.id.cardViewItem);
            iv_star = itemView.findViewById(R.id.ivStar);
        }
    }
}
