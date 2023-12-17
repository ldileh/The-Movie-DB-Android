package com.themoviedb.test.ui.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.databinding.DialogGenreBinding
import com.themoviedb.test.databinding.ItemGenreBinding
import com.themoviedb.test.ui.model.GenreModel
import com.themoviedb.test.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GenreDialog : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: DialogGenreBinding

    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DialogGenreBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            list.configure(viewModel.itemsGenre)

            btnSubmit.setOnClickListener {
                viewModel.setSelectedGenre(genreAdapter.getItemsSelected())
                dismiss()
            }

            btnReset.setOnClickListener {
                viewModel.setSelectedGenre(null)
                dismiss()
            }
        }
    }

    private fun RecyclerView.configure(items: List<GenreModel>){
        genreAdapter = GenreAdapter().apply {
            setItems(items)
        }

        adapter = genreAdapter
    }
}

private class GenreAdapter: RecyclerView.Adapter<GenreAdapter.ViewHolder>(){

    private val mItems = mutableListOf<GenreModel>()

    class ViewHolder(private val binding: ItemGenreBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: GenreModel){
            binding.root.apply {
                text = item.data.name.safe()
                isChecked = item.isSelected

                setOnCheckedChangeListener { _, isChecked ->
                    item.isSelected = isChecked
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    fun setItems(items: List<GenreModel>){
        mItems.apply {
            clear()
            addAll(items)
        }
    }

    fun getItemsSelected(): List<Int> = mItems.filter { it.isSelected }.map { it.data.id.safe() }
}