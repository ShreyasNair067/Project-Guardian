package deakin.gopher.guardian.view.general;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import deakin.gopher.guardian.R;
import deakin.gopher.guardian.model.Patient;

public class PatientViewHolder extends RecyclerView.ViewHolder {
    public TextView patientName;

    public PatientViewHolder(View itemView) {
        super(itemView);
        patientName = itemView.findViewById(R.id.tvPatientName);
    }

    public void bind(final Patient patient) {
        final String fullName = patient.getFirstName() + " " + patient.getLastName();
        patientName.setText(fullName);
    }
}

