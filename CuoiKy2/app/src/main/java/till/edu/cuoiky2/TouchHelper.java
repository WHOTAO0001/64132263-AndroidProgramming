package till.edu.cuoiky2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import till.edu.cuoiky2.Adapter.ToDoAdapter;

// TouchHelper cung cấp chức năng vuốt để xóa và vuốt để chỉnh sửa cho các mục RecyclerView.
public class TouchHelper extends ItemTouchHelper.SimpleCallback {
    private ToDoAdapter adapter; // Bộ điều hợp cho RecyclerView.
    public TouchHelper(ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT); // Cho phép vuốt sang trái và phải.
        this.adapter = adapter; // Khởi tạo bộ điều hợp.
    }

    @Override
    // Không được sử dụng cho chức năng kéo và thả.
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    // Được gọi khi một mục được vuốt.
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition(); // Lấy vị trí của mục đã vuốt.
        if(direction == ItemTouchHelper.RIGHT){ // Nếu vuốt sang phải (để xóa).
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext()); // Tạo một trình xây dựng AlertDialog.
            builder.setMessage("Bạn chắc chứ") // Đặt thông báo cho hộp thoại.
                    .setTitle("Xóa tác vụ") // Đặt tiêu đề của hộp thoại.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() { // Đặt nút tích cực (Có).
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteTask(position); // Gọi phương thức deleteTask trong bộ điều hợp.
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() { // Đặt nút tiêu cực (Không).
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.notifyItemChanged(position); // Thông báo cho bộ điều hợp rằng mục đã thay đổi (để hoàn nguyên thao tác vuốt).
                        }
                    });

            AlertDialog dialog = builder.create(); // Tạo AlertDialog.
            dialog.show(); // Hiển thị AlertDialog.
        }else { // Nếu vuốt sang trái (để chỉnh sửa).
            adapter.editTask(position); // Gọi phương thức editTask trong bộ điều hợp.
        }
    }

    @Override
    // Vẽ nền vuốt và các biểu tượng.
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // Sử dụng RecyclerViewSwipeDecorator để thêm các hình ảnh vuốt tùy chỉnh.
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightActionIcon(R.drawable.delete) // Thêm biểu tượng xóa cho vuốt phải.
                .addSwipeRightBackgroundColor(Color.RED) // Đặt nền màu đỏ cho vuốt phải.
                .addSwipeLeftActionIcon(R.drawable.edit) // Thêm biểu tượng chỉnh sửa cho vuốt trái.
                .addSwipeLeftBackgroundColor(Color.GREEN) // Đặt nền màu xanh lá cây cho vuốt trái.
                .create()
                .decorate(); // Áp dụng các trang trí.
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}