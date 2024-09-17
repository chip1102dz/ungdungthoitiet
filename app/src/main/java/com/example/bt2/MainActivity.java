package com.example.bt2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt2.database.UserDataBase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtNgay;
    private EditText edtNhietDo;
    private EditText edtDoAm;
    private Button btnSubMit;
    private Button btnOK;
    private List<User> mListUser;
    private RadioButton checkBoxCo;
    private Button btnDelete;
    User user;


    UserAdapter userAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //anh xa view
        initUI();

        //set RecyclerView
        recyclerView = findViewById(R.id.rvc_content);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mListUser = new ArrayList<>();
        userAdapter = new UserAdapter(mListUser);
        userAdapter.setOnItemClickListener(selectedUser -> {
            edtNgay.setText(selectedUser.getNgayThang());
            edtNhietDo.setText(selectedUser.getNhietDo());
            edtDoAm.setText(selectedUser.getDoAm());

            // Lưu đối tượng đã chọn vào biến user
            user = selectedUser;
        });
        recyclerView.setAdapter(userAdapter);

        loadData();
        setButtonOK();
        setButtonSubmit();
        setDelete();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void initUI(){
        edtNgay = findViewById(R.id.edt_ngay);
        edtNhietDo = findViewById(R.id.edt_nhietdo);
        edtDoAm = findViewById(R.id.edt_doam);

        btnOK = findViewById(R.id.btn_ok);
        btnSubMit = findViewById(R.id.btn_submit);
        btnDelete = findViewById(R.id.btn_delete);

        checkBoxCo = findViewById(R.id.checkboxco);
    }

    public void loadData(){
        mListUser = UserDataBase.getInstance(MainActivity.this).userDAO().getListUser();
        userAdapter.getData(mListUser);
    }

    public void setDelete(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngay = edtNgay.getText().toString();
                String nhietDo = edtNhietDo.getText().toString();
                String doAm = edtDoAm.getText().toString();

                user.setNgayThang(ngay);
                user.setNhietDo(nhietDo);
                user.setDoAm(doAm);

                if(checkBoxCo.isChecked()){
                    UserDataBase.getInstance(MainActivity.this).userDAO().DeleteUser(user);
                    Toast.makeText(MainActivity.this, "Xóa thông tin thành công", Toast.LENGTH_SHORT).show();

                    edtNgay.setText("");
                    edtNhietDo.setText("");
                    edtDoAm.setText("");
                    loadData();
                }

            }
        });
    }
    public void setButtonOK(){
        btnOK.setOnClickListener(view -> {
            String ngay = edtNgay.getText().toString();
            String nhietDo = edtNhietDo.getText().toString();
            String doAm = edtDoAm.getText().toString();
            user = new User(ngay, nhietDo, doAm);
            if (isUserExits(user)){
                Toast.makeText(this, "Đã có thông tin ngày này", Toast.LENGTH_SHORT).show();
                return;
            }
            if(checkBoxCo.isChecked()){
                if (TextUtils.isEmpty(ngay)||TextUtils.isEmpty(nhietDo)||TextUtils.isEmpty(doAm)){
                    Toast.makeText(MainActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    UserDataBase.getInstance(MainActivity.this).userDAO().InsertUser(user);
                    Toast.makeText(MainActivity.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                    edtNgay.setText("");
                    edtNhietDo.setText("");
                    edtDoAm.setText("");
                    loadData();

                }
            } else {
                Toast.makeText(MainActivity.this, "Checkbox \"không\" đã được chọn", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setButtonSubmit(){
        btnSubMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngay = edtNgay.getText().toString();
                String nhietdo = edtNhietDo.getText().toString();
                String doam = edtDoAm.getText().toString();

                if (user != null) {  // Kiểm tra xem có item nào đã được chọn hay không
                    user.setNgayThang(ngay);
                    user.setNhietDo(nhietdo);
                    user.setDoAm(doam);

                    if (checkBoxCo.isChecked()) {
                        if (TextUtils.isEmpty(ngay) || TextUtils.isEmpty(nhietdo) || TextUtils.isEmpty(doam)) {
                            Toast.makeText(MainActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Cập nhật đối tượng user đã chọn
                        UserDataBase.getInstance(MainActivity.this).userDAO().UpdateUser(user);

                        Toast.makeText(MainActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        edtNgay.setText("");
                        edtNhietDo.setText("");
                        edtDoAm.setText("");
                        loadData();  // Tải lại danh sách
                    } else {
                        Toast.makeText(MainActivity.this, "Checkbox \"không\" đã được chọn", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Hãy chọn một item từ danh sách", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public boolean isUserExits(User user){
        List<User> list = UserDataBase.getInstance(this).userDAO().checkDate(user.getNgayThang());
        return list != null && !list.isEmpty();
    }
}