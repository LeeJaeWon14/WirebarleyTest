package com.jeepchief.wirebarleytest

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.jeepchief.wirebarleytest.databinding.ActivityMainBinding
import com.jeepchief.wirebarleytest.model.LiveDTO
import com.jeepchief.wirebarleytest.viewmodel.MainViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var liveDTO: LiveDTO
    private val mainVM: MainViewModel by viewModels()
    private val countryList = listOf("한국 (KRW)", "일본 (JPY)", "필리핀 (PHP)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = ViewModel()

        mainVM.run {
            getExchangeRate()
            exchangeRate.observe(this@MainActivity) {
                liveDTO = it

                binding.apply {
                    spCountry.apply {
                        val spinnerAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, countryList)
                        adapter = spinnerAdapter
                        setSelection(0)
                        onItemSelectedListener = this@MainActivity
                    }
                }
            }
        }
    }

    inner class ViewModel {
        val textField = ObservableField<String>()

        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s?.toString() == "") {
                binding.apply {
                    tvMessage.isVisible = false
                    tvResult.isVisible = false
                }
                return
            }

            val receptionCode = binding.tvExchangeRate.text.toString().split(" ")[1]
            val resultAmount = s?.toString()?.toDouble()!! * getExchangeRate(receptionCode)
            binding.tvResult.text = String.format(
                getString(R.string.exchange_result),
                makeComma(resultAmount),
                receptionCode
            )

            var text = s?.toString()!!
            if(text.toInt() in 1 until 10000) {
                binding.apply {
                    tvMessage.isVisible = false
                    tvResult.isVisible = true
                }
            }
            else {
                binding.apply {
                    tvResult.isVisible = false
                    tvMessage.isVisible = true
                }
            }
        }

        private fun getExchangeRate(receptionCode: String) : Double {
            return when(receptionCode) {
                "KRW" -> liveDTO.quotes.USDKRW
                "JPY" -> liveDTO.quotes.USDJPY
                "PHP" -> liveDTO.quotes.USDPHP
                else -> 0.0
            }
        }
    }

    private fun makeComma(price : Double) : String {
        val formatter = DecimalFormat("#,##0.00")
        return formatter.format(price)
    }

    override fun onItemSelected(view: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        binding.apply {
            tvMessage.isVisible = false
            tvResult.isVisible = false
            edtInputAmount.setText("")
        }
        view?.selectedItem.toString().also {
            binding.apply {
                tvReception.text = it
                tvSearchTime.text = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date(System.currentTimeMillis()))
            }
            when(view?.selectedItem.toString().split(" ")[0]) {
                "한국" -> {
                    binding.tvExchangeRate.text = String.format(
                        getString(R.string.exchange_rate),
                        makeComma(liveDTO.quotes.USDKRW),
                        "KRW"
                    )
                }
                "일본" -> {
                    binding.tvExchangeRate.text = String.format(
                        getString(R.string.exchange_rate),
                        makeComma(liveDTO.quotes.USDJPY),
                        "JPY"
                    )
                }
                "필리핀" -> {
                    binding.tvExchangeRate.text = String.format(
                        getString(R.string.exchange_rate),
                        makeComma(liveDTO.quotes.USDPHP),
                        "PHP"
                    )
                }
                else -> {}
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        /* no-op */
    }
}