package com.example.qhatu.ui.mainflow.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.Meeting
import com.example.qhatu.ui.model.UserInfo
import com.example.qhatu.viewmodel.RequestMeetingViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_request_meeting.*


class RequestMeetingFragment : DialogFragment() {

    private lateinit var model: RequestMeetingViewModel

    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.fragment_request_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestMeetingCloseIcon.setOnClickListener {
            dialog?.dismiss()
        }

        model = ViewModelProvider((activity as MainActivity)).get(RequestMeetingViewModel::class.java)

        model.setMeetingDateList()



        model.getDateMeetings().observe(viewLifecycleOwner, Observer<MutableList<String>> { newDateMeeting ->
            var arr = mutableListOf<String>("asd", "123123")

            val array_adapter = ArrayAdapter((activity as MainActivity), android.R.layout.simple_spinner_item, newDateMeeting)
            array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spiMeetingDate.adapter = array_adapter
        })


    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


        return dialog
    }




}