/*
 *     Copyright (C) 2018 Tran Le Duy
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.ascii.art.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.duy.ascii.art.favorite.localdata.TextItem;
import com.duy.ascii.art.R;

import java.util.ArrayList;

/**
 * Created by Duy on 09-Aug-17.
 */

public class FavoriteActivity extends AppCompatActivity implements FavoriteContract.View {
    public static final int INDEX = 2;
    protected FavoritePresenter mPresenter;
    protected RecyclerView mRecyclerView;
    protected FavoriteAdapter mAdapter;
    protected ContentLoadingProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(R.string.favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mProgressBar = findViewById(R.id.progress_bar);
        mPresenter = new FavoritePresenter(this, this);
        mAdapter = new FavoriteAdapter(this, mPresenter.getDatabaseHelper());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadData();
    }


    @Override
    public void showProgress() {
        mProgressBar.show();
    }

    @Override
    public void hideProgress() {
        mProgressBar.hide();
    }

    @Override
    public void display(ArrayList<TextItem> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.mPresenter = (FavoritePresenter) presenter;
    }

    @Override
    public void append(TextItem value) {
        mAdapter.add(value);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
