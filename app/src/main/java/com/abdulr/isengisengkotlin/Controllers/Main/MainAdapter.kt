package com.abdulr.isengisengkotlin.Controllers.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.abdulr.isengisengkotlin.Listeners.RecyclerViewItemClickListener
import com.abdulr.isengisengkotlin.Models.M_MainMenu
import com.abdulr.isengisengkotlin.R
import kotlinx.android.synthetic.main.content_main.view.*
import java.util.ArrayList

class MainAdapter(private val activity: MainActivity): RecyclerView.Adapter<MainAdapter.Holder>(), Filterable {
    private lateinit var model: M_MainMenu

    companion object {
        lateinit var listener: RecyclerViewItemClickListener
    }

    init {
        activity.dataFilter = activity.data
        listener = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v: View = LayoutInflater.from(activity).inflate(R.layout.content_main, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return activity.dataFilter.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        model = activity.dataFilter[position]

        holder.tvBody.text = model.getBody()
    }

    fun getModel(position: Int): M_MainMenu {
        return activity.dataFilter[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val title = constraint.toString()

                if (title.isEmpty()) {
                    activity.dataFilter = activity.data
                } else {
                    val filter = ArrayList<M_MainMenu>()

                    for (data in activity.data) {
                        if (data.getBody().toLowerCase().contains(title.toLowerCase())) {
                            filter.add(data)
                        }
                    }

                    activity.dataFilter = filter
                }

                val results = FilterResults()
                results.values = activity.dataFilter

                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                activity.dataFilter = results.values as ArrayList<M_MainMenu>
                notifyDataSetChanged()
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvBody: TextView = itemView.tvBody
        private val llContent: LinearLayout = itemView.llContent

        init {
            llContent.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener.onItemClickListener(v, adapterPosition)
        }
    }
}