package com.example.smtrick.electionappuser.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtrick.electionappuser.Models.MemberVO;
import com.example.smtrick.electionappuser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.ViewHolder> {

    private Context context;
    private List<MemberVO> list;
    String Language;


    String item;


    public Post_Adapter(Context context, List<MemberVO> list, String language) {
        this.context = context;
        this.list = list;
        this.Language = language;
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

        if (Language.equalsIgnoreCase("Marathi")) {
//            holder.name1.setText(R.string.register_membername);
//            holder.date.setText(R.string.address);
//            holder.cast.setText(R.string.card_contact);
//            holder.contact.setText(R.string.register_wardno);
        }
        final MemberVO pveo = list.get(position);


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

    public void reload(ArrayList<MemberVO> leedsModelArrayList) {
        list.clear();
        list.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
