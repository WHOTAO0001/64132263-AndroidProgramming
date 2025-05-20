package till.edu.cuoiky2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// SplashActivity được hiển thị trong một thời gian ngắn trước hoạt động chính.
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Bật hiển thị tràn viền.
        setContentView(R.layout.activity_splash); // Đặt bố cục nội dung thành activity_splash.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar(); // Lấy thanh hành động.
        if (actionBar != null) { // Kiểm tra xem thanh hành động có tồn tại không.
            actionBar.hide(); // Ẩn thanh hành động cho màn hình chờ.
        }


        Handler handler = new Handler(); // Tạo một Handler mới.
        // Đăng một runnable bị trì hoãn để bắt đầu MainActivity sau một khoảng thời gian trễ.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Bắt đầu MainActivity.
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish(); // Kết thúc SplashActivity để không thể truy cập được thông qua nút quay lại.
            }
        }, 4000); // Trì hoãn 4000 mili giây (4 giây).
    }
}