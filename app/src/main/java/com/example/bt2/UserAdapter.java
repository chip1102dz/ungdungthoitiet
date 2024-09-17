package com.example.bt2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> mList;
    private OnItemClickListener mListener;

    public UserAdapter(List<User> mList) {
        this.mList = mList;
    }

    public void getData(List<User> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mList.get(position);
        if (user == null) return;

        holder.tvNgaythang.setText(user.getNgayThang());
        holder.tvNhietdo.setText(user.getNhietDo());
        holder.tvDoAm.setText(user.getDoAm());

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgaythang;
        TextView tvNhietdo;
        TextView tvDoAm;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgaythang = itemView.findViewById(R.id.tv_ngaythang);
            tvNhietdo = itemView.findViewById(R.id.tv_nhietdo);
            tvDoAm = itemView.findViewById(R.id.tv_doam);
        }
    }
}
