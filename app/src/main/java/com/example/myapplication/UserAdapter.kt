package com.example.myapplication

import android.content.Context
import android.content.Intent.getIntent
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val mcontext: Context, private val mUsers:List<Users>,
                  private val isChatCheck:Boolean):RecyclerView.Adapter<UserAdapter.ViewHolder?>()
{
    private var firebaseUser: String? = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mcontext).inflate(R.layout.user_search_item_layout,viewGroup,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val user: Users = mUsers[i]
        holder.usernameTxt.text = user.username
        Picasso.get().load(user.profile).placeholder(R.drawable.blank_profile_picture).into(holder.profileImageView)

        holder.addFriendButton.setOnClickListener{
            if (holder.addFriendButton.text.toString()== "Add Friend"){
                firebaseUser?.let {it ->
                    FirebaseDatabase.getInstance().reference
                        .child("Add Friend").child(user.uid)
                        .child("Receive").child(it)
                        .setValue(true)

                }
                holder.addFriendButton.text = "Cancel"
            }


            else {
                firebaseUser?.let {it ->
                    FirebaseDatabase.getInstance().reference
                        .child("Add Friend").child(user.uid)
                        .child("Receive").child(it)
                        .removeValue()

                }
                holder.addFriendButton.text = "Add Friend"


            }

            if (firebaseUser != user.uid){
                return@setOnClickListener
            }
            else{
                holder.addFriendButton.visibility = INVISIBLE
            }




        }


    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var usernameTxt: TextView = itemView.findViewById(R.id.activity_user_item_layout_username)
        var profileImageView: CircleImageView = itemView.findViewById(R.id.activity_user_item_layout_ProfileImage)
        var onlineImageView: CircleImageView = itemView.findViewById(R.id.activity_user_item_layout_online)
        var offlineImageView: CircleImageView = itemView.findViewById(R.id.activity_user_item_layout_offline)
        var lastMessageTxt: TextView = itemView.findViewById(R.id.activity_user_item_layout_lastMessage)
        var addFriendButton: Button = itemView.findViewById(R.id.add_friend)

    }




}