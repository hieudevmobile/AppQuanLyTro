package main.java.com.example;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_covananh.Model.Room;

public class AddEditRoomActivity extends AppCompatActivity {

    private EditText etRoomId, etRoomName, etPrice, etTenantName, etPhoneNumber;
    private Spinner spinnerStatus;
    private Button btnSave, btnCancel;
    private Room roomToEdit;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        initializeViews();
        setupSpinner();
        handleIntent();

        btnSave.setOnClickListener(v -> saveRoom());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        etRoomId = findViewById(R.id.et_room_id);
        etRoomName = findViewById(R.id.et_room_name);
        etPrice = findViewById(R.id.et_price);
        spinnerStatus = findViewById(R.id.spinner_status);
        etTenantName = findViewById(R.id.et_tenant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void setupSpinner() {
        String[] statusArray = {"Còn trống", "Đã thuê"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statusArray
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("room")) {
            roomToEdit = (Room) intent.getSerializableExtra("room");
            isEdit = true;
            populateFields();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Sửa Phòng Trọ");
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Thêm Phòng Trọ");
            }
        }
    }

    private void populateFields() {
        if (roomToEdit != null) {
            etRoomId.setText(roomToEdit.getId());
            etRoomId.setEnabled(false); // Không cho sửa mã phòng
            etRoomName.setText(roomToEdit.getName());
            etPrice.setText(String.valueOf(roomToEdit.getPrice()));
            etTenantName.setText(roomToEdit.getTenantName());
            etPhoneNumber.setText(roomToEdit.getPhoneNumber());

            // Set spinner status
            String status = roomToEdit.getStatus();
if (status.equals("Còn trống")) {
                spinnerStatus.setSelection(0);
            } else {
                spinnerStatus.setSelection(1);
            }
        }
    }

    private void saveRoom() {
        String roomId = etRoomId.getText().toString().trim();
        String roomName = etRoomName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String tenantName = etTenantName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        // Validation
        if (roomId.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã phòng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (roomName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên phòng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá thuê", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);

            Room room = new Room(roomId, roomName, price, status, tenantName, phoneNumber);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("room", room);
            resultIntent.putExtra("isEdit", isEdit);
            if (isEdit && roomToEdit != null) {
                resultIntent.putExtra("oldRoomId", roomToEdit.getId());
            }
            setResult(RESULT_OK, resultIntent);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá thuê không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}