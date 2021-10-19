package com.example.covidapp.Notifications;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidapp.Notifications.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.covidapp.Notifications.NotificationClass;
import com.example.covidapp.databinding.FragmentNotificationsItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNotificationRecyclerViewAdapter extends RecyclerView.Adapter<MyNotificationRecyclerViewAdapter.ViewHolder> {

//    private final List<PlaceholderItem> mValues;
    private final List<NotificationClass> notifications;

    public MyNotificationRecyclerViewAdapter(List<NotificationClass> items) {
        notifications = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentNotificationsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
        holder.notification = notifications.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.message.setText(notifications.get(position).getMessage());
        holder.sender.setText(notifications.get(position).getSender());
        holder.date.setText(notifications.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIdView;
        public final TextView sender;
        public final TextView message;
        public final TextView date;
        public PlaceholderItem mItem;
        public NotificationClass notification;

        public ViewHolder(FragmentNotificationsItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.senderIcon;
            sender = binding.sender;
            message = binding.message;
            date = binding.notificationDate;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + message.getText() + "'";
//        }
    }
}