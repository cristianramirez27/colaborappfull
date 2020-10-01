package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsDiscountsResponse;
import com.coppel.rhconecta.dev.business.models.Discounts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class DiscountsRecyclerAdapter extends RecyclerView.Adapter<DiscountsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<BenefitsDiscountsResponse.Discount> discounts;
    private OnBenefitsDiscountsClickListener onBenefitsDiscountsClickListener;

    public DiscountsRecyclerAdapter(Context context, List<BenefitsDiscountsResponse.Discount> discounts) {
        this.discounts = discounts;
        this.context = context;
    }

    @NonNull
    @Override
    public DiscountsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_discount_benefits, viewGroup, false);
        return new DiscountsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BenefitsDiscountsResponse.Discount currentItem = discounts.get(i);
        //viewHolder.d.setText(currentItem.getDescripciondes());
        viewHolder.discount.setText(currentItem.getDescuento());
        //viewHolder.checkboxElement.setEnabled(currentItem.getOpc_estatus() == 1 ? true : false);
        if(currentItem.getRuta() != null && !currentItem.getRuta().isEmpty()){

            Glide.with(getApplicationContext()).load(currentItem.getRuta())
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .placeholder(R.drawable.placeholder_discount)
                    .into(viewHolder.imageBenefits);

            /*
            Picasso.with(getApplicationContext())
                    .load(currentItem.getRuta())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.imageBenefits, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(getApplicationContext())
                                    .load(currentItem.getRuta())
                                    .error(R.drawable.placeholder_discount)
                                    .into(viewHolder.imageBenefits, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }
                    });*/
        }

       // viewHolder.setIsRecyclable(false);
       /* Picasso.with(getApplicationContext())
                .load(currentItem.getRuta())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(viewHolder.imageBenefits, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getApplicationContext())
                                .load(currentItem.getRuta())
                                .error(R.drawable.placeholder_discount)
                                .into(viewHolder.imageBenefits, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                });*/

        viewHolder.cardViewDiscounts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBenefitsDiscountsClickListener.onCategoryClick(currentItem);
            }
        });
    }

    public List<BenefitsDiscountsResponse.Discount> getBenefits(){
        return discounts;
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardViewDiscounts)
        CardView cardViewDiscounts;
        @BindView(R.id.imageBenefits)
        ImageView imageBenefits;
        @BindView(R.id.discount)
        TextView discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }

    public void setOnBenefitsDiscountsClickListener(OnBenefitsDiscountsClickListener onBenefitsDiscountsClickListener) {
        this.onBenefitsDiscountsClickListener = onBenefitsDiscountsClickListener;
    }

    public interface OnBenefitsDiscountsClickListener {
        void onCategoryClick(BenefitsDiscountsResponse.Discount discount);
    }
}
