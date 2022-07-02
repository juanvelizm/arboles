package com.veliz.arboles

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veliz.arboles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AnswerHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var answersAdapter: AnswersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutIntro.visibility = View.VISIBLE
        binding.layoutForm.visibility = View.GONE
        setupListeners()
    }

    private fun setupRecyclers() {
        val arrQuestions: ArrayList<QuestionsModel> = fromJson(Toolbox.loadJsonFromAssets(this, "formulario.json")!!)
        val posiblesRespuestas = arrQuestions.first().posiblesRespuestas!!
        answersAdapter = AnswersAdapter(posiblesRespuestas, this)
        with(binding) {
            txtQuestion.setText(arrQuestions.first().pregunta)
            recyclerAnswers.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerAnswers.adapter = answersAdapter
        }
    }

    private fun setupListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                layoutIntro.visibility = View.GONE
                layoutForm.visibility = View.VISIBLE
                setupRecyclers()
            }
        }
    }

    private fun restartForm() {
        with(binding) {
            layoutIntro.visibility = View.VISIBLE
            layoutForm.visibility = View.GONE
        }
    }

    override fun onAnswerSelected(model: AnswersModel) {
        if (!TextUtils.isEmpty(model.mensaje)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Tu resultado")
                .setMessage(model.mensaje)
                .setNegativeButton("Salir") { _, _ ->
                    finish()
                }
                .setNeutralButton("Reiniciar") { _, _ ->
                    restartForm()
                }
                .show()
        } else {
            binding.txtQuestion.setText(model.siguientePregunta?.pregunta)
            answersAdapter.updateData(model.siguientePregunta?.posiblesRespuestas!!)
        }
    }

    inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }

}