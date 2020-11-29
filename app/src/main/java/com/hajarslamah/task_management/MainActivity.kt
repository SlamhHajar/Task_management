package com.hajarslamah.task_management


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

lateinit var tabLayout: TabLayout
lateinit var tabViewPager: ViewPager2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        tabViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        tabLayout=findViewById(R.id.tabs)
        tabViewPager=findViewById(R.id.pager)
        tabViewPager.adapter = object: FragmentStateAdapter(this){
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    // deleted the sconde and third and  add new instance from the oneFragment
                    0 ->ToDoTask.newInstance("TODO","")
                    1->PrograssTask.newInstance("second","")
                    2->DoneTask.newInstance("third","")
                    else ->ToDoTask.newInstance("first","")
                }
            }
            override fun getItemCount(): Int {
                return 3
            }

        }
        TabLayoutMediator(tabLayout,tabViewPager){ tab ,postion ->
            when (postion){
                0 ->{
                    tab.setText("TODO")
                    tab.setIcon(R.drawable.ic_baseline_list_alt_24)}
                1->{tab.setText("Prograss")
                    tab.setIcon(R.drawable.ic_baseline_flip_camera_android_24)}
                2->{tab.setText("Done")
                    tab.setIcon(R.drawable.ic_baseline_done_outline_24)}
                else -> null
            }

        }.attach()



//        val mFab = findViewById<FloatingActionButton>(R.id.fab)
//        mFab.setOnClickListener {
//            Toast.makeText(this@MainActivity, "FAB is clicked...", Toast.LENGTH_LONG).show()
//        }

    }


}