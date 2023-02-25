package com.example.caas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.caas.databinding.FragmentTeacherRegistrationBinding
import com.example.caas.databinding.FragmentViewAttendanceBinding


class ViewAttendanceFragment : Fragment() {

    lateinit var binding: FragmentViewAttendanceBinding
    private val args by navArgs<ViewAttendanceFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewAttendanceBinding.inflate(inflater,container, false)

        Toast.makeText(context,"In view att fragment", Toast.LENGTH_SHORT).show()
        var myAttendanceRecords = args.attendanceRecords
        var record1 = "${myAttendanceRecords.listOfRecords[0].stID} ${myAttendanceRecords.listOfRecords[0].stName} ${myAttendanceRecords.listOfRecords[0].attStatus}"
        var record2 = "${myAttendanceRecords.listOfRecords[1].stID} ${myAttendanceRecords.listOfRecords[1].stName} ${myAttendanceRecords.listOfRecords[1].attStatus}"

        var finalList = "${record1}\n\n${record2}"

        binding.viewAttendanceFinalListTv.text = finalList


        Log.d("viewAttendanceFragment","${myAttendanceRecords.listOfRecords[0].stID} ${myAttendanceRecords.listOfRecords[0].stName} ${myAttendanceRecords.listOfRecords[0].attStatus}")

        return binding.root
    }

}