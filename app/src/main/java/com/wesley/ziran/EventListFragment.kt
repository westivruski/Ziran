package com.wesley.ziran

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "EventListFragment"

//this class will hold the recyclerView with a list of events


class EventListFragment : Fragment(){


    interface Callbacks {
        fun onEventSelected(id: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var eventRecyclerView: RecyclerView
    private var adapter: EventAdapter? = EventAdapter(emptyList())

    private val eventListViewModel: EventListViewModel by lazy {
        ViewModelProvider(this).get(EventListViewModel::class.java)
    }

    companion object {
        fun newInstance(): EventListFragment {
            return EventListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_event -> {
                val event = Event()
                eventListViewModel.addEvent(event)
                callbacks?.onEventSelected(event.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)

        eventRecyclerView  = view.findViewById(R.id.event_recycler_view) as RecyclerView
        eventRecyclerView.layoutManager = LinearLayoutManager(context)
        eventRecyclerView.adapter = adapter
        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    //here we are working with menu add a new event
    //add menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_event_list, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventListViewModel.eventListLiveData.observe(
            viewLifecycleOwner,
            Observer { events ->
                events?.let {
                    Log.i(TAG, "Got crimes ${events.size}")
                    updateUI(events)
                }
            })
    }

    private fun updateUI(events: List<Event>) {
        adapter = EventAdapter(events)
        eventRecyclerView.adapter = adapter
    }

    private inner class EventHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var event: Event
        val nameTV: TextView = itemView.findViewById(R.id.tv_event_name)
        val dateTV: TextView = itemView.findViewById(R.id.tv_event_date)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(event: Event) {
            this.event = event
            nameTV.text = this.event.name
            dateTV.text = this.event.date.toString()
        }
        override fun onClick(v: View) {
            callbacks?.onEventSelected(event.id)
        }
    }

    private inner class EventAdapter(var events: List<Event>)
        : RecyclerView.Adapter<EventHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : EventHolder {
            val view = layoutInflater.inflate(R.layout.list_item_event, parent, false)
            return EventHolder(view)
        }




        override fun onBindViewHolder(holder: EventHolder, position: Int) {
            val event = events[position]
            holder.bind(event)
        }
        override fun getItemCount() = events.size
    }
}