package com.example.caas

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.caas.databinding.FragmentUserLoginBinding
import com.example.caas.databinding.FragmentWelcomeBinding
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class UserLoginFragment : Fragment() {
    lateinit var binding: FragmentUserLoginBinding
    private val args by navArgs<UserLoginFragmentArgs>()
    lateinit var email: String
    lateinit var password: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserLoginBinding.inflate(inflater,container,false)

        if(args.userRole == "Teacher")
        {
            binding.userLoginLoginBtn.setOnClickListener {
                Toast.makeText(context,"${args.userRole}",Toast.LENGTH_SHORT).show()
                email = binding.userLoginEmailEt.text.toString()
                password = binding.userLoginPasswordEt.text.toString()
                verifyTeacher()
            }
            binding.userLoginRegisterClickableTv.setOnClickListener {
                findNavController().navigate(R.id.action_userLoginFragment_to_teacherRegistrationFragment)
            }

        }else if(args.userRole == "Student"){
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

    fun verifyTeacher(){
        var task = TaskUserLoginFragment()
        task.execute()
    }
    fun verifyStudent(email: String,password: String): String{
        // WRITE CODE TO VERIFY STUDENT WITH SQL QUERY TO THE TEACHER TABLE IN AWS RDS DATABASE
        /* if(dataFound){return id.toString()} else return null */
        return "RA1911028010071"
    }

    inner class TaskUserLoginFragment : AsyncTask<Void, Void, Void>() {
        var currentTeacherID = ""
        var error = ""

        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://caas.crjbtooqk9d1.ap-south-1.rds.amazonaws.com:3306/caas","admin","Sadguru1520")
                Log.d("Test","OK!")
                var statement : Statement = conn.createStatement()
                var resultSet : ResultSet = statement.executeQuery("select t_Id from Teachers where t_Email = '$email' and t_Password = '$password'")
                while (resultSet.next()){
                    currentTeacherID += resultSet.getString(1)
                }
            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if(error != ""){
                Log.d("Test","Error: $error")
            }else{
                  Log.d("Test","Connected to database successfully")
                if(currentTeacherID!=""){
                    Log.d("Test","Email ID verified successfully")
                    Log.d("Test","Current teacher ID is: $currentTeacherID")
                    val action = UserLoginFragmentDirections.actionUserLoginFragmentToTeacherMainScreenFragment(currentTeacherID)
                    findNavController().navigate(action)
                }else{
                    Toast.makeText(context,"Email ID doesn't exist!",Toast.LENGTH_SHORT).show()
                    Log.d("Test","Email ID doesn't exist!")
                }
            }
            super.onPostExecute(result)
        }
    }

}