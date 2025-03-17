package till.edu.ex3tinhtong;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Gan layout tuong ung oi file nay
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //day la bo lang nghe va xu ly su kien click tren nut tinh tong
    public void XuLyCong(View view){
        //tim, tham chieu den dieu khien tren tep XML, mapping sang Java file
        EditText editTextsoA = findViewById(R.id.edtA);
        EditText editTextsoB = findViewById(R.id.edtB);
        EditText editTextKetQua = findViewById(R.id.edtKQ);
        //Lay du lieu ve o dieu khien so A
        String strA= editTextsoA.getText().toString();      //strA="2"
        //Lay du lieu ve o dieu khien so B
        String strB= editTextsoB.getText().toString();      //strB="4"
        //Chuyen du lieu sang dang so
        int so_A= Integer.parseInt(strA);   //2
        int so_B= Integer.parseInt(strB);   //4
        //Tinh toan theo yeu cau
        int tong = so_A + so_B;     //6
        String strTong = String.valueOf(tong);      //Chuyen sang dang chuoi "6"
        //Hien ra man hinh
        editTextKetQua.setText(strTong);
    }
}