package com.dobranos.ghsearcher.ui.fragment.fgUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.utils.ViewUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FgUserRepoAdapter extends RecyclerView.Adapter<FgUserRepoAdapter.ViewHolder>
{
    private FragmentActivity context;
    private Fragment holder;

    private List<IRepository> dataProvider;
    private List<Long> dataProviderKeys;

    private int layoutResourceId;

    public FgUserRepoAdapter(FragmentActivity context, Fragment holder, List<IRepository> source, int layoutResourceId)
    {
        this.context = context;
        this.holder = holder;

        this.layoutResourceId = layoutResourceId;
        this.dataProvider = new ArrayList();
        this.dataProviderKeys = new ArrayList();

        this.addData(source);
    }

    public List<IRepository> getData() { return new ArrayList<>(dataProvider); }

    public FgUserRepoAdapter addData(List<IRepository> values)
    {
        for(IRepository r : values)
            if(!dataProviderKeys.contains(r.getId()))
            {
                dataProvider.add(r);
                dataProviderKeys.add(r.getId());

                notifyDataSetChanged();
            }

        return this;
    }

    @Override
    public FgUserRepoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        Context c = viewGroup.getContext();
        View v = LayoutInflater.from(c).inflate(layoutResourceId, viewGroup, false);
        return new FgUserRepoAdapter.ViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(FgUserRepoAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.refresh(getItem(i), i);
    }

    @Override
    public int getItemCount()
    {
        return dataProvider.size();
    }

    private IRepository getItem(int i)
    {
        return dataProvider.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private FragmentActivity context;
        private final View rootView;

        private final View vBackground;
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvLanguage;
        private final TextView tvStargazers;
        private final TextView tvForks;
        private final TextView tvUpdated;

        private final View vStargazerGroup;
        private final View vForkGroup;

        private IRepository repo;

        private ViewHolder(final FragmentActivity context, View itemView)
        {
            super(itemView);

            this.context = context;
            this.rootView = itemView;

            this.vBackground    = rootView.findViewById(R.id.v_background);
            this.tvTitle        = rootView.findViewById(R.id.tv_title);
            this.tvDescription  = rootView.findViewById(R.id.tv_description);
            this.tvLanguage     = rootView.findViewById(R.id.tv_language);
            this.tvStargazers   = rootView.findViewById(R.id.tv_stargazers);
            this.tvForks        = rootView.findViewById(R.id.tv_forks);
            this.tvUpdated      = rootView.findViewById(R.id.tv_updated);

            this.vStargazerGroup    = rootView.findViewById(R.id.v_stargazerGroup);
            this.vForkGroup         = rootView.findViewById(R.id.v_forkGroup);

            this.vBackground.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(repo.getHtmlUrl()));
                        context.startActivity(intent);
                    }
                    catch (Exception e) { }
                }
            });
        }

        private void refresh(IRepository repo, int index)
        {
            this.repo = repo;

            tvTitle.setText(repo.getName());
            tvDescription.setText(repo.getDescription());

            tvLanguage.setText(repo.getLanguage());
            if(StringUtils.isEmpty(repo.getLanguage()))
                ViewUtil.toGone(tvLanguage);
            else
                ViewUtil.toVisible(tvLanguage);

            tvStargazers.setText(Long.toString(repo.getStargazersCount()));
            if(repo.getStargazersCount() == 0)
                ViewUtil.toGone(vStargazerGroup);
            else
                ViewUtil.toVisible(vStargazerGroup);

            tvForks.setText(Long.toString(repo.getForksCount()));
            if (repo.getForksCount() == 0)
                ViewUtil.toGone(vForkGroup);
            else
                ViewUtil.toVisible(vForkGroup);

            tvUpdated.setText("updated on " + new SimpleDateFormat("dd.MM.yyyy").format(repo.getUpdatedAt()));
        }
    }
}