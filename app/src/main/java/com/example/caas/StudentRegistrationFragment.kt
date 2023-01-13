package com.example.caas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.caas.databinding.FragmentStudentMainScreenBinding
import com.example.caas.databinding.FragmentStudentRegistrationBinding


class StudentRegistrationFragment : Fragment() {

    lateinit var binding: FragmentStudentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentRegistrationBinding.inflate(inflater,container,false)
        Toast.makeText(context,"This is student registration fragment", Toast.LENGTH_SHORT).show()


        return binding.root
    }


}