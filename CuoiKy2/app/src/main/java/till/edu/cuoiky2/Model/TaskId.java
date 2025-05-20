package till.edu.cuoiky2.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

// TaskId là một lớp cơ sở để bao gồm ID tài liệu từ Firestore.
public class TaskId {
    @Exclude // Loại trừ trường này khỏi việc ánh xạ trực tiếp tới các trường tài liệu Firestore.
    public String TaskId; // Lưu trữ ID tài liệu.

    // Phương thức chung để đặt ID tài liệu cho đối tượng.
    public  <T extends TaskId> T withId(@NonNull final String id){
        this.TaskId = id; // Đặt TaskId.
        return (T) this; // Trả về đối tượng hiện tại để liên kết.
    }

}