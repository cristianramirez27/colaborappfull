package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class BenefitsRecyclerAdapter extends RecyclerView.Adapter<BenefitsRecyclerAdapter.ViewHolder> {

    private Context context;
    private OnBenefitsCategoryClickListener onBenefitsCategoryClickListener;

    private List<BenefitsCategoriesResponse.Category> categories;
    public BenefitsRecyclerAdapter(Context context,List<BenefitsCategoriesResponse.Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public BenefitsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_benefits, viewGroup, false);
        return new BenefitsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BenefitsCategoriesResponse.Category currentItem = categories.get(i);
        viewHolder.name.setText(currentItem.getNombre());
        viewHolder.description.setText(currentItem.getDescripcion());
       // Picasso.with(getContext()).load(currentItem.getLogo()).placeholder(R.drawable.placeholder_category ).into(viewHolder.imageBenefits);
        Picasso.get().load(currentItem.getLogo())
                .placeholder(R.drawable.placeholder_category)
                .error(R.drawable.placeholder_category).into(viewHolder.imageBenefits);

       // ImageLoaderUtil.loadPictureFromURLPlaceholder(context,currentItem.getLogo(),viewHolder.imageBenefits,R.drawable.placeholder_category);
        viewHolder.cardViewBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBenefitsCategoryClickListener.onCategoryClick(currentItem);
            }
        });
    }


    public void setOnBenefitsCategoryClickListener(OnBenefitsCategoryClickListener onBenefitsCategoryClickListener) {
        this.onBenefitsCategoryClickListener = onBenefitsCategoryClickListener;
    }

    public List<BenefitsCategoriesResponse.Category> getBenefits(){
        return categories;
    }

    @Override
    public int getItemCount() {
        return categories.size();
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

        @BindView(R.id.cardViewBenefits)
        CardView cardViewBenefits;
        @BindView(R.id.imageBenefits)
        ImageView imageBenefits;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnBenefitsCategoryClickListener {
        void onCategoryClick(BenefitsCategoriesResponse.Category category);
    }

}
