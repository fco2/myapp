package com.example.myapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FriendRequestFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var requestList: List<Users>? = null
    private var mDatabaseReference: DatabaseReference? = null
    private val mUsersDatabase: DatabaseReference? = null
    private val mMessageDatabase: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var firebaseUser: String? = null
    private var mRequestAdapter: RequestAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_friend_request, container, false)
        recyclerView = view.findViewById(R.id.request_list)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Receive")

        requestList = ArrayList()
        friendRequest()
        return view
    }



    private fun friendRequest() {

        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (requestList as ArrayList<Users>).clear()
                for (snapshot in p0.children){
                    val user: Users = snapshot.getValue(Users::class.java)!!
                    if (firebaseUser != user.uid){
                        (requestList as ArrayList<Users>).add(user)
                    }
                }



                mRequestAdapter = RequestAdapter(context!!,requestList!!,false)
                recyclerView?.adapter = mRequestAdapter

            }


        })



    }




}
