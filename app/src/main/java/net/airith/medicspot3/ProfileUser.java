package net.airith.medicspot3;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileUser extends AppCompatActivity implements View.OnClickListener {
    GoogleSignInClient mGoogleSignInClient;
    String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        TextView tvName =(TextView) findViewById(R.id.tvname);
        TextView tvEmail =(TextView) findViewById(R.id.tvemail);

        name = getIntent().getStringExtra("Name");
        email = getIntent().getStringExtra("Email");

        tvName.setText(name);
        tvEmail.setText(email);

        Button sigout = findViewById(R.id.signout);
        sigout.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.medispot:

                //Toast.makeText(this,"This is about", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "MedicSpot Tracker", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MedicSpotActivity.class);
                startActivity(intent);
                break;

            case R.id.aboutus:

                Toast.makeText(this, "About us Section", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(this, AboutUsActivity.class);
                startActivity(intent1);
                break;

            case R.id.aboutmedicspot:

                //Toast.makeText(this,"This is about", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "About MedicSpot Section", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, AboutMedicSpotActivity.class);
                startActivity(intent2);
                break;

            case R.id.userfeedback:

                //Toast.makeText(this,"This is about", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "User Feedback Section", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(this, UserFeedback.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signout:
                signOut();
                break;

        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),email +"Signed Out", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
    }
}
