package deakin.gopher.guardian.view.general;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import deakin.gopher.guardian.R;
import deakin.gopher.guardian.adapter.PatientAdapter;
import deakin.gopher.guardian.model.login.SessionManager;
import deakin.gopher.guardian.model.Patient;

public class MainActivity extends BaseActivity {

    @Override
  protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main);

      // Initialize RecyclerView
      final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));


      final List<Patient> patientList = new ArrayList<>();

      patientList.add(new Patient("1", "John", "Doe"));
      patientList.add(new Patient("2", "Jane", "Doe"));



      final PatientAdapter adapter = new PatientAdapter(patientList);
      recyclerView.setAdapter(adapter);





        final Button getStartedButton = findViewById(R.id.getStartedButton);

      getStartedButton.setOnClickListener(this::onClick);

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull final RecyclerView recyclerView, @NonNull final RecyclerView.ViewHolder viewHolder, @NonNull final RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, final int direction) {

                patientList.remove(viewHolder.getBindingAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getBindingAdapterPosition());
            }

            @Override
            public void onChildDraw(@NonNull final Canvas c, @NonNull final RecyclerView recyclerView,
                                    @NonNull final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                final View itemView = viewHolder.itemView;
                final Paint paint = new Paint();
                paint.setColor(Color.RED);

                if (0 > dX) { // Swipe to left
                    // Draw the red background
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                        (float) itemView.getRight(), (float) itemView.getBottom(), paint);
                }
            }




        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        FirebaseMessaging.getInstance()
        .getToken()
        .addOnCompleteListener(
            task -> {
              if (!task.isSuccessful()) {
                Log.w("tag", "Fetching FCM registration token failed", task.getException());
                return;
              }

              // Get new FCM registration token
              final String token = task.getResult();

              // Log and toast
              Log.d("token: ", token);
            });
  }

  public void logout(final View view) {
    final SessionManager sessionManager = new SessionManager(getApplicationContext());
    sessionManager.logoutUser();
    FirebaseAuth.getInstance().signOut(); // logout
    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    finish();
  }

  private void onClick(final View view) {
    final SessionManager sessionManager = new SessionManager(this);
    if (!sessionManager.isLoggedIn()) {
      final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivity(intent);
    } else {
      final Intent getStartedIntent = new Intent(MainActivity.this, Homepage4caretaker.class);
      startActivity(getStartedIntent);
    }
  }
}
