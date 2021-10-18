package com.ace1ofspades.alertmodule

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ListAlert(val context: Context) {
    var selected: Any? = null
    var adapter = GroupAdapter<ViewHolder>()
    lateinit var positiveButton: Button
    lateinit var builder: AlertDialog
    lateinit var view: View
    
    fun alert(selectedHandler:(selected:Any, view:View)->Unit?, handler: (picked: Any) -> Unit) {
        view = LayoutInflater.from(context).inflate(R.layout.alert_list_pick, null)
        builder = AlertDialog.Builder(context).setView(view).setTitle("").show()
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
        
        val recycler = builder.findViewById<RecyclerView>(R.id.alert_recycler)
        positiveButton = builder.findViewById(R.id.alert_done_button)
        
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(context, 1)
        
        positiveButton.setOnClickListener {
            if (selected != null) {
                handler(selected!!)
            }
            builder.dismiss()
        }
        adapter.setOnItemClickListener { item, view ->
            selected = item
            if (selected != null) {
                selectedHandler(selected!!,view)
            }
        }
    }
}