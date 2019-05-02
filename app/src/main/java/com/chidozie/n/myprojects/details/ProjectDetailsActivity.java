package com.chidozie.n.myprojects.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.chidozie.n.myprojects.SingleFragmentActivity;
import com.chidozie.n.myprojects.model.Project;

import static com.chidozie.n.myprojects.util.Constants.EXTRA_PROJECT;

/**
 * Created by Chidozie on 10/7/2018.
 */

public class ProjectDetailsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, @NonNull Project project) {
        Intent intent = new Intent(context, ProjectDetailsActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Project project = getIntent().getParcelableExtra(EXTRA_PROJECT);
        return ProjectDetailsFragment.newInstance(project);
    }
}
