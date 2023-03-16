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
import kotlin.properties.Delegates


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

        binding.teacherMainScreenUpdateAttendanceBtn.setOnClickListener {
            className = binding.teacherMainScreenClassEt.text.toString()
            date = binding.teacherMainScreenDt.text.toString()
            subject = binding.teacherMainScreenSubjectEt.text.toString()
            hour = binding.teacherMainScreenHourEt.text.toString()
            studentId = binding.teacherMainScreenStudentIdEt.text.toString()

            Log.d("Values", "$className, $date, $subject, $hour, $studentId")

            var task5 = Task5()
            task5.execute()
        }

        binding.generateIntegrityReportBtn.setOnClickListener {
            studentId = binding.teacherMainScreenStudentIdEt.text.toString()
            className = binding.teacherMainScreenClassEt.text.toString()
            var task6 = Task6()
            task6.execute()
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
                //var resultSet: ResultSet = statement.executeQuery("SELECT * FROM attendance WHERE subject='${subject}' and date='${date}' and class='${className}' and hour='${hour}';")
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
                conn.close()


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

    inner class Task5 : AsyncTask<Void, Void, Void>() {
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
                //statement.executeUpdate("UPDATE attendance SET att_Status='1' WHERE date = '${date}' and hour = '${hour}' and subject='${subject}' and class='${className}' and st_Id='${studentId}';")
                statement.executeUpdate("UPDATE attendance SET att_Status='1' WHERE date = '${date}' and hour = '${hour}' and subject='${subject}' and class='${className}' and st_Id='${studentId}';")



            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if(error != ""){
                Log.d("MyError","$error")
            }else{
                Toast.makeText(context,"Updated successfully",Toast.LENGTH_SHORT).show()
            }
            super.onPostExecute(result)
        }
    }

    inner class Task6 : AsyncTask<Void, Void, Void>() {
        //lateinit var badaList1: BadaList
        var error = ""
        var attendanceArray = mutableListOf<String>()
        var avgEnglishAttendance = 0.0f
        var avgMathsAttendance  = 0.0f
        var avgSocialAttendance  = 0.0f
        var avgStudentAttendance = 0.0f


        override fun doInBackground(vararg p0: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                var conn = DriverManager.getConnection(
                    "jdbc:mysql://caas.crjbtooqk9d1.ap-south-1.rds.amazonaws.com:3306/caas","admin","Sadguru1520")
                Log.d("Test","OK!")
                var statement : Statement = conn.createStatement()

                //var resultSet : ResultSet = statement.executeQuery("Select attendance.st_Id,attendance.st_Name, attendance.att_Status From attendance Inner join students on attendance.st_Id = students.st_id Where date = '${date}' and hour = '${hour}' and subject='${subject}' and class='${className}';")
                var resultSet : ResultSet = statement.executeQuery("SELECT attendance.att_Status FROM attendance WHERE st_Id = '${studentId}';")
                //var resultSet: ResultSet = statement.executeQuery("SELECT * FROM attendance WHERE subject='${subject}' and date='${date}' and class='${className}' and hour='${hour}';")
//                var attendanceArray = mutableListOf<String>()
                while (resultSet.next()) {
//                    val id = resultSet.getString("st_Id")
//                    val name = resultSet.getString("st_Name")
                    val attStatus = resultSet.getString("att_Status")
                   // Log.d("Test21"," In while loop: \n $attStatus \n")
                    attendanceArray.add(attStatus)
                }

                var statement2 : Statement = conn.createStatement()

                var resultSet2 : ResultSet = statement2.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'English' and st_Id='R79';")
                var totalNumberOfEnglishClassesConducted = 1
                while(resultSet2.next()){
                     totalNumberOfEnglishClassesConducted = resultSet2.getInt("count")
                  //  Log.d("Test21"," Total english classes: \n $totalNumberOfEnglishClassesConducted \n")
                }

                var statement3 : Statement = conn.createStatement()

                var resultSet3: ResultSet = statement3.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'English' and st_Id='${studentId}' and att_Status=\"1\";")
                var totalNumberOfEnglishClassesAttended = 0
                while(resultSet3.next()){
                     totalNumberOfEnglishClassesAttended = resultSet3.getInt("count")
                   // Log.d("Test21","NUmber of english classes attended: \n $totalNumberOfEnglishClassesAttended \n")
                }

                 avgEnglishAttendance = (totalNumberOfEnglishClassesAttended.toFloat()/totalNumberOfEnglishClassesConducted.toFloat())*100

                var statement4 : Statement = conn.createStatement()

                var resultSet4 : ResultSet = statement4.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'maths' and st_Id='R79';")
                var totalNumberOfMathsClassesConducted = 1
                while(resultSet4.next()){
                    totalNumberOfMathsClassesConducted = resultSet4.getInt("count")
                  //  Log.d("Test21"," Total maths classes: \n $totalNumberOfMathsClassesConducted \n")
                }

                var statement5 : Statement = conn.createStatement()

                var resultSet5: ResultSet = statement5.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'maths' and st_Id='${studentId}' and att_Status=\"1\";")
                var totalNumberOfMathsClassesAttended = 0
                while(resultSet5.next()){
                    totalNumberOfMathsClassesAttended = resultSet5.getInt("count")
                  //  Log.d("Test21","NUmber of social classes attended: \n $totalNumberOfMathsClassesAttended \n")
                }

                 avgMathsAttendance = (totalNumberOfMathsClassesAttended.toFloat()/totalNumberOfMathsClassesConducted.toFloat())*100

                var statement6 : Statement = conn.createStatement()

                var resultSet6 : ResultSet = statement6.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'Social' and st_Id='R79';")
                var totalNumberOfSocialClassesConducted = 1
                while(resultSet6.next()){
                    totalNumberOfSocialClassesConducted = resultSet6.getInt("count")
                   // Log.d("Test21"," Total Social classes: \n $totalNumberOfSocialClassesConducted \n")
                }

                var statement7 : Statement = conn.createStatement()

                var resultSet7: ResultSet = statement7.executeQuery("SELECT COUNT(*) as count FROM attendance WHERE subject = 'Social' and st_Id='${studentId}' and att_Status=\"1\";")
                var totalNumberOfSocialClassesAttended = 0
                while(resultSet7.next()){
                    totalNumberOfSocialClassesAttended = resultSet7.getInt("count")
                   // Log.d("Test21","NUmber of social classes attended: \n $totalNumberOfSocialClassesAttended \n")
                }

                 avgSocialAttendance = (totalNumberOfSocialClassesAttended.toFloat()/totalNumberOfSocialClassesConducted.toFloat())*100

                var totalNumberOfClassesConducted = 0
                var statement8 : Statement = conn.createStatement()
                var resultSet8: ResultSet = statement8.executeQuery("select count(*) as count from attendance where class='${className}' and st_Id='R77';")
                while(resultSet8.next()){
                    totalNumberOfClassesConducted = resultSet8.getInt("count")
                   // Log.d("Test21","Total no.of classes conducted: \n $totalNumberOfClassesConducted \n")
                }
//
//                var totalNumberOfClassesAttended = 0
//                var statement9 : Statement = conn.createStatement()
//                var resultSet9: ResultSet = statement9.executeQuery("select count(*) as count from attendance where class='${className}' and att_Status='1';")
//                while(resultSet9.next()){
//                    totalNumberOfClassesAttended = resultSet9.getInt("count")
//                    Log.d("Test21","Total no.of classes attended: \n $totalNumberOfClassesAttended \n")
//                }
//
//                var avgClassAttendance = (totalNumberOfClassesAttended.toFloat()/totalNumberOfClassesConducted.toFloat())*100

                var totalNumberOfClassesAttendedByStudent = 0
                var statement10 : Statement = conn.createStatement()
                var resultSet10: ResultSet = statement10.executeQuery("select count(*) as count from attendance where class='${className}' and att_Status='1' and st_Id='${studentId}';")
                while(resultSet10.next()){
                    totalNumberOfClassesAttendedByStudent = resultSet10.getInt("count")
                    //Log.d("Test21","Total no.of classes attended by ${studentId}: \n $totalNumberOfClassesAttendedByStudent \n")
                }

                avgStudentAttendance = (totalNumberOfClassesAttendedByStudent.toFloat()/totalNumberOfClassesConducted.toFloat())*100
                Log.d("Test21","Avg student att: ${avgStudentAttendance}")
                //Log.d("Test21","Avg Class att: ${avgClassAttendance}")
//                var avgAttendanceScore = (avgStudentAttendance/avgClassAttendance)*100
//                Log.d("Test21","Average attendance score: ${avgAttendanceScore}")

                conn.close()


            }catch (e: Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if(error != ""){
                Log.d("Test21","Error in post execute: \n $error \n")
            }else{
                var count = 0
                var foundOne = false
                val zerosBetweenOnes = mutableListOf<Int>()
                val numberOfClasses = attendanceArray.size
                for (i in attendanceArray.indices) {
                    if (attendanceArray[i] == "1" || attendanceArray[i] == "2") {
                        if (foundOne) {
                            zerosBetweenOnes.add(count)
                        }
                        count = 0
                        foundOne = true
                    } else if (foundOne) {
                        count++
                    }
                }
                var sumOfFrequencyOfIrregularity = 0.0f
//                for (i in zerosBetweenOnes.indices) {
//                    Log.d("Test21","${zerosBetweenOnes[i]} ")
//                    sumOfFrequencyOfIrregularity += zerosBetweenOnes[i]
//                }
//                Log.d("Test21","Attendance array: ${attendanceArray}")
//                Log.d("Test21","ZeroesBetweenOnes: ${zerosBetweenOnes}")
                for (i in zerosBetweenOnes.indices) {
                    sumOfFrequencyOfIrregularity += zerosBetweenOnes[i]
                }
                //Log.d("Test21","sumOfFrequencyOfIrregularity: ${sumOfFrequencyOfIrregularity}")

                //val avgFrequencyOfIrregularity = (sumOfFrequencyOfIrregularity.toFloat() / (zerosBetweenOnes.size).toFloat())
                //Log.d("Test21","avgFrequencyOfIrregularity: ${avgFrequencyOfIrregularity}")
                val consistencyScore = (100-((sumOfFrequencyOfIrregularity.toFloat()/numberOfClasses.toFloat())*100))
                Log.d("Test21","Consistency Score: ${consistencyScore}")
                //====================================================================================//

                var n = 3
                var sumOfAvgAttendance = avgEnglishAttendance+avgMathsAttendance+avgSocialAttendance;
                var ideal_share : Float = sumOfAvgAttendance/n

                val avg_att_arr = mutableListOf<Float>()
                val diff_arr = mutableListOf<Float>()

                avg_att_arr.add(avgEnglishAttendance)
                avg_att_arr.add(avgMathsAttendance)
                avg_att_arr.add(avgSocialAttendance)

                //Log.d("Test21","Avg Subject att: \n ${avgEnglishAttendance} ${avgMathsAttendance} ${avgSocialAttendance}")

                for (i in avg_att_arr.indices) {
                    val curr_diff: Float
                    if ((avg_att_arr[i] - ideal_share) > 0) {
                        diff_arr.add(avg_att_arr[i] - ideal_share)
                    } else {
                        diff_arr.add(-1 * (avg_att_arr[i] - ideal_share))
                    }
                }

                var curr_total_deviation = 0.0f

                for (i in diff_arr.indices) {
                    curr_total_deviation += diff_arr[i]
                }
                //Log.d("Test22","Current tot dev: \n${curr_total_deviation}")

                val accepted_deviation = 30.0f


                val deviation_difference = curr_total_deviation - accepted_deviation
                //Log.d("Test22","deviation_difference: \n${deviation_difference}")


                var balanceScore = 0.0f
                if(deviation_difference<0){
                    balanceScore = 100-curr_total_deviation
                }else{
                    balanceScore = 100-deviation_difference
                }
                Log.d("Test21","Balance Score: ${balanceScore}")

                //===================================================================================//
                var report = "Consistency Score: ${consistencyScore}\nSubject Balance: ${balanceScore}\nAverage Attendance: ${avgStudentAttendance}"

                    binding.teacherMainScreenIntegrityReportTv.text = report


            }
            super.onPostExecute(result)
        }
    }
}