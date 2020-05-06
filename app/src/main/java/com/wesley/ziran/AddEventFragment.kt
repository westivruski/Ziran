package com.wesley.ziran



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


import java.util.*


private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0


class AddEventFragment : Fragment(), DatePickerFragment.Callbacks{

    private lateinit var event: Event
    private lateinit var etEventTitle : EditText
    private lateinit var btEventDate : Button
    private lateinit var cbEventExpired : CheckBox
    private lateinit var btSubmitEvent : Button

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this).get(EventDetailViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event = Event()




     /*   btEventDate.setOnClickListener {
            DatePickerFragment.newInstance(event.date).apply  {
                setTargetFragment(this@AddEventFragment, REQUEST_DATE)
                show(this@AddEventFragment.parentFragmentManager, DIALOG_DATE)
            }
        }*/
        btSubmitEvent.setOnClickListener {
            eventDetailViewModel?.addEvent(event)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        etEventTitle = view?.findViewById(R.id.et_event_name) as EditText
        btEventDate = view?.findViewById(R.id.bt_event_date) as Button
        cbEventExpired= view?.findViewById(R.id.cb_event_expired) as CheckBox
        btSubmitEvent= view?.findViewById(R.id.bt_event_submit) as Button

        return view
    }


    override fun onDateSelected(date: Date) {
        event.date = date
        updateUI()
    }

    private fun updateUI() {
        etEventTitle.setText(event.name)
        btEventDate.text = event.date.toString()
        cbEventExpired.apply {
            isChecked = event.isExpired
            jumpDrawablesToCurrentState()
        }
    }



    companion object {
        fun newInstance(crimeId: UUID): AddEventFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return AddEventFragment().apply {
                arguments = args
            }
        }
    }
}