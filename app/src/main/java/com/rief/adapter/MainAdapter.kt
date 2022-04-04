package com.rief.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rief.databinding.CounterUserBinding
import com.rief.model.ModelUser
class MainAdapter : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {
	
	private val dataUser = ArrayList<ModelUser>()
	private var onItemClickCallback: OnItemClickCallback? = null

	interface OnItemClickCallback{
		fun onItemClicked(data: ModelUser)
	}
	
	fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback
	}
	
	fun setList(users : ArrayList<ModelUser>){
		dataUser.clear()
		dataUser.addAll(users)
		notifyDataSetChanged()
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
		val view = CounterUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return UserViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
		holder.bindingUser(dataUser[position])
	}
	
	override fun getItemCount(): Int = dataUser.size
	
	inner class UserViewHolder(private val binding: CounterUserBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bindingUser(user : ModelUser) {
			binding.apply{
				root.setOnClickListener {
					onItemClickCallback?.onItemClicked(user)
				}
				
				Glide.with(itemView)
					.load(user.avatar)
					.centerCrop()
					.into(imageUser)
				tvUsername.text = user.username
			}
		}
	}
}

