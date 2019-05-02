package com.chidozie.n.myprojects.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.chidozie.n.myprojects.App;
import com.chidozie.n.myprojects.R;
import com.chidozie.n.myprojects.database.RepositoryViewModel;
import com.chidozie.n.myprojects.databinding.FragmentProjectDetailsBinding;
import com.chidozie.n.myprojects.dialog.DateDialog;
import com.chidozie.n.myprojects.dialog.ListDialog;
import com.chidozie.n.myprojects.dialog.TimeDialog;
import com.chidozie.n.myprojects.model.Project;
import com.chidozie.n.myprojects.model.ProjectViewModel;

import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.chidozie.n.myprojects.util.Constants.EXTRA_CATEGORY;
import static com.chidozie.n.myprojects.util.Constants.EXTRA_DATE;
import static com.chidozie.n.myprojects.util.Constants.REQUEST_CATEGORY;
import static com.chidozie.n.myprojects.util.Constants.REQUEST_DATE;
import static com.chidozie.n.myprojects.util.Constants.REQUEST_TIME;

/**
 * Created by Chidozie on 10/7/2018.
 */

public class ProjectDetailsFragment extends Fragment {

    private static final String ARG_PROJECT = "arg_project";
    protected Project project;
    private FragmentProjectDetailsBinding binding;
    private RepositoryViewModel repo;

    public static ProjectDetailsFragment newInstance(Project project) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PROJECT, project);

        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CATEGORY) {
            int category = data.getIntExtra(EXTRA_CATEGORY, 0);

            binding.getViewModel().setImportance(category != 0);
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(EXTRA_DATE);
            binding.getViewModel().setDate(date);
            return;
        }

        if (requestCode == REQUEST_TIME) {
            Date time = (Date) data.getSerializableExtra(EXTRA_DATE);
            binding.getViewModel().setTime(time);
            return;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        project = getArguments().getParcelable(ARG_PROJECT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_details, container, false);
        binding.setLifecycleOwner(this);
        ProjectViewModel viewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        binding.setViewModel(viewModel);
        binding.getViewModel().setProject(project);
        repo = ViewModelProviders.of(this).get(RepositoryViewModel.class);

        // On click listeners
        binding.okButton.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                // save / update project
                onOkClicked();
                getActivity().finish();
            }
        });
        binding.oknewButton.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                // save / update project
                onOkClicked();
                Intent i = ProjectDetailsActivity.newIntent(getActivity(), new Project());
                startActivity(i);
                getActivity().finish();
            }
        });
        binding.backButton.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        binding.dateText.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                // open new date dialog
                FragmentManager manager = getFragmentManager();

                DateDialog dialog = DateDialog.newInstance(project.getDeadlineDate());
                dialog.setTargetFragment(ProjectDetailsFragment.this, REQUEST_DATE);
                dialog.show(manager, "DateDialog");
            }
        });
        binding.timeText.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                // open new time dialog
                FragmentManager manager = getFragmentManager();
                assert manager != null;

                TimeDialog dialog = TimeDialog.newInstance(project.getDeadlineDate());
                dialog.setTargetFragment(ProjectDetailsFragment.this, REQUEST_TIME);
                dialog.show(manager, "TimeDialog");
            }
        });
        binding.importanceText.setOnClickListener(new OnClickListener() {
            {
            }

            @Override
            public void onClick(View view) {
                // open category dialog
                FragmentManager manager = getFragmentManager();
                String[] texts = App.getRes().getStringArray(R.array.category_array);
                ListDialog dialog = ListDialog.newInstance(texts);
                dialog.setTargetFragment(ProjectDetailsFragment.this, REQUEST_CATEGORY);
                dialog.show(manager, "dialog_project");
            }
        });

        return binding.getRoot();
    }


    public void onOkClicked() {
        repo.insert(project);
        getActivity().setResult(RESULT_OK);
    }

}
