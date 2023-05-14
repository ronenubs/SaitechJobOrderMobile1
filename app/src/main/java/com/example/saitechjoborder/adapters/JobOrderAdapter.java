package com.example.saitechjoborder.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.saitechjoborder.JobOrderDetails;
import com.example.saitechjoborder.R;
import com.example.saitechjoborder.classes.JobOrder;

import java.util.ArrayList;

public class JobOrderAdapter extends RecyclerView.Adapter<JobOrderAdapter.ViewHolder> {

    private ArrayList<JobOrder> localDataSet;
    private Context _context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvJobOrder;
        private final Button btnAssign;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvJobOrder = (TextView) view.findViewById(R.id.tvJobOrder);
            btnAssign= (Button) view.findViewById(R.id.btnAssign);
        }

        public TextView getTextView() {
            return tvJobOrder;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public JobOrderAdapter(Context _context, ArrayList<JobOrder> dataSet) {
        this._context = _context;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.job_order_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getJobOrderNo());
        viewHolder.btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJobOrderDetails();
            }
        });
    }

    private void showJobOrderDetails() {
        Intent i = new Intent(_context, JobOrderDetails.class);
        _context.startActivity(i);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
