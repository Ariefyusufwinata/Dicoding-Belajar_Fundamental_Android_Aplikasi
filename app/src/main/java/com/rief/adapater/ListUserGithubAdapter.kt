package com.rief.adapater

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rief.view.DetailActivity
import com.rief.R
import com.rief.model.UserGithub

class ListUserGithubAdapter : RecyclerView.Adapter<ListUserGithubAdapter.ListViewHolder>() {
    private var temp = arrayListOf<UserGithub>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvName : TextView = itemView.findViewById(R.id.tvName)
        val tvUsername : TextView = itemView.findViewById(R.id.tvUsername)
        val tvRepository : TextView = itemView.findViewById(R.id.tvRepository)
        val imgAvatar : ImageView = itemView.findViewById(R.id.imgAvatar)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(arrayList: ArrayList<UserGithub>){
        temp = arrayList
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_users, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, name, _, repository, _, _, _, avatar) = temp[position]
        holder.imgAvatar.setImageResource(avatar)
        holder.tvName.text = "Name : $name"
        holder.tvUsername.text = "Username : $username"
        holder.tvRepository.text = "Repository : $repository"
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, temp[position])
            holder.itemView.context.startActivity(intent)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(temp[position])
            }
        }
    }

    override fun getItemCount(): Int = temp.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserGithub)
    }
}
