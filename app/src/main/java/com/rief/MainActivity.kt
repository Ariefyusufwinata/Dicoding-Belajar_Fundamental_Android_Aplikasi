package com.rief

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rief.adapater.ListUserGithubAdapter
import com.rief.model.UserGithub

class MainActivity : AppCompatActivity(), ListUserGithubAdapter.OnItemClickCallback {
    private lateinit var rvUserGithub : RecyclerView
    private val listUser = arrayListOf<UserGithub>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUserGithub = findViewById(R.id.rv_users)
        rvUserGithub.setHasFixedSize(true)
        listUser.addAll(listUserGithub)
        showRecyclerList()
    }

    private val listUserGithub : ArrayList<UserGithub>
        @SuppressLint("Recycle")
        get(){
            val dataUsername = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val listUsers = arrayListOf<UserGithub>()
            for (i in dataUsername.indices){
                val user = UserGithub(
                    dataUsername[i],
                    dataName[i],
                    dataLocation[i],
                    dataRepository[i],
                    dataCompany[i],
                    dataFollowers[i],
                    dataFollowing[i],
                    dataAvatar.getResourceId(i, -1)
                )
                listUsers.add(user)
            }
            dataAvatar.recycle()
            return listUsers
        }

    private fun showRecyclerList() {
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUserGithub.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUserGithub.layoutManager = LinearLayoutManager(this)
        }

        val listUserGithubAdapter = ListUserGithubAdapter()
        rvUserGithub.adapter = listUserGithubAdapter
        listUserGithubAdapter.setData(listUser)
        listUserGithubAdapter.setOnItemClickCallback(object: ListUserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithub) {}
        })
    }

    override fun onItemClicked(data: UserGithub) {}
}
