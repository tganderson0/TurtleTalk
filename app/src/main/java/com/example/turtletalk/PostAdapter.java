package com.example.turtletalk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turtletalk.models.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;

    public PostAdapter(ArrayList<Post> courses){
        this.posts = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        TextView username = holder.itemView.findViewById(R.id.post_user_textView);
        TextView caption = holder.itemView.findViewById(R.id.post_like_count);
        TextView likeCount = holder.itemView.findViewById(R.id.post_caption);
        ImageView image = holder.itemView.findViewById(R.id.post_image);
        ImageButton likeButton = holder.itemView.findViewById(R.id.post_like_button);

        username.setText(post.getUsername());
        caption.setText(post.getCaption());
        image.setImageBitmap(post.getPhotoBitmap());
        String likeText = (post.getLikes() + " likes");
        likeCount.setText(likeText);

        likeButton.setOnClickListener((view) ->{
            System.out.println("The like button was pressed");
            post.addLike();
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
