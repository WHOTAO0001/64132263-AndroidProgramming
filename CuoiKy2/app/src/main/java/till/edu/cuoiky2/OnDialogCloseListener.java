package till.edu.cuoiky2;

import android.content.DialogInterface;

// Giao diện để lắng nghe các sự kiện đóng hộp thoại.
public interface OnDialogCloseListener {

    // Phương thức sẽ được gọi khi một hộp thoại bị đóng.
    void onDialogClose(DialogInterface dialogInterface);
}