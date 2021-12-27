package com.rief

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.rief.model.UserGithub

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val listUser : UserGithub = intent.getParcelableExtra(EXTRA_DATA)!!

        val imageAvatar : ImageView = findViewById(R.id.imgAvatarDetail)
        val tvName : TextView = findViewById(R.id.tvNameDetail)
        val tvUsername : TextView = findViewById(R.id.tvUsernameDetail)
        val tvFollowers : TextView = findViewById(R.id.tvFollowersDetail)
        val tvFollowing : TextView = findViewById(R.id.tvFollowingDetail)
        val tvRepository : TextView = findViewById(R.id.tvRepositoryDetail)
        val tvCompany : TextView = findViewById(R.id.tvCompanyDetail)
        val tvLocation : TextView = findViewById(R.id.tvLocationDetail)

        imageAvatar.setImageResource(listUser.avatar)
        tvName.text = listUser.name
        tvUsername.text = "Username : " + listUser.username
        tvFollowers.text = "Followes : " + listUser.followers
        tvFollowing.text = "Following : " + listUser.following
        tvRepository.text = "Repository : " + listUser.repository
        tvCompany.text = "Company : " + listUser.company
        tvLocation.text = "Location : " + listUser.location

    }

    companion object {
        var EXTRA_DATA = "0"
    }
}
