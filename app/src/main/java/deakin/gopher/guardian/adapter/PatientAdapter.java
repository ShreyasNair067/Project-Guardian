package deakin.gopher.guardian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import deakin.gopher.guardian.R;

import deakin.gopher.guardian.view.general.PatientViewHolder;
import deakin.gopher.guardian.model.Patient;

public class PatientAdapter extends RecyclerView.Adapter<PatientViewHolder> {
    private final List<Patient> patientList;

    public PatientAdapter(final List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remove_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PatientViewHolder holder, int position) {
        final Patient patient = patientList.get(position);
        holder.bind(patient);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}
