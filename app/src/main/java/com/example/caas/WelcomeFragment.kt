package com.example.caas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.caas.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        binding.welcomeScreenNextBtn.setOnClickListener {
            if(binding.radioTeacher.isChecked){
                //Toast.makeText(context,"Teacher",Toast.LENGTH_SHORT).show()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToUserLoginFragment("Teacher")
                findNavController().navigate(action)
            }
            else if(binding.radioStudent.isChecked){
                //Toast.makeText(context,"Student",Toast.LENGTH_SHORT).show()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToUserLoginFragment("Student")
                findNavController().navigate(action)
            }
            else{
                Toast.makeText(context,"Please select a role",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}