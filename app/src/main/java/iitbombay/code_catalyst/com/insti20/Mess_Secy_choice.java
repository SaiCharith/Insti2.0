package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mess_Secy_choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess__secy_choice);
        Button update_menu= (Button) findViewById(R.id.updatemenu);
        Button original_app=(Button) findViewById(R.id.originalapp);
        original_app.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Mess_Secy_choice.this,After_login.class));
            }
        });
        update_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Mess_Secy_choice.this,Update_menu.class));
            }
        });


    }


}
