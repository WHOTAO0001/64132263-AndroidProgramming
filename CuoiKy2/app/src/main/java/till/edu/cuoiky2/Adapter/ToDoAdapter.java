package till.edu.cuoiky2.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import till.edu.cuoiky2.AddNewTask;
import till.edu.cuoiky2.MainActivity;
import till.edu.cuoiky2.Model.ToDoModel;
import till.edu.cuoiky2.R;

// ToDoAdapter chịu trách nhiệm điền dữ liệu vào RecyclerView với các mục ToDo.
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    // Phần 1: Biến thành viên
    private List<ToDoModel> toDoList; // Danh sách các đối tượng ToDoModel để hiển thị.
    private MainActivity activity; // Tham chiếu đến MainActivity.
    private FirebaseFirestore firestore; // Thể hiện FirebaseFirestore cho các thao tác cơ sở dữ liệu.

    // Phần 2: Hàm tạo
    public ToDoAdapter(MainActivity mainActivity, List<ToDoModel> toDoList){
        this.toDoList = toDoList; // Khởi tạo danh sách ToDo.
        activity = mainActivity; // Khởi tạo tham chiếu MainActivity.
    }

    // Phần 3: onCreateViewHolder
    @NonNull
    @Override
    // Tạo và trả về một ViewHolder cho mỗi mục trong RecyclerView.
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khởi tạo bố cục cho một mục tác vụ duy nhất.
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false);
        firestore = FirebaseFirestore.getInstance(); // Lấy thể hiện của FirebaseFirestore.

        return new MyViewHolder(view); // Trả về một MyViewHolder mới.
    }

    // Phần 4: Các phương thức trợ giúp cho các thao tác tác vụ
    // Xóa một tác vụ khỏi Firestore và cập nhật RecyclerView.
    public void deleteTask(int position){
        ToDoModel toDoModel = toDoList.get(position); // Lấy ToDoModel tại vị trí đã cho.
        firestore.collection("task").document(toDoModel.TaskId).delete(); // Xóa tác vụ khỏi Firestore bằng ID của nó.
        toDoList.remove(position); // Xóa tác vụ khỏi danh sách cục bộ.
        notifyItemRemoved(position); // Thông báo cho bộ điều hợp rằng một mục đã bị xóa.
    }
    // Trả về ngữ cảnh của MainActivity.
    public Context getContext(){
        return activity;
    }
    // Chỉnh sửa một tác vụ hiện có bằng cách mở hộp thoại AddNewTask với chi tiết tác vụ.
    public void editTask(int position){
        ToDoModel toDoModel = toDoList.get(position); // Lấy ToDoModel tại vị trí đã cho.

        Bundle bundle = new Bundle(); // Tạo một Bundle mới để truyền dữ liệu.
        bundle.putString("task", toDoModel.getTask()); // Đặt mô tả tác vụ vào bundle.
        bundle.putString("due", toDoModel.getDue()); // Đặt ngày đến hạn vào bundle.
        bundle.putString("id", toDoModel.TaskId); // Đặt ID tác vụ vào bundle.

        AddNewTask addNewTask = new AddNewTask(); // Tạo một thể hiện mới của AddNewTask.
        addNewTask.setArguments(bundle); // Đặt các đối số cho fragment AddNewTask.
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag()); // Hiển thị hộp thoại AddNewTask.
    }
    @Override
    // Ràng buộc dữ liệu với ViewHolder.
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ToDoModel toDoModel = toDoList.get(position); // Lấy ToDoModel cho vị trí hiện tại.
        holder.mcheckBox.setText(toDoModel.getTask()); // Đặt văn bản tác vụ vào CheckBox.
        holder.mDueDateTv.setText("Due On" + toDoModel.getDue()); // Đặt văn bản ngày đến hạn.

        holder.mcheckBox.setChecked(toBoolean(toDoModel.getStatus())); // Đặt trạng thái đã chọn của CheckBox dựa trên trạng thái tác vụ.

        // Đặt OnCheckedChangeListener cho CheckBox.
        holder.mcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            // Được gọi khi trạng thái đã chọn của CheckBox thay đổi.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){ // Nếu CheckBox được chọn.
                    firestore.collection("task").document(toDoModel.TaskId).update("status", 1); // Cập nhật trạng thái thành 1 (đã chọn) trong Firestore.
                }else { // Nếu CheckBox không được chọn.
                    firestore.collection("task").document(toDoModel.TaskId).update("status", 0); // Cập nhật trạng thái thành 0 (chưa chọn) trong Firestore.
                }
            }
        });
    }

    // Phần 5: Phương thức tiện ích và getItemCount
    // Chuyển đổi trạng thái số nguyên thành boolean.
    private boolean toBoolean(int status){
        return status != 0; // Trả về true nếu trạng thái khác 0, ngược lại là false.
    }

    @Override
    // Trả về tổng số mục trong danh sách.
    public int getItemCount() {
        return toDoList.size();
    }

    // Phần 6: Lớp ViewHolder
    // MyViewHolder chứa view cho mỗi mục ToDo riêng lẻ.
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv; // TextView để hiển thị ngày đến hạn.
        CheckBox mcheckBox; // CheckBox cho trạng thái và mô tả tác vụ.

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date_tv); // Khởi tạo TextView ngày đến hạn.
            mcheckBox = itemView.findViewById(R.id.mcheckbox); // Khởi tạo CheckBox.

        }
    }
}