package com.example.appgitsearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appgitsearch.R
import com.example.appgitsearch.databinding.FragmentSearchBinding
import com.example.appgitsearch.model.User
import com.example.appgitsearch.net.EndPointPath
import com.example.appgitsearch.net.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentSearch : Fragment(), View.OnClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        if(view.id == R.id.buttonSearch){
            getData()
        }
    }

    private fun getData() {
        val retrofitBase = NetworkUtils.getRetrofitInstance()
        val endPointPath = retrofitBase.create(EndPointPath::class.java)
        val callback = endPointPath.getUsers(binding.editTextName.text.toString())

        callback.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(requireContext(), "Não funciona", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    var action = FragmentSearchDirections.actionFragmentSearchToFragmentProfile(it)
                    findNavController().navigate(action)
                }
            }
        })
    }
}