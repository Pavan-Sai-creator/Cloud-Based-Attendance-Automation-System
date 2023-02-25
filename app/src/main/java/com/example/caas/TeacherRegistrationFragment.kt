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
import com.example.caas.databinding.FragmentStudentRegistrationBinding
import com.example.caas.databinding.FragmentTeacherRegistrationBinding
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class TeacherRegistrationFragment : Fragment() {

    lateinit var binding: FragmentTeacherRegistrationBinding
    lateinit var teacherId: String
    lateinit var teacherName: String
    lateinit var teacherEmail: String
    lateinit var teacherPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherRegistrationBinding.inflate(inflater,container, false)
        Toast.makeText(context,"This is teacher registration fragment", Toast.LENGTH_SHORT).show()

        binding.teacherRegistrationRegisterBtn.setOnClickListener {
            Toast.makeText(context,"Register Button Clicked",Toast.LENGTH_SHORT).show()
            teacherId = binding.teacherRegistrationIdEt.text.toString()
            teacherName = binding.teacherRegistrationNameEt.text.toString()
            teacherEmail = binding.teacherRegistrationEmailEt.text.toString()
            teacherPassword = binding.teacherRegistrationPasswordEt.text.toString()

            Log.d("Values","$teacherId, $teacherName, $teacherEmail, $teacherPassword")


            var task = Task()
            task.execute()
        }


        return binding.root
    }

    inner class Task : AsyncTask<Void, Void, Void>() {
        var records = ""
        var error = ""

        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://caas.crjbtooqk9d1.ap-south-1.rds.amazonaws.com:3306/caas","admin","Sadguru1520")
                Log.d("Test","OK!")
                var statement : Statement = conn.createStatement()
                statement.executeUpdate("INSERT INTO Teachers (t_Id, t_Name, t_Email, t_Password) VALUES ('$teacherId', '$teacherName', '$teacherEmail', '$teacherPassword');")

            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            //text.text = records
            Log.d("Test","$records")
            if(error != ""){
                Log.d("Test","$error")
            }else{
                val action = TeacherRegistrationFragmentDirections.actionTeacherRegistrationFragmentToTeacherMainScreenFragment("${teacherId}")
                findNavController().navigate(action)
            }
            super.onPostExecute(result)
        }
    }


}