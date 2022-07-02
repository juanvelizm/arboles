package com.veliz.arboles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnswersAdapter(private var arrAnswers: ArrayList<AnswersModel>, private var handler: AnswerHandler): RecyclerView.Adapter<AnswersAdapter.AnswerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)
        return AnswerHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.render(arrAnswers.get(position))
    }

    override fun getItemCount(): Int {
        return arrAnswers.size
    }

    fun updateData( arr: ArrayList<AnswersModel>){
        arrAnswers = arr
        notifyDataSetChanged()
    }

    inner class AnswerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtItem: TextView = itemView.findViewById(R.id.txtItem)
        fun render(model: AnswersModel) {
            txtItem.setText(model.respuesta)
            txtItem.setOnClickListener { handler.onAnswerSelected(model) }
        }
    }
}