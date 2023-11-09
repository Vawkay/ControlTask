package com.example.controltask.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.controltask.R
import com.example.controltask.databinding.FragmentFormTaskBinding
import com.example.controltask.model.Task


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task

    private var newTask: Boolean = true

    private var statusTask: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { validateDate() }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            statusTask = when(id) {
                R.id.rbTodo -> 0
                R.id.rbDoing -> 1
                else -> 2
            }
        }
    }

    private fun validateDate () {
        val description = binding.edtDescription.text.toString().trim()

        if (description.isNotEmpty()) {

            binding.progressBar.isVisible = true

            if(newTask) task = Task()
            task.description = description
            task.status = statusTask

            saveTask()

        } else {
            Toast.makeText(
                requireContext(),
                "Informe uma descrição para a tarefa.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveTask(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}