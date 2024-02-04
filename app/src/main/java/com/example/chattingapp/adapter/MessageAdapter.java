package com.example.chattingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MessageList> mMessageList;
    private Context context;
    private CircleImageView circleImageView;
    private TextView tvName;
    private TextView tvMessage;


    public MessageAdapter(List<MessageList> mMessageList, Context context) {
        this.mMessageList = mMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mMessageList != null) {
            return mMessageList.size();
        }
        return 0;
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            circleImageView = itemView.findViewById(R.id.civAvatarUser);
        }
    }
}
