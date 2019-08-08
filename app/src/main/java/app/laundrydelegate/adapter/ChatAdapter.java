package app.laundrydelegate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.R;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.databinding.SendItemBinding;
import app.laundrydelegate.models.chat.Message;
import app.laundrydelegate.viewModels.ChatItemViewModels;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<Message> messages;
    Context context;

    public ChatAdapter() {
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Message details = messages.get(position);
        final ChatItemViewModels itemViewModel = new ChatItemViewModels(details);
        if (messages.get(position).getSentFrom().equals("delegate"))
            holder.itemBinding.sendContainer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        else
            holder.itemBinding.sendContainer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SendItemBinding itemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (itemBinding == null) {
                itemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (itemBinding != null) {
                itemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(ChatItemViewModels viewModel) {
            if (itemBinding != null) {
                itemBinding.setChatItemViewModel(viewModel);
            }
        }
    }
}
