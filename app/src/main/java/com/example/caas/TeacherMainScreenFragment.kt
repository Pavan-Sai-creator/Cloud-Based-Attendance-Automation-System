package com.example.caas

import android.icu.text.AlphabeticIndex
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
import com.example.caas.databinding.FragmentTeacherMainScreenBinding
import com.example.caas.databinding.FragmentTeacherRegistrationBinding
import com.example.caas.databinding.FragmentUserLoginBinding
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class TeacherMainScreenFragment : Fragment() {
    lateinit var binding: FragmentTeacherMainScreenBinding
    private val args by navArgs<TeacherMainScreenFragmentArgs>()

    lateinit var className: String
    lateinit var date: String
    lateinit var subject: String
    lateinit var hour: String
    lateinit var studentId: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherMainScreenBinding.inflate(inflater, container, false)
        val currentTeacherId = args.teacherId


        binding.teacherMainScreenViewAttendanceBtn.setOnClickListener {

            Toast.makeText(context,"Clicked on view attendance button",Toast.LENGTH_SHORT).show()
            className = binding.teacherMainScreenClassEt.text.toString()
            date = binding.teacherMainScreenDt.text.toString()
            subject = binding.teacherMainScreenSubjectEt.text.toString()
            hour = binding.teacherMainScreenHourEt.text.toString()
            studentId = binding.teacherMainScreenStudentIdEt.text.toString()

            Log.d("Values", "$className, $date, $subject, $hour, $studentId")

            var task4 = Task4()
            task4.execute()
        }
        return binding.root
    }



    inner class Task4 : AsyncTask<Void, Void, Void>() {
        lateinit var badaList1: BadaList
        var error = ""

        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://caas.crjbtooqk9d1.ap-south-1.rds.amazonaws.com:3306/caas","admin","Sadguru1520")
                Log.d("Test","OK!")
                var statement : Statement = conn.createStatement()

                    //var resultSet : ResultSet = statement.executeQuery("Select attendance.st_Id,attendance.st_Name, attendance.att_Status From attendance Inner join students on attendance.st_Id = students.st_id Where date = '${date}' and hour = '${hour}' and subject='${subject}' and class='${className}';")
                var resultSet : ResultSet = statement.executeQuery("SELECT attendance.st_Id,attendance.st_Name, attendance.att_Status FROM attendance INNER JOIN students on attendance.st_Id = students.st_Id WHERE date = '${date}' and hour = '${hour}' and subject='${subject}' and class='${className}';")
                var listOfRecords = mutableListOf<Record>()
                    while (resultSet.next()) {
                        val id = resultSet.getString("st_Id")
                        val name = resultSet.getString("st_Name")
                        val attStatus = resultSet.getString("att_Status")
                        Log.d("Test21","$id $name $attStatus")
                        listOfRecords.add(Record(id, name, attStatus))
                    }
                    badaList1 = BadaList(listOfRecords)
                Log.d("MyArray","${badaList1.listOfRecords[0].stName}")


            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if(error != ""){
                Log.d("MyError","$error")
            }else{
                val action = TeacherMainScreenFragmentDirections.actionTeacherMainScreenFragmentToViewAttendanceFragment(badaList1)
                findNavController().navigate(action)
            }
            super.onPostExecute(result)
        }
    }




}