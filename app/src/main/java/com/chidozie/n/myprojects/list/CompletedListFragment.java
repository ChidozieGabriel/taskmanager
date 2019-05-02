package com.chidozie.n.myprojects.list;

import android.arch.lifecycle.LiveData;

import com.chidozie.n.myprojects.R;
import com.chidozie.n.myprojects.model.Project;
import com.chidozie.n.myprojects.util.Constants;

import java.util.List;

/**
 * Created by Chidozie on 10/10/2018.
 */

public class CompletedListFragment extends ProjectListFragment {

    @Override
    protected LiveData<List<Project>> query() {
        return repo.getCompletedProjects();
    }

    @Override
    protected int type() {
        return Constants.COMPLETED_LIST;
    }

    @Override
    protected String emptyText() {
        return getString(R.string.empty_completed);

    }
}