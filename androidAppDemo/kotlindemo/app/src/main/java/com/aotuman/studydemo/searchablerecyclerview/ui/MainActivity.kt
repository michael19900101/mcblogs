package com.aotuman.studydemo.searchablerecyclerview.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aotuman.studydemo.R
import com.aotuman.studydemo.searchablerecyclerview.adapter.ExampleAdapter
import com.aotuman.studydemo.searchablerecyclerview.models.WordModel
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import kotlinx.android.synthetic.main.activity_search_rv.*
import java.util.*

class MainActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener, SortedListAdapter.Callback {
    private var mAdapter: ExampleAdapter? = null
    private var mAnimator: Animator? = null
    private var mModels: ArrayList<WordModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_rv)
        setSupportActionBar(tool_bar)
        mAdapter = ExampleAdapter(
            this,
            COMPARATOR,
            ExampleAdapter.Listener { model: WordModel ->
                val message =
                    getString(R.string.model_clicked_pattern, model.rank, model.word)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        )
        mAdapter!!.addCallback(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mAdapter
        val words = resources.getStringArray(R.array.words)
        for (i in words.indices) {
            mModels.add(WordModel(i.toLong(), i + 1, words[i]))
        }
        mAdapter!!.edit()
            .replaceAll(mModels)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_rv, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView =
            MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        val filteredModelList =
            filter(
                mModels,
                query
            )
        mAdapter!!.edit()
            .replaceAll(filteredModelList)
            .commit()
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onEditStarted() {
        if (edit_progress_bar.getVisibility() !== View.VISIBLE) {
            edit_progress_bar.visibility = View.VISIBLE
            edit_progress_bar.alpha = 0.0f
        }
        if (mAnimator != null) {
            mAnimator!!.cancel()
        }
        mAnimator = ObjectAnimator.ofFloat(
            edit_progress_bar,
            View.ALPHA,
            1.0f
        )
        mAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        mAnimator!!.start()
        recycler_view.animate().alpha(0.5f)
    }

    override fun onEditFinished() {
        recycler_view.scrollToPosition(0)
        recycler_view.animate().alpha(1.0f)
        if (mAnimator != null) {
            mAnimator!!.cancel()
        }
        mAnimator = ObjectAnimator.ofFloat(
            edit_progress_bar,
            View.ALPHA,
            0.0f
        )
        mAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        mAnimator!!.addListener(object : AnimatorListenerAdapter() {
            private var mCanceled = false
            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                mCanceled = true
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (!mCanceled) {
                    edit_progress_bar.visibility = View.GONE
                }
            }
        })
        mAnimator!!.start()
    }

    companion object {
        private val COMPARATOR =
            SortedListAdapter.ComparatorBuilder<WordModel>()
                .setOrderForModel(
                    WordModel::class.java
                ) { a: WordModel, b: WordModel ->
                    Integer.signum(a.rank - b.rank)
                }
                .build()

        private fun filter(
            models: List<WordModel>?,
            query: String
        ): List<WordModel> {
            val lowerCaseQuery = query.toLowerCase()
            val filteredModelList: MutableList<WordModel> =
                ArrayList()
            for (model in models!!) {
                val text = model.word.toLowerCase()
                val rank = model.rank.toString()
                if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                    filteredModelList.add(model)
                }
            }
            return filteredModelList
        }
    }
}