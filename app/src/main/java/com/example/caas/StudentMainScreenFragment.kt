package com.example.caas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.caas.databinding.FragmentStudentMainScreenBinding

class StudentMainScreenFragment : Fragment() {

    lateinit var binding: FragmentStudentMainScreenBinding
    private val args by navArgs<StudentMainScreenFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentMainScreenBinding.inflate(inflater,container,false)
        val currentStudentId = args.studentId
        Toast.makeText(context,"StudentID: $currentStudentId",Toast.LENGTH_LONG).show()


        return binding.root
    }


}