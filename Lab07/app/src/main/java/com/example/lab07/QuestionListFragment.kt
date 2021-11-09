package com.example.lab07

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab07.models.MyViewModel


class QuestionListFragment : Fragment(), DataAdapter.OnItemClickListener, DataAdapter.OnDeleteButtonClickListener{
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val layout = inflater.inflate(R.layout.fragment_question_list, container, false)
        Log.d("xxx", "QuestionListFragment - onCreateView")
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val adapter = DataAdapter(viewModel.getList(), this, this)
        val recyclerView : RecyclerView = layout.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)

        return layout
    }

    override fun onItemClick(position: Int) {
        viewModel.currentPosition = position
        findNavController().navigate(R.id.action_questionListFragment_to_questionDetailFragment)
        Log.d("xxx", "AdapterPosition: $position")
    }

    override fun onDeleteClick(position: Int) {
        viewModel.deleteQuestion(position)

    }

}