package till.edu.cuoiky2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import till.edu.cuoiky2.Adapter.ToDoAdapter;
import till.edu.cuoiky2.Model.ToDoModel;

// MainActivity là màn hình chính của ứng dụng, hiển thị danh sách các tác vụ.
public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView recyclerView; // RecyclerView để hiển thị danh sách các mục ToDo.
    private FloatingActionButton mFab; // Nút hành động nổi để thêm tác vụ mới.
    private FirebaseFirestore firestore; // Thể hiện FirebaseFirestore cho các thao tác cơ sở dữ liệu.
    private ToDoAdapter adapter; // Bộ điều hợp (adapter) cho RecyclerView.
    private List<ToDoModel> mList; // Danh sách để chứa các đối tượng ToDoModel.
    private Query query; // Truy vấn Firestore để lấy các tác vụ.
    private ListenerRegistration listenerRegistration; // Đăng ký trình nghe để cập nhật thời gian thực từ Firestore.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Bật hiển thị tràn viền (edge-to-edge).
        setContentView(R.layout.activity_main); // Đặt bố cục nội dung thành activity_main.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerview); // Khởi tạo RecyclerView.
        mFab = findViewById(R.id.floatingActionButton); // Khởi tạo Nút hành động nổi.
        firestore = FirebaseFirestore.getInstance(); // Lấy thể hiện của FirebaseFirestore.

        recyclerView.setHasFixedSize(true); // Tối ưu hóa hiệu suất RecyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); // Đặt LinearLayoutManager cho RecyclerView.

        // Đặt OnClickListener cho Nút hành động nổi.
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại bottom sheet AddNewTask.
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG );
            }
        });

        mList = new ArrayList<>(); // Khởi tạo danh sách các đối tượng ToDoModel.
        adapter = new ToDoAdapter(MainActivity.this, mList); // Khởi tạo ToDoAdapter.

        // Đính kèm ItemTouchHelper để có chức năng vuốt để xóa và vuốt để chỉnh sửa.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter); // Đặt bộ điều hợp cho RecyclerView.
        showData(); // Gọi phương thức để truy xuất và hiển thị dữ liệu.
    }

    // Lấy và hiển thị dữ liệu ToDo từ Firestore trong thời gian thực.
    private void showData(){
        // Tạo một truy vấn để sắp xếp các tác vụ theo "time" theo thứ tự tăng dần.
        query = firestore.collection("tasks").orderBy("time", Query.Direction.ASCENDING);

        // Thêm trình nghe ảnh chụp nhanh thời gian thực vào truy vấn.
        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Lặp lại các thay đổi tài liệu để cập nhật danh sách.
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    if (documentChange.getType() == DocumentChange.Type.ADDED){ // Nếu một tài liệu được thêm vào.
                        String id = documentChange.getDocument().getId(); // Lấy ID tài liệu.
                        // Chuyển đổi tài liệu thành ToDoModel và đặt ID của nó.
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);

                        mList.add(toDoModel); // Thêm ToDoModel mới vào danh sách.
                        adapter.notifyDataSetChanged(); // Thông báo cho bộ điều hợp về thay đổi dữ liệu.
                    }
                }
                listenerRegistration.remove(); // Xóa trình nghe sau khi tải dữ liệu ban đầu.
            }
        });
    }

    @Override
    // Được gọi khi hộp thoại AddNewTask bị đóng, để làm mới danh sách tác vụ.
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear(); // Xóa danh sách hiện có.
        showData(); // Lấy lại và hiển thị dữ liệu đã cập nhật.
        adapter.notifyDataSetChanged(); // Thông báo cho bộ điều hợp rằng tập dữ liệu đã thay đổi.
    }
}