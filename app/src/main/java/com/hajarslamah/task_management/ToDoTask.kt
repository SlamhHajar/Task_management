package com.hajarslamah.task_management

import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.Color.red
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ToDoTask.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToDoTask : Fragment(),InputDialogFragment.Callbacks,DatePickerFragment.Callbacks {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var task:TaskMang

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProviders.of(this).get(TaskViewModel::class.java)
    }
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var newTaskButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         val view=inflater.inflate(R.layout.fragment_to_do_task, container, false)
        newTaskButton = view.findViewById(R.id.add) as FloatingActionButton
        taskRecyclerView = view.findViewById(R.id.task1_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)//to manage the calling of on create and on bind and allow you how to show the UI
        taskRecyclerView.adapter = adapter
        newTaskButton.setOnClickListener {
          //  val task = TaskMang()
            InputDialogFragment().apply{
                setTargetFragment(this@ToDoTask, 0)
                show(this@ToDoTask.requireFragmentManager(), "Input")
   }}


        return view
    }
    private fun updateUI(tasks: List<TaskMang>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
        adapter= taskRecyclerView.adapter as TaskAdapter
    }
    //////////////////////////////////////////////////////////////////Holder to store the data and inflate to item////////////////////////////////
    private inner class TaskHolder(view: View)
        : RecyclerView.ViewHolder(view) , View.OnClickListener{
        private lateinit var task: TaskMang

        val card: CardView = itemView.findViewById(R.id.card)
        val nameTextView: TextView = itemView.findViewById(R.id.task_name)
        val detailsTextView: TextView = itemView.findViewById(R.id.task_details)
        val dateEndTextView: TextView = itemView.findViewById(R.id.end_date)
        val moveButton: ImageButton = itemView.findViewById(R.id.move_task)
        val deletButton: ImageButton = itemView.findViewById(R.id.delete)
        val rebackButton: ImageButton = itemView.findViewById(R.id.reback_task)

        init {
            itemView.setOnClickListener(this)

        }
        fun bind(task: TaskMang) {
            this.task = task

            val expiry_date = this.task.time_end .time
              var current_date=System.currentTimeMillis()
                  val day_counter = expiry_date - current_date
                    val priod = (day_counter / (1000 * 60 * 60 * 24)).toInt()
// Date(expiry_date - System.currentTimeMillis()); // 3 * 24 * 60 * 60 * 1000
            if( priod>3){
                 Toast.makeText(context, " their is more than three day to your task", Toast.LENGTH_SHORT) .show()
            card.setBackgroundResource(R.color.green)
            //setBackgroundColor(Color.GREEN)
            }
            else if(priod==3){
                card.setBackgroundResource(R.color.design_default_color_error)
             //   card.setBackgroundColor(Color.RED)
                Toast.makeText(context, "  3 days to your task", Toast.LENGTH_SHORT) .show()
            }
            else{

                card.setBackgroundResource(R.color.orange)
                Toast.makeText(context, " less than 3 days ", Toast.LENGTH_SHORT) .show()
            }
            deletButton.setOnClickListener {
                taskViewModel.deleteTask(task)
            }
            nameTextView.text = this.task.title_task
            detailsTextView.text=this.task.details
            dateEndTextView.text=  java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(
                this.task.time_end
            ).toString()
            moveButton.setOnClickListener {
                this.task.status_task +=1
                  taskViewModel.updateTask(task)
                //updateView()
                // Toast.makeText(context, " Hi I'm Hajar *__-", Toast.LENGTH_SHORT) .show()
            }
            rebackButton.visibility=View.GONE
                   }

   override fun onClick(p0: View?) {
   Toast.makeText(context, "${task.title_task}!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    ////////////////////////////////////////////////////Adapter using holder//////////////////////////
    private inner class TaskAdapter(var tasks: List<TaskMang>)
        : RecyclerView.Adapter<TaskHolder>() {
        ////////////////////////////// that's fun call once On created to the  first items
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.task_show, parent, false)
            return TaskHolder(view)
        }
        ///////////////////////////////////////////////////Fun @onBindViewHolder  that's call at to connect between tha data exaist on array and the item on holder
        override fun onBindViewHolder(holder: TaskHolder, position: Int) {

            val student=tasks[position]
            holder.apply {
                holder.bind(student)


                }
            }
        ////////////////////////to return  size of array
        override fun getItemCount(): Int {
//            if(tasks.isEmpty()){
//                emptyTextView.visibility=View.VISIBLE
//                newTaskButton.visibility=View.VISIBLE
//            }else{
//                emptyTextView.visibility=View.GONE
//               // newTaskButton.visibility=View.GONE
//            }

            return tasks.size
        }}
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            taskViewModel.taskToDoListLiveData.observe(
                viewLifecycleOwner,
                { tasks ->
                    tasks?.let {

                        updateUI(tasks)

                    }
                })    }

  companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ToDoTask.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ToDoTask().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun addTask(task: TaskMang) {
        taskViewModel.addTask(task)
    }

    override fun onDateSelected(date: Date) {
task.time_end=date
        //updateUI()
    }
}