package com.example.guia2_ejercicio_complementario02

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var txtNumero: EditText
    private lateinit var txtResultado: TextView
    private lateinit var txtHistorial: TextView
    private val historial = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNumero = findViewById(R.id.txtNumero)
        txtResultado = findViewById(R.id.txtResultado)
        txtHistorial = findViewById(R.id.txtHistorial)

        val btnSumar = findViewById<Button>(R.id.btnSumar)
        val btnRestar = findViewById<Button>(R.id.btnRestar)
        val btnMultiplicar = findViewById<Button>(R.id.btnMultiplicar)
        val btnDividir = findViewById<Button>(R.id.btnDividir)
        val btnPorcentaje = findViewById<Button>(R.id.btnPorcentaje)
        val btnCuadrado = findViewById<Button>(R.id.btnCuadrado)
        val btnRaiz = findViewById<Button>(R.id.btnRaiz)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar)

        btnSumar.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                mostrarOperacion(numero, "+", numero, numero + numero)
            }
        }

        btnRestar.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                mostrarOperacion(numero, "-", numero, numero - numero)
            }
        }

        btnMultiplicar.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                mostrarOperacion(numero, "x", numero, numero * numero)
            }
        }

        btnDividir.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                if (numero == 0.0) {
                    txtResultado.text = getString(R.string.mensaje_division_cero)
                } else {
                    mostrarOperacion(numero, "/", numero, numero / numero)
                }
            }
        }

        btnPorcentaje.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                val resultado = numero / 100
                mostrarOperacion("${formato(numero)} / 100 = ${formato(resultado)}")
            }
        }

        btnCuadrado.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                val resultado = numero * numero
                mostrarOperacion("${formato(numero)} ^ 2 = ${formato(resultado)}")
            }
        }

        btnRaiz.setOnClickListener {
            val numero = obtenerNumero()
            if (numero != null) {
                if (numero < 0) {
                    txtResultado.text = getString(R.string.mensaje_raiz_negativa)
                } else {
                    val resultado = sqrt(numero)
                    mostrarOperacion("Raiz de ${formato(numero)} = ${formato(resultado)}")
                }
            }
        }

        btnLimpiar.setOnClickListener {
            txtNumero.text.clear()
            txtResultado.text = getString(R.string.resultado_inicial)
            txtHistorial.text = ""
            historial.clear()
        }
    }

    private fun obtenerNumero(): Double? {
        val texto = txtNumero.text.toString()
        if (texto.isEmpty()) {
            txtResultado.text = getString(R.string.mensaje_ingrese_numero)
            return null
        }
        return texto.toDoubleOrNull()
    }

    private fun mostrarOperacion(numero1: Double, signo: String, numero2: Double, resultado: Double) {
        val operacion = "${formato(numero1)} $signo ${formato(numero2)} = ${formato(resultado)}"
        mostrarOperacion(operacion)
    }

    private fun mostrarOperacion(operacion: String) {
        txtResultado.text = operacion
        historial.add(0, operacion)
        if (historial.size > 5) {
            historial.removeAt(historial.size - 1)
        }
        txtHistorial.text = historial.joinToString("\n")
    }

    private fun formato(numero: Double): String {
        return if (numero % 1 == 0.0) {
            numero.toInt().toString()
        } else {
            String.format("%.2f", numero)
        }
    }
}
