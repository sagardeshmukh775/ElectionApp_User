package com.example.smtrick.electionappuser.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smtrick.electionappuser.Models.MemberVO;
import com.example.smtrick.electionappuser.Models.PostVO;
import com.example.smtrick.electionappuser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.ViewHolder> {

    private Context context;
    private List<PostVO> list;
//    String Language;


    String item;


    public Post_Adapter(Context context, List<PostVO> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public Post_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_adapter_layout, parent, false);
        Post_Adapter.ViewHolder viewHolder = new Post_Adapter.ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Post_Adapter.ViewHolder holder, final int position) {

//        if (Language.equalsIgnoreCase("Marathi")) {
//
//        }
        final PostVO postVO = list.get(position);


        holder.txtUsername.setText(postVO.getPostName());
        holder.txtDays.setText("");
        holder.txtDescription.setText(postVO.getPostDetails());

        Glide.with(context).load(postVO.getPostImage()).placeholder(R.drawable.loading).into(holder.imgPost);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        private TextView txtUsername,txtDays,txtDescription;
        private ImageView imgPost,imgLike,imgShare;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card);

            txtUsername =  itemView.findViewById(R.id.txtUsername);
            txtDays =  itemView.findViewById(R.id.txtPostdays);
            txtDescription =  itemView.findViewById(R.id.txtDescription);

            imgPost =  itemView.findViewById(R.id.imgPost);
            imgLike =  itemView.findViewById(R.id.imgLike);
            imgShare =  itemView.findViewById(R.id.imgShare);

        }
    }

    public void reload(ArrayList<PostVO> leedsModelArrayList) {
        list.clear();
        list.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
