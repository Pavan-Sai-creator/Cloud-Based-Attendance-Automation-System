package com.example.caas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.caas.databinding.FragmentUserLoginBinding
import com.example.caas.databinding.FragmentWelcomeBinding


class UserLoginFragment : Fragment() {
    lateinit var binding: FragmentUserLoginBinding
    private val args by navArgs<UserLoginFragmentArgs>()
    lateinit var currentRole: String
    lateinit var email: String
    lateinit var password: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserLoginBinding.inflate(inflater,container,false)
//        var studentRole = false
//        var teacherRole = false

        if(args.userRole == "Teacher")
        {
            //teacherRole = true
            //var verified: Boolean = false

            var teacherID: String? = null
            binding.userLoginLoginBtn.setOnClickListener {
                Toast.makeText(context,"${args.userRole}",Toast.LENGTH_SHORT).show()
                email = binding.userLoginEmailEt.text.toString()
                password = binding.userLoginPasswordEt.text.toString()
                teacherID = verifyTeacher(email,password)
                if(teacherID!=null){
                    val action = UserLoginFragmentDirections.actionUserLoginFragmentToTeacherMainScreenFragment(teacherID.toString())
                    findNavController().navigate(action)
                }else{
                    Toast.makeText(context,"Incorrect credentials!",Toast.LENGTH_SHORT).show()
                }

            }
            binding.userLoginRegisterClickableTv.setOnClickListener {
                findNavController().navigate(R.id.action_userLoginFragment_to_teacherRegistrationFragment)
            }

        }else if(args.userRole == "Student"){
           // studentRole = true
            var studentID: String? = null
            binding.userLoginLoginBtn.setOnClickListener {
                email = binding.userLoginEmailEt.text.toString()
                password = binding.userLoginPasswordEt.text.toString()
                studentID = verifyStudent(email,password)
                if(studentID!=null){
                    val action = UserLoginFragmentDirections.actionUserLoginFragmentToStudentMainScreenFragment(studentID.toString())
                    findNavController().navigate(action)
                }else{
                    Toast.makeText(context,"Incorrect credentials!",Toast.LENGTH_SHORT).show()
                }

            }
            binding.userLoginRegisterClickableTv.setOnClickListener {
                findNavController().navigate(R.id.action_userLoginFragment_to_studentRegistrationFragment)
            }
        }else{
            Toast.makeText(context,"ERROR!",Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    fun verifyTeacher(email: String,password: String): String{
        // WRITE CODE TO VERIFY TEACHER WITH SQL QUERY TO THE TEACHER TABLE IN AWS RDS DATABASE
        /* if(dataFound){return id.toString()} else return null */
        return "RA1911028010071"
    }
    fun verifyStudent(email: String,password: String): String{
        // WRITE CODE TO VERIFY STUDENT WITH SQL QUERY TO THE TEACHER TABLE IN AWS RDS DATABASE
        /* if(dataFound){return id.toString()} else return null */
        return "RA1911028010071"
    }

}