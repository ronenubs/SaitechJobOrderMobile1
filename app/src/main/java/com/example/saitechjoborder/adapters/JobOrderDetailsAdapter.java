package com.example.saitechjoborder.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.saitechjoborder.qrscanners.ItemQRScanner;
import com.example.saitechjoborder.R;

public class JobOrderDetailsAdapter extends RecyclerView.Adapter<JobOrderDetailsAdapter.ViewHolder> {

    private String[] localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvJobOrderDetail;
        private final Button btnScan;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvJobOrderDetail = (TextView) view.findViewById(R.id.tvJobOrderDetail);
            btnScan = (Button) view.findViewById(R.id.btnScan);
        }

        public TextView getTextView() {
            return tvJobOrderDetail;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public JobOrderDetailsAdapter(Context context, String[] dataSet) {
        this.context = context;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.job_order_detail_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
        viewHolder.btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQr();
            }
        });
    }

    private void scanQr() {
        Intent i = new Intent(context, ItemQRScanner.class);
        context.startActivity(i);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
