package thigk2.nguyenngochungthien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn1 = findViewById(R.id.btn1);
    Button btn2 = findViewById(R.id.btn2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn1.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent1);
        });
        btn2.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent2);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}