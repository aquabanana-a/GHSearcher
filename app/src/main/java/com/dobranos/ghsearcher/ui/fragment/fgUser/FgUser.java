package com.dobranos.ghsearcher.ui.fragment.fgUser;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.di.module.DiActMainModule;
import com.dobranos.ghsearcher.model.data.common.IServiceProvider;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.logic.db.DbBookmarkProvider;
import com.dobranos.ghsearcher.model.logic.db.DbServiceProvider;
import com.dobranos.ghsearcher.model.logic.gitHub.GitHubServiceProvider;
import com.dobranos.ghsearcher.ui.fragment.FgBase;
import com.dobranos.ghsearcher.utils.ViewUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pnikosis.materialishprogress.ProgressWheel;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FgUser extends FgBase
{
    @Inject DiActMainModule.Menu menu;
    @Inject DiActMainModule.Behavior behavior;

    @Inject DbBookmarkProvider bookmarkProvider;
    @Inject GitHubServiceProvider networkProvider;
    @Inject DbServiceProvider storageProvider;
    private IServiceProvider getServiceProvider() { return useStorage ? storageProvider : networkProvider; }

    private View fgView;

    private boolean bookmarked;

    private SimpleDraweeView ivPhoto;
    private ImageView ivBookmark;
    private TextView tvName;
    private TextView tvLogin;
    private TextView tvCompany;
    private TextView tvLocation;
    private TextView tvBio;
    private TextView tvCreated;

    private TextView tvEmpty;

    private View vCompanyGroup;
    private View vLocationGroup;

    private ProgressWheel vLoading;
    private RecyclerView recyclerView;
    private FgUserRepoAdapter recyclerViewAdapter;

    private IUser user;

    @Override
    public void onResume()
    {
        super.onResume();

        behavior.setScrollingBehavior(false);
        context.setTitle(""/*R.string.cart_title*/);
        setHasOptionsMenu(true);
        setBackArrowInsteadHamburger(true);
    }

    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle inState)
    {
        if (inState != null)
        {
            userLogin = inState.getString("user_login");
            bookmarked = inState.getBoolean("bookmarked");
            useStorage = inState.getBoolean("use_storage");
        }

        fgView = inflater.inflate(R.layout.fg_user, container, false);

        ivPhoto     = ((SimpleDraweeView)fgView.findViewById(R.id.iv_photo));
        ivBookmark  = ((ImageView)fgView.findViewById(R.id.iv_bookmark));
        tvName      = ((TextView)fgView.findViewById(R.id.tv_name));
        tvLogin     = ((TextView)fgView.findViewById(R.id.tv_login));
        tvCompany   = ((TextView)fgView.findViewById(R.id.tv_company));
        tvLocation  = ((TextView)fgView.findViewById(R.id.tv_location));
        tvBio       = ((TextView)fgView.findViewById(R.id.tv_bio));
        tvCreated   = ((TextView)fgView.findViewById(R.id.tv_created));

        vCompanyGroup   = fgView.findViewById(R.id.v_companyGroup);
        vLocationGroup  = fgView.findViewById(R.id.v_locationGroup);

        tvEmpty = fgView.findViewById(R.id.tv_empty);
        //ViewUtil.toVisible(tvEmpty);

        vLoading = fgView.findViewById(R.id.v_loading);

        recyclerView = fgView.findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), getActivity().getResources().getBoolean(R.bool.layout_land) ? 2 : 1, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(recyclerViewAdapter = new FgUserRepoAdapter(context, this, new ArrayList<IRepository>(), R.layout.fg_user_repo_item));

        return fgView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getServiceProvider().getUser(userLogin)
            .subscribeWith(new Observer<IUser>()
            {
                @Override public void onSubscribe(Disposable d) { }
                @Override public void onComplete() { }

                @Override
                public void onNext(IUser u)
                {
                    user = u;

                    tvCreated.setText("since " + new SimpleDateFormat("dd.MM.yyyy").format(user.getCreatedAt()));

                    ivPhoto.setImageURI(user.getAvatarUrl());
                    tvName.setText(user.getName());
                    tvLogin.setText(user.getLogin());

                    tvCompany.setText(user.getCompany());
                    if (user.getCompany() == null)
                        ViewUtil.toGone(vCompanyGroup);

                    tvLocation.setText(user.getLocation());
                    if (user.getLocation() == null)
                        ViewUtil.toGone(vLocationGroup);

                    tvBio.setText(user.getBio());
                    if (user.getBio() == null)
                        ViewUtil.toGone(tvBio);

                    refreshBookmarkAndInvalidate();
                    ivBookmark.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            bookmarked = !bookmarked;
                            refreshBookmark();

                            ivBookmark.startAnimation(AnimationUtils.loadAnimation(ivBookmark.getContext(), R.anim.milkshake));

                            if (!bookmarkProvider.isKnownUser(user))
                                bookmarkProvider.rememberUser(user, recyclerViewAdapter.getData()).subscribe(new CompletableObserver()
                                {
                                    @Override
                                    public void onSubscribe(Disposable d) { }

                                    @Override
                                    public void onComplete() { Toast.makeText(getActivity(), R.string.fg_user_bookmark_add_success, Toast.LENGTH_SHORT).show(); }

                                    @Override
                                    public void onError(Throwable e) { Toast.makeText(getActivity(), R.string.fg_user_bookmark_add_fault, Toast.LENGTH_LONG).show(); }
                                });
                            else
                                bookmarkProvider.removeUser(user).subscribe(new CompletableObserver()
                                {
                                    @Override
                                    public void onSubscribe(Disposable d) { }

                                    @Override
                                    public void onComplete()
                                    {
                                        refreshBookmarkAndInvalidate();
                                        Toast.makeText(getActivity(), R.string.fg_user_bookmark_remove_success, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(Throwable e) { Toast.makeText(getActivity(), R.string.fg_user_bookmark_remove_fault, Toast.LENGTH_LONG).show(); }
                                });
                        }
                    });
                }

                @Override
                public void onError(Throwable e)
                {
                    handleError(e);
                }
            });

        getServiceProvider().getUserRepositories(userLogin)
            .subscribeWith(new Observer<List<IRepository>>()
            {
                Boolean empty;

                @Override public void onSubscribe(Disposable d) { behavior.showLoading(true); }
                @Override public void onComplete()
                {
                    behavior.showLoading(false);

                    if (empty == null || empty)
                        ViewUtil.toVisible(tvEmpty);
                    else
                        ViewUtil.toGone(tvEmpty);
                }

                @Override
                public void onNext(List<IRepository> values)
                {
                    if (empty == null)
                        empty = values.size() == 0;

                    recyclerViewAdapter.addData(values);
                }

                @Override
                public void onError(Throwable e)
                {
                    behavior.showLoading(false);

                    handleError(e);
                }
            });
    }

    private void refreshBookmarkAndInvalidate()
    {
        bookmarked = bookmarkProvider.isKnownUser(user);
        refreshBookmark();
    }

    private void refreshBookmark()
    {
        ivBookmark.setImageResource(bookmarked ? R.drawable.ic_bookmark_black_24dp
                                               : R.drawable.ic_bookmark_border_black_24dp);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("user_login", userLogin);
        outState.putBoolean("bookmarked", bookmarked);
        outState.putBoolean("use_storage", useStorage);
        super.onSaveInstanceState(outState);
    }

    private String userLogin;
    public FgUser withUserLogin(String userLogin)
    {
        this.userLogin = userLogin;
        return this;
    }

    private boolean useStorage;
    private IServiceProvider desiredProvider;
    public FgUser withServiceProvider(IServiceProvider provider)
    {
        this.desiredProvider = provider;
        return this;
    }

    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(m, inflater);

        menu.miSearch.setVisible(false);
        menu.miBookmarks.setVisible(true);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof InjectorProvider)
        {
            ((InjectorProvider) context).inject(this);

            // todo: facepalm
            useStorage = desiredProvider == storageProvider;
        }
        else
        {
            throw new IllegalStateException("You should provide InjectorProvider");
        }
    }

    public interface InjectorProvider
    {
        void inject(FgUser fragment);
    }
}