package com.example.appgitsearch.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appgitsearch.R
import com.example.appgitsearch.adapter.ListAdapter
import com.example.appgitsearch.databinding.FragmentProfileBinding
import com.example.appgitsearch.model.Repository
import com.example.appgitsearch.net.EndPointPath
import com.example.appgitsearch.net.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentProfileArgs by navArgs()
    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
        setImagePerfil()
    }

    private fun loadView() {
        binding.textNameUser.text = args.user.name
        getData()
    }

    private fun getData() {
        val retrofitBase = NetworkUtils.getRetrofitInstance()
        val endPointPath = retrofitBase.create(EndPointPath::class.java)
        val callback = endPointPath.getRepos(args.user.login)

        callback.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(requireContext(), "Não funciona", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                response.body()?.let {
                    setAdapter(it)
                }
            }
        })
    }

    private fun setAdapter(listRepos: List<Repository>) {
        listAdapter = ListAdapter(listRepos) {
            opnGitHub(it)
        }

        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun opnGitHub(repository: Repository) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repository.htmlUrl))
        startActivity(intent)
    }

    fun setImagePerfil() {
        val media = args.user.avatarUrl
        if (media !== null) {
            Glide.with(this)
                .load(media)
                .into(binding.imageUser)
        } else {
            binding.imageUser.setImageResource(R.drawable.giticon)
        }
    }
}