package tiil.edu.ex4_addsubmuldiv_onclick;

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
    EditText editTextSo1;
    EditText editTextSo2;
    EditText editTextKQ;
    Button nutCong,nutTru,nutNhan,nutChia

    void TimDieuKhien() {
        EditText editTextSo1=(EditText) findViewById(R.id.edtSo1);
        EditText editTextSo2=(EditText) findViewById(R.id.edtSo2);
        EditText editTextKQ =(EditText)findViewById(R.id.edtKetQua);
        nutCong =(Button) findViewById(R.id.btnCong);
        nutTru =(Button) findViewById(R.id.btnTru);
        nutNhan =(Button) findViewById(R.id.btnNhan);
        nutChia =(Button) findViewById(R.id.btnChia);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TimDieuKhien();
     }


        public void XuLyCong (View view){
            String soThu1=editTextSo1.getText().toString();
            String soThu2=editTextSo2.getText().toString();
            float SoA= Float.parseFloat(soThu1);
            float SoB= Float.parseFloat(soThu2);
            float Tong=SoA + SoB;
            String chuoiKQ = String.valueOf(Tong);
            editTextKQ.setText(chuoiKQ);
        }
        public void XuLyTru (View view){
            String soThu1=editTextSo1.getText().toString();
            String soThu2=editTextSo2.getText().toString();
            float SoA= Float.parseFloat(soThu1);
            float SoB= Float.parseFloat(soThu2);
            float Hieu=SoA - SoB;
            String chuoiKQ = String.valueOf(Hieu);
            editTextKQ.setText(chuoiKQ);
        }
        public void XuLyNhan(View view){
            String soThu1=editTextSo1.getText().toString();
            String soThu2=editTextSo2.getText().toString();
            float SoA= Float.parseFloat(soThu1);
            float SoB= Float.parseFloat(soThu2);
            float Tich=SoA * SoB;
            String chuoiKQ = String.valueOf(Tich);
            editTextKQ.setText(chuoiKQ);
        }
        public void XuLyChia(View view){
            String soThu1=editTextSo1.getText().toString();
            String soThu2=editTextSo2.getText().toString();
            float SoA= Float.parseFloat(soThu1);
            float SoB= Float.parseFloat(soThu2);
            float Thuong=SoA / SoB;
            String chuoiKQ = String.valueOf(Thuong);
            editTextKQ.setText(chuoiKQ);
        }
    }
}