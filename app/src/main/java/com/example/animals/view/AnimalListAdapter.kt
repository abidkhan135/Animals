package com.example.animals.view

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private  val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {


    fun updateAnimalList(newanimalList : List<Animal>){
        animalList.clear()
        animalList.addAll(newanimalList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text= animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))

    }

    class AnimalViewHolder (var view: View): RecyclerView.ViewHolder(view)
}