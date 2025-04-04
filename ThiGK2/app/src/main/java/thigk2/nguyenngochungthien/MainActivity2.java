package thigk2.nguyenngochungthien;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void XuLyTong(View view){
        EditText editTextsoA =findViewById(R.id.edt1);
        EditText editTextsoB =findViewById(R.id.edt2);
        EditText editTextKQ =findViewById(R.id.edtKQ);
        String strA= editTextsoA.getText().toString();
        String strB= editTextsoB.getText().toString();
        int soA =Integer.parseInt(strA);
        int soB =Integer.parseInt(strB);
        double tong = 0.5*soA + 0.5*soB;
        String strTong =String.valueOf(tong);
        editTextKQ.setText(strTong);
    }
}