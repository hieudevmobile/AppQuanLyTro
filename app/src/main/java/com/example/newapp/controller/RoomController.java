package com.example.newapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.newapp.model.Room;

public class RoomController {
    private List<Room> roomList;

    public RoomController() {
        this.roomList = new ArrayList<>();
    }

    // CREATE - Thêm phòng mới
    public boolean addRoom(Room room) {
        if (isRoomIdExists(room.getId())) {
            return false;
        }
        return roomList.add(room);
    }

    // READ - Lấy danh sách tất cả phòng
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomList);
    }

    // READ - Lấy phòng theo ID
    public Room getRoomById(String id) {
        for (Room room : roomList) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    // UPDATE - Cập nhật thông tin phòng
    public boolean updateRoom(String oldId, Room newRoom) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(oldId)) {
                roomList.set(i, newRoom);
                return true;
            }
        }
        return false;
    }

    // DELETE - Xóa phòng
    public boolean deleteRoom(String id) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(id)) {
                roomList.remove(i);
                return true;
            }
        }
        return false;
    }

    // DELETE - Xóa phòng theo vị trí
    public boolean deleteRoomByPosition(int position) {
        if (position >= 0 && position < roomList.size()) {
            roomList.remove(position);
            return true;
        }
        return false;
    }

    // Kiểm tra ID phòng đã tồn tại
    public boolean isRoomIdExists(String id) {
        for (Room room : roomList) {
            if (room.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Lấy số lượng phòng
    public int getRoomCount() {
        return roomList.size();
    }

    // Lấy danh sách phòng còn trống
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : roomList) {
            if (room.getStatus().equals("Còn trống")) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Lấy danh sách phòng đã thuê
    public List<Room> getRentedRooms() {
        List<Room> rentedRooms = new ArrayList<>();
        for (Room room : roomList) {
            if (room.getStatus().equals("Đã thuê")) {
                rentedRooms.add(room);
            }
        }
        return rentedRooms;
    }
}
