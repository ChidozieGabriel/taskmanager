package com.chidozie.n.myprojects.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.chidozie.n.myprojects.App;
import com.chidozie.n.myprojects.R;
import com.chidozie.n.myprojects.database.RepositoryViewModel;
import com.chidozie.n.myprojects.database.UpdateService;
import com.chidozie.n.myprojects.databinding.FragmentProjectListBinding;
import com.chidozie.n.myprojects.databinding.ListItemProjectBinding;
import com.chidozie.n.myprojects.details.ProjectDetailsActivity;
import com.chidozie.n.myprojects.model.Project;
import com.chidozie.n.myprojects.model.ProjectViewModel;

import java.util.List;

/**
 * Created by Chidozie on 9/21/2018.
 */

public abstract class ProjectListFragment extends Fragment {
    protected RepositoryViewModel repo;
    private ProjectAdapter adapter;
    private Callbacks callbacks;
    private int tabNo = type();
    private FragmentProjectListBinding binding;

    public interface Callbacks {
        void onSizeChanged(int tabNo, int size);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = ViewModelProviders.of(this).get(RepositoryViewModel.class);
        UpdateService.setServiceAlarm(getContext(), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_project_list, container, false);
        binding.setLifecycleOwner(this);
        binding.emptyText.setText(emptyText());
        binding.recyclerView.setLayoutManager(
            new LinearLayoutManager(getActivity()));

        adapter = new ProjectAdapter();

        query().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                adapter.set(projects);

            }
        });

        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    protected abstract LiveData<List<Project>> query();

    protected abstract int type();

    protected abstract String emptyText();


    public class ProjectAdapter extends Adapter<ProjectHolder> {
        protected int count;
        private List<Project> projects; // cached copy of projects

        @Override
        public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemProjectBinding adapterBinding = DataBindingUtil
                .inflate(inflater, R.layout.list_item_project, parent, false);
            return new ProjectHolder(adapterBinding);
        }

        @Override
        public void onBindViewHolder(ProjectHolder holder, int position) {
            Project project = projects.get(position);
            holder.bind(project);
        }

        @Override
        public int getItemCount() {
            return projects == null ? 0 : projects.size();
        }

        public void set(@Nullable List<Project> projects) {
            this.projects = projects;
            int bufferCount = projects == null ? 0 : projects.size();
            if (count != bufferCount) {
                count = bufferCount;
                callbacks.onSizeChanged(tabNo, count);
            }

            if(count == 0) {
                binding.emptyText.setText(emptyText());
            } else {
                binding.emptyText.setText("");
            }
            notifyDataSetChanged();
        }

    }

    public class ProjectHolder extends ViewHolder {
        private ListItemProjectBinding binding;
        private Project project;

        private ProjectHolder(ListItemProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ProjectViewModel viewModel = new ProjectViewModel(App.getInstance());
            binding.setViewModel(viewModel);
            binding.getRoot().setOnClickListener(new OnClickListener() {
                {
                }

                @Override
                public void onClick(View view) {
                    // open the project details
                    Intent intent = ProjectDetailsActivity.newIntent(
                        getActivity(), project);
                    startActivity(intent);
                }
            });
            binding.options.setOnClickListener(new OnClickListener() {
                {
                }

                @Override
                public void onClick(View view) {
                    // open contextual menu

                }
            });
        }

        private void bind(Project project) {
            this.project = project;
            binding.getViewModel().setProject(project);
            binding.executePendingBindings();
        }

    }

}

