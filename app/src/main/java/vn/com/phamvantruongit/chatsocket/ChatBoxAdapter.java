package vn.com.phamvantruongit.chatsocket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {
    private List<Message> messageList;

    public ChatBoxAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
       myViewHolder.txtMessage.setText(messageList.get(position).getMessage());
       myViewHolder.txtNickName.setText(messageList.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNickName,txtMessage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNickName=itemView.findViewById(R.id.txtNickName);
            txtMessage=itemView.findViewById(R.id.txtMessage);
        }
    }
}
