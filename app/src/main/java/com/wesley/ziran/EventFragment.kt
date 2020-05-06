package com.wesley.ziran

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer

import java.util.*

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
class EventFragment : Fragment(), DatePickerFragment.Callbacks{

    private lateinit var event: Event
    private lateinit var etEventTitle : EditText
    private lateinit var btEventDate : Button
    private lateinit var cbEventExpired : CheckBox

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this).get(EventDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event = Event()
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        eventDetailViewModel.loadEvent(crimeId)
    }


    // explicitly inflate fragment views here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        etEventTitle = view.findViewById(R.id.et_event_name) as EditText
        btEventDate = view.findViewById(R.id.bt_event_date) as Button
        cbEventExpired= view.findViewById(R.id.cb_event_expired) as CheckBox


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventDetailViewModel.eventLiveData.observe(
            viewLifecycleOwner,
            Observer { event ->
                event?.let {
                    this.event = event
                    updateUI()
                }
            })
    }

    // this is listener (TextWatcher) that is called on onStart method when view state is restored, we are using only one of its three methods
    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                event.name = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        etEventTitle.addTextChangedListener(titleWatcher)

        cbEventExpired.apply {
            setOnCheckedChangeListener { _, isChecked ->
                event.isExpired = isChecked
            }
        }
        btEventDate.setOnClickListener {
            DatePickerFragment.newInstance(event.date).apply  {
                setTargetFragment(this@EventFragment, REQUEST_DATE)
                show(this@EventFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
    }

    //this will e replaced with a button
    override fun onStop() {
        super.onStop()
        eventDetailViewModel.saveEvent(event)
    }

    override fun onDateSelected(date: Date) {
        event.date = date
        updateUI()
    }

    private fun updateUI() {
        etEventTitle.setText(event.name)
        btEventDate.text = event.date.toString()
       // cbEventExpired.isChecked = event.isExpired
        cbEventExpired.apply {
            isChecked = event.isExpired
            jumpDrawablesToCurrentState()
        }
    }

/*    val args = Bundle().apply {
        putSerializable(ARG_MY_OBJECT, myObject)
        putInt(ARG_MY_INT, myInt)
        putCharSequence(ARG_MY_STRING, myString)
    }*/

    companion object {

        fun newInstance(crimeId: UUID): EventFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return EventFragment().apply {
                arguments = args
            }
        }
    }
}