package com.example.caas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.caas.databinding.FragmentTeacherMainScreenBinding
import com.example.caas.databinding.FragmentUserLoginBinding


class TeacherMainScreenFragment : Fragment() {
    lateinit var binding: FragmentTeacherMainScreenBinding
    private val args by navArgs<TeacherMainScreenFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherMainScreenBinding.inflate(inflater,container,false)
        val currentTeacherId = args.teacherId
        Toast.makeText(context,"You are in Teacher Main Screen Fragment, Teacher ID is: $currentTeacherId",Toast.LENGTH_LONG).show()

        return binding.root
    }


}