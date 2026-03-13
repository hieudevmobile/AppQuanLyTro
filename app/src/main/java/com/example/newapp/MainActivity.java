package com.example.newapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newapp.Adapter.RoomAdapter;
import com.example.app_covananh.Controller.RoomController;
import com.example.app_covananh.Model.Room;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvRooms;
    private Button btnAddRoom;
    private RoomAdapter roomAdapter;
    private RoomController roomController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initializeViews() {
        rcvRooms = findViewById(R.id.rcv_rooms);
        btnAddRoom = findViewById(R.id.btn_add_room);
        roomController = new RoomController();
    }

    private void setupRecyclerView() {
        roomAdapter = new RoomAdapter(this, roomController.getAllRooms());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvRooms.setLayoutManager(layoutManager);
        rcvRooms.setAdapter(roomAdapter);

        // Item click listener - Edit
        roomAdapter.setOnItemClickListener((room, position) -> {
            Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
            intent.putExtra("room", room);
            startActivityForResult(intent, 1);
        });

        // Item long click listener - Delete
        roomAdapter.setOnItemLongClickListener((room, position) -> {
            showDeleteConfirmDialog(room, position);
        });
    }

    private void setupListeners() {
        btnAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Room room = (Room) data.getSerializableExtra("room");
            boolean isEdit = data.getBooleanExtra("isEdit", false);

            if (!isEdit) {
                // Add new room - use controller
                if (!roomController.addRoom(room)) {
                    Toast.makeText(this, "Mã phòng đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
            } else {
                // Update room - use controller
                String oldRoomId = data.getStringExtra("oldRoomId");
                if (roomController.updateRoom(oldRoomId, room)) {
                    Toast.makeText(this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
                }
            }
            roomAdapter.updateList(roomController.getAllRooms());
        }
    }

    private void showDeleteConfirmDialog(Room room, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng: " + room.getName() + " ?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomController.deleteRoomByPosition(position);
                    roomAdapter.updateList(roomController.getAllRooms());
                    Toast.makeText(MainActivity.this, "Xóa phòng thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }
}