package android.example.technomedic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;


public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>{

    @SuppressWarnings("CanBeFinal")
    private Context mCtx;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Symptom> symptomList;

    public SymptomAdapter(Context mCtx, ArrayList<Symptom> symptomList) {
        this.mCtx = mCtx;
        this.symptomList = symptomList;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public SymptomAdapter.SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.symptom_layout, parent,false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SymptomAdapter.SymptomViewHolder holder, int position) {
        final Symptom symptom = symptomList.get(position);
        holder.mname.setText(symptom.getName());
        if(symptom.isAdded()){
            holder.mrel.setCardBackgroundColor(ContextCompat.getColor(mCtx,R.color.select));
        }
        else{
            holder.mrel.setCardBackgroundColor(ContextCompat.getColor(mCtx,R.color.not_select));
        }

        holder.mrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(symptom.isAdded()){
                    holder.mrel.setCardBackgroundColor(ContextCompat.getColor(mCtx,R.color.not_select));
                    symptom.setAdded(false);
                    MainActivity.searchpara.remove(symptom.getId());
                }
                else{
                    holder.mrel.setCardBackgroundColor(ContextCompat.getColor(mCtx,R.color.select));
                    symptom.setAdded(true);
                    MainActivity.searchpara.add(symptom.getId());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class SymptomViewHolder extends RecyclerView.ViewHolder {

        final TextView mname;

        final CardView mrel;
      SymptomViewHolder(View itemView) {
            super(itemView);
            mname = itemView.findViewById(R.id.symptom_name);
            mrel=itemView.findViewById(R.id.symptom_card);
        }
    }



    public void filter(String charText) {
        charText = charText.toLowerCase().replaceAll("[*0-9]", "");
        ArrayList<Symptom> arraylist=new ArrayList<>(MainActivity.symptomList2);
        MainActivity.symptomList.clear();
        if (charText.length() == 0 ) {
            MainActivity.setup();
            for (Symptom wp : MainActivity.symptomList) {
                if (MainActivity.searchpara.contains(wp.getId())) {
                    wp.setAdded(true);
                }
            }
        } else {
            for (Symptom wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.symptomList.add(wp);
                }
            }
            arraylist.clear();
        }
        notifyDataSetChanged();
    }

    }
