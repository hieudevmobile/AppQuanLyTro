package main.java.com.example.newapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_covananh.Model.Room;
import com.example.app_covananh.R;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> rooms;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(Room room, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Room room, int position);
    }

    public RoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        Room room = rooms.get(position);

        holder.tvRoomName.setText("Phòng: " + room.getName());
        holder.tvPrice.setText("Giá: " + String.format("%.0f VND", room.getPrice()));
        holder.tvStatus.setText("Tình trạng: " + room.getStatus());
        holder.tvTenantName.setText("Người thuê: " + (room.getTenantName().isEmpty() ? "N/A" : room.getTenantName()));
        holder.tvPhoneNumber.setText("SĐT: " + (room.getPhoneNumber().isEmpty() ? "N/A" : room.getPhoneNumber()));

        // Set background color based on status
        if (room.getStatus().equals("Còn trống")) {
            holder.layoutItem.setBackgroundColor(Color.parseColor("#90EE90")); // Light green
        } else {
            holder.layoutItem.setBackgroundColor(Color.parseColor("#FFB6C6")); // Light red
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(room, position);
            }
        });

        // Long click listener
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(room, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus, tvTenantName, tvPhoneNumber;
        LinearLayout layoutItem;

        public RoomViewHolder(View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tv_room_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvTenantName = itemView.findViewById(R.id.tv_tenant_name);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }

    public void updateList(List<Room> newList) {
        this.rooms = newList;
        notifyDataSetChanged();
    }
}