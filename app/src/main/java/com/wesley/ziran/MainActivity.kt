package com.wesley.ziran

import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), EventListFragment.Callbacks  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navigation = findViewById<BottomNavigationView>(R.id.toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val fragment = EventListFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                .commit()
        }


/*
this here is to be edited by default property
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = EventListFragment.newInstance()
            supportFragmentManager                                                                  //Fragment transactions are used to add, remove, attach, detach, or replace fragments in the fragment list.
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }*/
    }

    override fun onEventSelected(id: UUID) {
        val fragment = EventFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


   // val appBarConfiguration = AppBarConfiguration(setOf(R.id.main, R.id.profile))
   private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
       when (menuItem.itemId) {
           R.id.navigation_blog -> {
               val fragment = EventListFragment()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                   .commit()
               return@OnNavigationItemSelectedListener true
           }
           R.id.navigation_second -> {
               val fragment = AddEventFragment()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                   .commit()
               return@OnNavigationItemSelectedListener true
           }
           R.id.navigation_third -> {
               val fragment = EventListFragment()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                   .commit()
               return@OnNavigationItemSelectedListener true
           }
       }
       false
   }

  /*  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                val fragment = FragmentHome.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val fragment = FragmentDashboard()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val fragment = FragmentNotification()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/
}
