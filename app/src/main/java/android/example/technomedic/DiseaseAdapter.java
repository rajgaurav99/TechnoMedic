package android.example.technomedic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {
    private Context mCtx;
    private ArrayList<Disease> diseaseList;

    public DiseaseAdapter(Context mCtx, ArrayList<Disease> diseaseList) {
        this.mCtx = mCtx;
        this.diseaseList = diseaseList;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public DiseaseAdapter.DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.disease_layout, null);
        return new DiseaseAdapter.DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiseaseAdapter.DiseaseViewHolder holder, int position) {
        Disease disease = diseaseList.get(position);
        holder.mname.setText(disease.getName());
        holder.mproname.setText(disease.getProName());
        holder.mdesc.setText(disease.getDesc());
        holder.mspec.setText(disease.getSpec());
        holder.mprogressbar.setProgress((int)disease.getAccuracy());
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class DiseaseViewHolder extends RecyclerView.ViewHolder {

        TextView mname,mproname,mdesc,mspec;
        ProgressBar mprogressbar;
        public DiseaseViewHolder(View itemView) {
            super(itemView);
            mname = itemView.findViewById(R.id.disease_name);
            mproname=itemView.findViewById(R.id.pro_disease);
            mdesc=itemView.findViewById(R.id.disease_desc);
            mspec=itemView.findViewById(R.id.spec);
            mprogressbar=itemView.findViewById(R.id.accuracy);

        }
    }

}
