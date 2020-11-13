package com.example.smtrick.electionappuser.Views.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Models.MemberVO;
import com.example.smtrick.electionappuser.Models.PostVO;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.LeedRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.Impl.UserRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.preferences.AppSharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.ViewHolder> {

    private Context context;
    private List<PostVO> list;
    private static int flag = 0;
    private FirebaseAuth firebaseAuth;

    UserRepository userRepository;
    LeedRepository leedRepository;
    AppSharedPreference appSharedPreference;
    String item;
    ArrayList<String> Likes;


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


        final PostVO postVO = list.get(position);

        userRepository = new UserRepositoryImpl();
        leedRepository = new LeedRepositoryImpl();
        appSharedPreference = new AppSharedPreference(holder.cardView.getContext());
        Likes = new ArrayList<>();

        holder.txtUsername.setText(postVO.getPostName());
        holder.txtDays.setText("");
        holder.txtDescription.setText(postVO.getPostDetails());

        if (postVO.getLikes() != null) {
            Likes = (ArrayList<String>) postVO.getLikes();
        }

        Glide.with(context).load(postVO.getPostImage()).placeholder(R.drawable.loading).into(holder.imgPost);

        if (postVO.getLikes() != null) {
            if (postVO.getLikes().contains(appSharedPreference.getEmaiId())) {
                flag = 1;
                holder.imgLike.setImageResource(R.drawable.heart_blue);
                holder.txtLike.setTextColor(ContextCompat.getColor(context, R.color.like));

            } else {
                flag = 0;
                holder.imgLike.setImageResource(R.drawable.heart);
                holder.txtLike.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

            }
        }

        holder.layoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {

                    flag = 1;
                    holder.imgLike.setImageResource(R.drawable.heart_blue);
                    holder.txtLike.setTextColor(ContextCompat.getColor(context, R.color.like));
                    Likes.add(appSharedPreference.getEmaiId());
                    UpdatePost(postVO);

                } else if (flag == 1) {
                    flag = 0;
                    holder.imgLike.setImageResource(R.drawable.heart);
                    holder.txtLike.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    int index = Likes.indexOf(appSharedPreference.getEmaiId());
                    Likes.remove(index);
                    UpdatePost(postVO);
                }
            }
        });


    }

    private void UpdatePost(PostVO postVO) {
        postVO.setLikes(Likes);
        updateLeed(postVO.getPostId(), postVO.getLeedStatusMap());
    }

    private void updateLeed(String postId, Map leedStatusMap) {
        leedRepository.updatePost(postId, leedStatusMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Object object) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        private TextView txtUsername, txtDays, txtDescription, txtLike, txtShare;
        private ImageView imgPost, imgLike, imgShare;
        private LinearLayout layoutLike, layoutShare;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtDays = itemView.findViewById(R.id.txtPostdays);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtLike = itemView.findViewById(R.id.txtLike);

            imgPost = itemView.findViewById(R.id.imgPost);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgShare = itemView.findViewById(R.id.imgShare);

            layoutLike = itemView.findViewById(R.id.layoutLike);
            layoutShare = itemView.findViewById(R.id.layoutShare);

        }
    }

    public void reload(ArrayList<PostVO> leedsModelArrayList) {
        list.clear();
        list.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
