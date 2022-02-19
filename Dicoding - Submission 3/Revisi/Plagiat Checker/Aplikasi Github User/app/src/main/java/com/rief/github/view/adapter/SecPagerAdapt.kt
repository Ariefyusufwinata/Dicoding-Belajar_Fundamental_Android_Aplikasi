package com.rief.github.view.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rief.R
import com.rief.github.view.fragment.FragmentFollowers
import com.rief.github.view.fragment.FragmentFollowing


class SecPagerAdapt(private val mCtx: Context, fm: FragmentManager, data : Bundle) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundl : Bundle

    init {
        fragmentBundl = data
    }

    @StringRes
    private val tabTittles = intArrayOf(R.string.tab_1, R.string.tab_2)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundl
        return  fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mCtx.resources.getString(tabTittles[position])
    }
}