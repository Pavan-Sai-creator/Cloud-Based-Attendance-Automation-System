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
import com.example.caas.databinding.FragmentStudentMainScreenBinding
import com.example.caas.databinding.FragmentStudentRegistrationBinding
import java.sql.DriverManager
import java.sql.Statement


class StudentRegistrationFragment : Fragment() {

    lateinit var binding: FragmentStudentRegistrationBinding
    lateinit var studentId: String
    lateinit var studentName: String
    lateinit var studentEmail: String
    lateinit var studentPassword: String
    lateinit var studentClass: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentRegistrationBinding.inflate(inflater,container,false)
        Toast.makeText(context,"This is student registration fragment", Toast.LENGTH_SHORT).show()

        binding.studentRegistrationRegisterBtn.setOnClickListener {
            Toast.makeText(context,"Register Button Clicked",Toast.LENGTH_SHORT).show()
            studentId = binding.studentRegistrationIdEt.text.toString()
            studentName = binding.studentRegistrationNameEt.text.toString()
            studentEmail = binding.studentRegistrationEmailEt.text.toString()
            studentPassword = binding.studentRegistrationPasswordEt.text.toString()
            studentClass = binding.studentRegistrationClassEt.text.toString()

            Log.d("Values","$studentId, $studentName, $studentEmail, $studentPassword, $studentClass")


            var task3 = Task3()
            task3.execute()
        }

        return binding.root
    }

    inner class Task3 : AsyncTask<Void, Void, Void>() {
        var records = ""
        var error = ""

        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://caas.crjbtooqk9d1.ap-south-1.rds.amazonaws.com:3306/caas","admin","Sadguru1520")
                Log.d("Test","OK!")
                var statement : Statement = conn.createStatement()
                statement.executeUpdate("INSERT INTO students (st_Id, st_Name, st_Email, st_Password, st_Class) VALUES ('$studentId', '$studentName', '$studentEmail', '$studentPassword', '$studentClass');")

            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            //text.text = records
            Log.d("Test","$records")
            if(error != ""){
                Toast.makeText(context,"Error connecting to Database!",Toast.LENGTH_LONG).show()
                Log.d("Test","$error")
                Toast.makeText(context,"$error",Toast.LENGTH_LONG).show()
            }else{
                val action = StudentRegistrationFragmentDirections.actionStudentRegistrationFragmentToStudentMainScreenFragment("${studentId}")
                findNavController().navigate(action)
            }
            super.onPostExecute(result)
        }
    }


}