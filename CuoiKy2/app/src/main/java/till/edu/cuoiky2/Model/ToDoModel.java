package till.edu.cuoiky2.Model;

// ToDoModel đại diện cho một mục ToDo duy nhất, mở rộng TaskId để bao gồm ID tài liệu Firestore của nó.
public class ToDoModel extends TaskId{

    private String task, due; // Lưu trữ mô tả tác vụ và ngày đến hạn.
    private int status; // Lưu trữ trạng thái của tác vụ (ví dụ: 0 cho chưa chọn, 1 cho đã chọn).

    // Getter cho mô tả tác vụ.
    public String getTask() {
        return task;
    }

    // Getter cho ngày đến hạn.
    public String getDue() {
        return due;
    }

    // Getter cho trạng thái tác vụ.
    public int getStatus() {
        return status;
    }
}