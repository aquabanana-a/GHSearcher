package com.dobranos.ghsearcher.ui.fragment.fgSearchResults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.model.data.common.IServiceProvider;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.logic.appearance.SwitchScreenMgr;
import com.dobranos.ghsearcher.ui.fragment.fgUser.FgUser;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FgSearchResultsAdapter extends RecyclerView.Adapter<FgSearchResultsAdapter.ViewHolder>
{
    private FragmentActivity holder;

    private IServiceProvider serviceProvider;
    private ArrayList<IUser> dataProvider;
    private int layoutResourceId;

    public FgSearchResultsAdapter(FragmentActivity holder, int layoutResourceId, IServiceProvider provider)
    {
        this.holder = holder;
        this.layoutResourceId = layoutResourceId;

        this.serviceProvider = provider;
        this.dataProvider = new ArrayList<>();
    }

    public FgSearchResultsAdapter addData(List<IUser> value)
    {
        dataProvider.addAll(value);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        Context c = viewGroup.getContext();
        View v = LayoutInflater.from(c).inflate(layoutResourceId, viewGroup, false);
        return new ViewHolder(holder, v, serviceProvider);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        viewHolder.refresh(getItem(i), i);
    }

    @Override
    public int getItemCount()
    {
        return dataProvider.size();
    }

    private IUser getItem(int i)
    {
        return dataProvider.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private final FragmentActivity context;
        private final View rootView;
        private final IServiceProvider serviceProvider;

        private final View vBackground;
        private final SimpleDraweeView ivPhoto;
        private final TextView tvTitle;
        private final ImageView ivBookmark;

        private IUser user;

        private ViewHolder(final FragmentActivity context, View itemView, IServiceProvider provider)
        {
            super(itemView);

            this.context = context;
            this.rootView = itemView;
            this.serviceProvider = provider;

            this.vBackground = rootView.findViewById(R.id.v_background);
            this.ivPhoto = rootView.findViewById(R.id.iv_photo);
            this.tvTitle = rootView.findViewById(R.id.tv_title);
            this.ivBookmark = rootView.findViewById(R.id.iv_bookmark);

            this.vBackground.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SwitchScreenMgr.switchFragment(context, new FgUser()
                        .withUserLogin(user.getLogin())
                        .withServiceProvider(serviceProvider));
                }
            });

            this.ivBookmark.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }

        private void refresh(IUser user, int index)
        {
            this.user = user;

            ivPhoto.setImageURI(user.getAvatarUrl());
            tvTitle.setText(user.getLogin());
        }
    }
}