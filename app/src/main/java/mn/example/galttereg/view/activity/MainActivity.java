package mn.example.galttereg.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import mn.example.galttereg.R;
import mn.example.galttereg.client.entity.User;
import mn.example.galttereg.client.main.UserListener;
import mn.example.galttereg.client.response.Response;
import mn.example.galttereg.client.util.CustomAlertDialog;
import mn.example.galttereg.view.widget.MainWidget;

public class MainActivity extends AppCompatActivity {
    private Button btnLeft, btnRight, btn_1, btn_2, btn_3, btn_4, btn_5;
    private CustomAlertDialog customAlertDialog;
    private MainWidget mainWidget;
    private Context context;
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainWidget.getUserId("https://jsonplaceholder.typicode.com/users", new UserListener() {
                @Override
                public void onSuccess(User user) {
                    Log.e("asd", "onSuccess: " + user.getId());
                    customAlertDialog.cancel();
                }

                @Override
                public void errorResponse(Response errorResponce) {
                    Log.e("asd", "onSuccess: " + errorResponce.getErrorMessage());
                    customAlertDialog.cancel();

                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainWidget = new MainWidget(this);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        customAlertDialog = new CustomAlertDialog(this, onClickListener);
        btnLeft.setOnClickListener(v -> {
            customAlertDialog.show();
        });
        btnRight.setOnClickListener(v -> {
            customAlertDialog.show();
        });
        btn_1.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormActivity.class);
            startActivity(intent);
        });

    }
}