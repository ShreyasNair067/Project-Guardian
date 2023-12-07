package deakin.gopher.guardian.view.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import deakin.gopher.guardian.R;
import deakin.gopher.guardian.adapter.PatientListAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import deakin.gopher.guardian.model.Patient;

@SuppressLint("Registered")
public class ArchivedPatientListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private PatientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_patient_list);


        recyclerView = findViewById(R.id.archived_patient_recycler_view);



        DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference().child("patients");
        Query archivedQuery = patientRef.orderByChild("is_Archived").equalTo(true);
        archivedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("FirebaseTest", "Archived patients found: " + dataSnapshot.getChildrenCount());
                } else {
                    Log.d("FirebaseTest", "No archived patients found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseTest", "Error fetching data", databaseError.toException());
            }
        });
        final FirebaseRecyclerOptions<Patient> options = new FirebaseRecyclerOptions.Builder<Patient>()
            .setQuery(archivedQuery, Patient.class)
            .build();





        adapter = new PatientListAdapter(this, options, true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
