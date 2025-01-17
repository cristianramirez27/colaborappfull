package com.coppel.rhconecta.dev.views.adapters;

import static com.coppel.rhconecta.dev.views.utils.AppUtilities.getStringFromSharedPreferences;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
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
        /*Picasso.get().load(currentItem.getLogo())
                .placeholder(R.drawable.placeholder_category)
                .error(R.drawable.placeholder_category).into(viewHolder.imageBenefits);*/

        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );

        /*String imageName = currentItem.getLogo().substring(currentItem.getLogo().lastIndexOf("/") + 1);  // Nombre con extensión
        String resourceName = imageName.substring(0, imageName.lastIndexOf(".")); // Quita la extensión

        // Obtener el ID del recurso en drawable
        int imageResId = viewHolder.itemView.getContext().getResources().getIdentifier(
                resourceName.toLowerCase(),
                "drawable",
                viewHolder.itemView.getContext().getPackageName()
        );*/

        // Crear GlideUrl con el header
        GlideUrl glideUrl = new GlideUrl(currentItem.getLogo(), new LazyHeaders.Builder()
                .addHeader("Authorization", authHeader)
                .build());

        Glide.with(context)
                .load(glideUrl)
                .placeholder(R.drawable.placeholder_category)
                .error(R.drawable.placeholder_category)
                .into(viewHolder.imageBenefits);

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
