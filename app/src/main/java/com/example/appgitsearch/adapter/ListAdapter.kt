package com.example.appgitsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgitsearch.databinding.ContainerBinding
import com.example.appgitsearch.model.Repository

class ListAdapter(private val listAdapter: List<Repository>,
private val onClicked: (Repository) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = ContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ListViewHolder -> {
                holder.bind(listAdapter[position], onClicked)
            }
        }
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    class ListViewHolder constructor(
        itemView: ContainerBinding
    ) : RecyclerView.ViewHolder(itemView.root){
        var repositoryName = itemView.textName
        var repositoryLanguage = itemView.textLinguages

         fun bind(repository: Repository, onClicked: (Repository) -> Unit){
             repositoryName.text = repository.name
             repositoryLanguage.text = repository.language

             itemView.setOnClickListener {
                 onClicked(repository)
             }
         }
    }

}