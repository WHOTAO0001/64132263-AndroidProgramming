package till.edu.cuoiky2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// AddNewTask là một BottomSheetDialogFragment được sử dụng để thêm các tác vụ mới hoặc chỉnh sửa các tác vụ hiện có.
public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask"; // Thẻ (tag) để ghi nhật ký và quản lý fragment.

    private TextView setDueDate; // TextView để hiển thị và đặt ngày đến hạn.
    private EditText mTaskEdit; // EditText để nhập mô tả tác vụ.
    private Button mSaveBtn; // Nút để lưu tác vụ.
    private FirebaseFirestore firestore; // Thể hiện Firestore cho các thao tác cơ sở dữ liệu.
    private Context context; // Ngữ cảnh (context) của hoạt động (activity).
    private String dueDate = ""; // Lưu trữ ngày đến hạn đã chọn.
    private String id = ""; // Lưu trữ ID của tác vụ nếu nó đang được cập nhật.
    private String dueDateUpdate = ""; // Lưu trữ ngày đến hạn của tác vụ nếu nó đang được cập nhật.

    // Phương thức tạo tĩnh để tạo một thể hiện mới của AddNewTask.
    public static AddNewTask newInstance(){
        return new AddNewTask();

    }

    @Nullable
    @Override
    // Khởi tạo bố cục cho hộp thoại bottom sheet.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);

    }

    @Override
    // Được gọi sau khi view được tạo, nơi các phần tử UI được khởi tạo và các trình nghe được thiết lập.
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv); // Khởi tạo TextView để đặt ngày đến hạn.
        mTaskEdit = view.findViewById(R.id.task_edittext); // Khởi tạo EditText cho mô tả tác vụ.
        mSaveBtn = view.findViewById(R.id.save_btn); // Khởi tạo nút Lưu.

        firestore = FirebaseFirestore.getInstance(); // Lấy thể hiện của FirebaseFirestore.

        boolean isUpdate = false; // Cờ để kiểm tra xem tác vụ có đang được cập nhật hay không.

        final Bundle bundle = getArguments(); // Lấy các đối số được truyền cho fragment.
        if(bundle != null){ // Kiểm tra xem các đối số có tồn tại không, cho biết một hoạt động cập nhật.
            isUpdate = true; // Đặt cờ cập nhật thành true.
            String task = bundle.getString("task"); // Lấy mô tả tác vụ từ bundle.
            id = bundle.getString("id"); // Lấy ID tác vụ từ bundle.
            dueDateUpdate = bundle.getString("due"); // Lấy ngày đến hạn từ bundle.

            mTaskEdit.setText(task); // Đặt mô tả tác vụ vào EditText.
            setDueDate.setText(dueDateUpdate); // Đặt ngày đến hạn vào TextView.

            if(task.length() > 0){ // Vô hiệu hóa nút lưu nếu độ dài tác vụ lớn hơn 0 trong quá trình cập nhật.
                mSaveBtn.setEnabled(false); // Vô hiệu hóa nút lưu.
                mSaveBtn.setBackgroundColor(Color.GRAY); // Đặt màu nền thành màu xám.
            }
        }

        // Thêm TextWatcher vào EditText tác vụ để bật/tắt nút lưu.
        mTaskEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không được sử dụng.
            }

            @Override
            // Được gọi khi văn bản trong EditText thay đổi.
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){ // Nếu văn bản tác vụ trống.
                    mSaveBtn.setEnabled(false); // Vô hiệu hóa nút lưu.
                    mSaveBtn.setBackgroundColor(Color.GRAY); // Đặt nền nút thành màu xám.
                }else{ // Nếu văn bản tác vụ không trống.
                    mSaveBtn.setEnabled(true); // Bật nút lưu.
                    mSaveBtn.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark)); // Đặt nền nút thành màu cam.
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không được sử dụng.
            }
        });

        // Đặt OnClickListener cho TextView setDueDate để hiển thị DatePickerDialog.
        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(); // Lấy thể hiện lịch hiện tại.

                int MONTH = calendar.get(Calendar.MONTH); // Lấy tháng hiện tại.
                int YEAR = calendar.get(Calendar.YEAR); // Lấy năm hiện tại.
                int DAY = calendar.get(Calendar.DATE); // Lấy ngày hiện tại.

                // Tạo và hiển thị DatePickerDialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    // Được gọi khi một ngày được chọn trong hộp thoại.
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1; // Điều chỉnh tháng thành chỉ số 1.
                        setDueDate.setText(dayOfMonth + "/" + month + "/" + year); // Đặt ngày đã chọn vào TextView.
                        dueDate = dayOfMonth + "/" + month + "/" + year; // Lưu trữ ngày đã chọn.

                    }
                }, YEAR, MONTH, DAY);

                datePickerDialog.show(); // Hiển thị hộp thoại chọn ngày.
            }
        });

        boolean finalIsUpdate = isUpdate; // Biến cuối cùng để sử dụng trong lớp nội bộ.
        // Đặt OnClickListener cho nút lưu.
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = mTaskEdit.getText().toString(); // Lấy mô tả tác vụ từ EditText.

                if(finalIsUpdate){ // Nếu là hoạt động cập nhật.
                    // Cập nhật tác vụ hiện có trong Firestore.
                    firestore.collection("task").document(id).update("task", task, "due", dueDate);
                    Toast.makeText(context, "Tác vụ đã được cập nhật", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo thành công.
                }else { // Nếu là tác vụ mới.
                    if (task.isEmpty()) { // Nếu mô tả tác vụ trống.
                        Toast.makeText(context, "Không thể để trống", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lỗi.
                    } else {

                        Map<String, Object> taskMap = new HashMap<>(); // Tạo HashMap để lưu trữ dữ liệu tác vụ.

                        taskMap.put("task", task); // Đặt mô tả tác vụ vào bản đồ.
                        taskMap.put("due", dueDate); // Đặt ngày đến hạn vào bản đồ.
                        taskMap.put("status", 0); // Đặt trạng thái ban đầu là 0 (chưa chọn).
                        taskMap.put("time", FieldValue.serverTimestamp()); // Thêm dấu thời gian máy chủ.

                        // Thêm tác vụ mới vào bộ sưu tập "task" trong Firestore.
                        firestore.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            // Được gọi khi hoạt động thêm hoàn tất.
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) { // Nếu hoạt động thành công.
                                    Toast.makeText(context, "Tác vụ đã được lưu", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo thành công.
                                } else { // Nếu hoạt động thất bại.
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lỗi.
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            // Được gọi nếu hoạt động thêm thất bại.
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lỗi.
                            }
                        });
                    }
                }
                dismiss(); // Đóng hộp thoại bottom sheet.
            }
        });
    }

    @Override
    // Được gọi khi fragment được đính kèm vào một hoạt động, được sử dụng để lấy ngữ cảnh.
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context; // Gán ngữ cảnh được đính kèm cho biến ngữ cảnh cục bộ.
    }

    @Override
    // Được gọi khi hộp thoại bị đóng, thông báo cho trình nghe.
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity(); // Lấy hoạt động máy chủ.
        if(activity instanceof OnDialogCloseListener){ // Kiểm tra xem hoạt động có triển khai OnDialogCloseListener không.
            ((OnDialogCloseListener)activity).onDialogClose(dialog); // Ép kiểu và gọi phương thức onDialogClose.
        }
    }
}