package com.chidozie.n.myprojects.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.chidozie.n.myprojects.R;
import com.chidozie.n.myprojects.database.RepositoryViewModel;
import com.chidozie.n.myprojects.databinding.ActivityPagerBinding;
import com.chidozie.n.myprojects.details.ProjectDetailsActivity;
import com.chidozie.n.myprojects.model.Project;


/**
 * Created by Chidozie on 10/10/2018.
 */

public class PagerActivity extends AppCompatActivity implements ProjectListFragment.Callbacks {
    private volatile ActivityPagerBinding binding;
    private MyPagerAdapter adapter;
    private RepositoryViewModel repo;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_pager);
        binding.setLifecycleOwner(this);
        repo = ViewModelProviders.of(this).get(RepositoryViewModel.class);

        binding.fab.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {

                Intent intent = ProjectDetailsActivity.newIntent(PagerActivity.this, new Project());
                startActivity(intent);
            }
        });

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSizeChanged(int tabNo, int size) {
        String tabName;
        switch (tabNo) {
            default:
            case 0:
                tabName = getString(R.string.tab_uncompleted, size);
                break;
            case 1:
                tabName = getString(R.string.tab_completed, size);
                break;
        }
        binding.pagerTab.getTabAt(tabNo).setText(tabName);
    }

    protected class MyPagerAdapter extends FragmentStatePagerAdapter {
        static final int NUM_PAGES = 3;
        private final ProjectListFragment fragments[] = {
            new UncompletedListFragment(),
            new CompletedListFragment(),
            new UncompletedListFragment()
        };

        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_uncompleted, 0);
                case 1:
                    return getString(R.string.tab_completed, 0);
                default:
                    return "Error";
            }
        }
    }

    private class TabTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
