package till.edu.ex5_addsubmuldiv_anynomous;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Khai bao cac doi tuong gan voi dieu khien tuong ung o day
    EditText editTextSo1;
    EditText editTextSo2;
    EditText editTextKQ;
    Button nutCong, nutTru, nutNhan, nutChia;
    void TimDieuKhien(){
        editTextSo1 = (EditText)findViewById(R.id.edtso1);
        editTextSo2 = (EditText)findViewById(R.id.edtso2);
        editTextKQ = (EditText)findViewById(R.id.edtKQ);
        nutCong = (Button)findViewById(R.id.btnCong);
        nutTru = (Button)findViewById(R.id.btnTru);
        nutNhan = (Button)findViewById(R.id.btnNhan);
        nutChia = (Button)findViewById(R.id.btnChia);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TimDieuKhien();
        //Gan bo lang nghe su kien va code xu ly cho tung nut
        View.OnClickListener bolangngheCong = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly cong o day
                //tim, tham chieu den dieu khien tren tep XML, mapping sang Java file
                EditText editTextsoA = findViewById(R.id.edtso1);
                EditText editTextsoB = findViewById(R.id.edtso2);
                EditText editTextKetQua = findViewById(R.id.edtKQ);
                //Lay du lieu ve o dieu khien so 1 va 2
                String strA= editTextsoA.getText().toString();      //strA="2"
                String strB= editTextsoB.getText().toString();      //strB="4"
                //Chuyen du lieu sang dang so
                float so_A= Float.parseFloat(strA);   //2
                float so_B= Float.parseFloat(strB);   //4
                //Tinh toan theo yeu cau
                float tong = so_A + so_B;     //6
                String strTong = String.valueOf(tong);      //Chuyen sang dang chuoi "6"
                //Hien ra man hinh
                editTextKetQua.setText(strTong);
            }
        };
        nutCong.setOnClickListener(bolangngheCong);
        nutTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly tru
                //tim, tham chieu den dieu khien tren tep XML, mapping sang Java file
                EditText editTextsoA = findViewById(R.id.edtso1);
                EditText editTextsoB = findViewById(R.id.edtso2);
                EditText editTextKetQua = findViewById(R.id.edtKQ);
                //Lay du lieu ve o dieu khien so 1 va 2
                String strA= editTextsoA.getText().toString();      //strA="2"
                String strB= editTextsoB.getText().toString();      //strB="4"
                //Chuyen du lieu sang dang so
                float so_A= Float.parseFloat(strA);   //2
                float so_B= Float.parseFloat(strB);   //4
                //Tinh toan theo yeu cau
                float tong = so_A - so_B;     //6
                String strTru = String.valueOf(tong);      //Chuyen sang dang chuoi "6"
                //Hien ra man hinh
                editTextKetQua.setText(strTru);
            }
        });
        nutNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly nhan
                //tim, tham chieu den dieu khien tren tep XML, mapping sang Java file
                EditText editTextsoA = findViewById(R.id.edtso1);
                EditText editTextsoB = findViewById(R.id.edtso2);
                EditText editTextKetQua = findViewById(R.id.edtKQ);
                //Lay du lieu ve o dieu khien so 1 va 2
                String strA= editTextsoA.getText().toString();      //strA="2"
                String strB= editTextsoB.getText().toString();      //strB="4"
                //Chuyen du lieu sang dang so
                float so_A= Float.parseFloat(strA);   //2
                float so_B= Float.parseFloat(strB);   //4
                //Tinh toan theo yeu cau
                float tong = so_A * so_B;     //6
                String strNhan = String.valueOf(tong);      //Chuyen sang dang chuoi "6"
                //Hien ra man hinh
                editTextKetQua.setText(strNhan);
            }
        });
        nutChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly Chia
                //tim, tham chieu den dieu khien tren tep XML, mapping sang Java file
                EditText editTextsoA = findViewById(R.id.edtso1);
                EditText editTextsoB = findViewById(R.id.edtso2);
                EditText editTextKetQua = findViewById(R.id.edtKQ);
                //Lay du lieu ve o dieu khien so 1 va 2
                String strA= editTextsoA.getText().toString();      //strA="2"
                String strB= editTextsoB.getText().toString();      //strB="4"
                //Chuyen du lieu sang dang so
                float so_A= Float.parseFloat(strA);   //2
                float so_B= Float.parseFloat(strB);   //4
                //Tinh toan theo yeu cau
                float tong = so_A / so_B;     //6
                String strChia = String.valueOf(tong);      //Chuyen sang dang chuoi "6"
                //Hien ra man hinh
                editTextKetQua.setText(strChia);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}