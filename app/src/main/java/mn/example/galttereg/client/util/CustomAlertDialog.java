package mn.example.galttereg.client.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mn.example.galttereg.R;


public class CustomAlertDialog extends Dialog {

    private TextView textInfo;
    private Button btnEnter;
    private View.OnClickListener dialogListener;

    public CustomAlertDialog(Context context, View.OnClickListener dialogListener) {
        super(context);
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_direction);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        inflatedView();
        initView();
    }

    private void inflatedView() {
        btnEnter = findViewById(R.id.btnEnter);
    }

    private void initView() {
        btnEnter.setOnClickListener(dialogListener);
    }
}