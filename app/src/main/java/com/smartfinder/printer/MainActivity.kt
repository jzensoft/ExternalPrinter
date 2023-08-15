package com.smartfinder.printer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartfinder.printer.databinding.ActivityMainBinding
import com.smartfinder.printermanager.PrinterManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initPax()
        //initNewland()
        //initNexgo()
        initPrinterExternal()
    }

    private fun initPrinterExternal() {
        binding.btnPrinterEpson.setOnClickListener {
            binding.btnPrinterEpson.isEnabled = false
            setResult("Printing...")
            PrinterManager.instance.print(
                this,
                "EPSON",
                "192.168.1.36",
                "CHINESE",
                "TM_T88",
                510,
                "",
                9100,
                false
            )
                .timeout(25, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        binding.btnPrinterEpson.isEnabled = true
                        setResult("Printed${Calendar.getInstance().time.time}")
                    }

                    override fun onError(e: Throwable) {
                        binding.btnPrinterEpson.isEnabled = true
                        setResult(e.message ?: "")
                    }
                })
        }
        binding.btnOther.setOnClickListener {
            binding.btnOther.isEnabled = false
            setResult("")
            PrinterManager.instance.print(
                this,
                "XPrinter",
                "192.168.1.36",
                "",
                "",
                500,
                "",
                9100,
                false
            )
                .timeout(25, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        binding.btnOther.isEnabled = true
                        setResult("Printed${Calendar.getInstance().time.time}")
                    }

                    override fun onError(e: Throwable) {
                        binding.btnOther.isEnabled = true
                        setResult(e.message ?: "")
                    }
                })
        }
    }

    private fun initPax() {
        binding.btnPaxSn.setOnClickListener {
//            setResult(PaxSDKManager.instance.getSN())
        }
        binding.btnPaxCash.setOnClickListener {
//            setResult("${PaxSDKManager.instance.openCashDrawer()}")
        }
        binding.btnPaxNfc.setOnClickListener {
            paxNfc()
        }
        binding.btnPaxPrinter.setOnClickListener {
            binding.btnPaxPrinter.isEnabled = false
            paxPrinter()
        }
    }

    private fun initNewland() {
        binding.btnNewlandSn.setOnClickListener {
//            setResult(NewlandSDKManager.instance.getSN())
        }
        binding.btnNewlandPrinter.setOnClickListener {
            binding.btnNewlandPrinter.isEnabled = false
            newlandPrinter()
        }
        binding.btnNewlandNfc.setOnClickListener {
            binding.btnNewlandNfc.isEnabled = false
            newlandNFC()
        }
    }

    private fun initNexgo() {
        binding.btnNexgoSn.setOnClickListener {
//            setResult("${NEXGOSdkManager.instance.getSN()}")
        }
        binding.btnNexgoPrinter.setOnClickListener {
            binding.btnNexgoPrinter.isEnabled = false
            nexgoPrinter()
        }
        binding.btnNexgoNfc.setOnClickListener {
            binding.btnNexgoNfc.isEnabled = false
            nexgoNFC()
        }
    }

    private fun nexgoPrinter() {
//        NEXGOSdkManager.instance.print(0, "")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : CompletableObserver {
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onComplete() {
//                    binding.btnNexgoPrinter.isEnabled = true
//                    setResult("Printed")
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnNexgoPrinter.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//            })
    }

    private fun nexgoNFC() {
//        NEXGOSdkManager.instance.getNFC()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<String> {
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnNexgoNfc.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//
//                override fun onSuccess(t: String) {
//                    binding.btnNexgoNfc.isEnabled = true
//                    setResult(t)
//                }
//            })
    }

    private fun paxPrinter() {
//        PaxSDKManager.instance.print(0, "")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : CompletableObserver {
//                override fun onSubscribe(d: Disposable) {
//                    compositeDisposable.add(d)
//                }
//
//                override fun onComplete() {
//                    binding.btnPaxPrinter.isEnabled = true
//                    setResult("Printed")
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnPaxPrinter.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//            })
    }

    private fun paxNfc() {
//        PaxSDKManager.instance.getNFCData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<String> {
//                override fun onSubscribe(d: Disposable) {
//                    compositeDisposable.add(d)
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnPaxNfc.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//
//                override fun onSuccess(t: String) {
//                    binding.btnPaxNfc.isEnabled = true
//                    setResult(t)
//                }
//            })
    }

    private fun newlandPrinter() {
//        NewlandSDKManager.instance.print(0, "")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : CompletableObserver {
//                override fun onSubscribe(d: Disposable) {
//                    compositeDisposable.add(d)
//                }
//
//                override fun onComplete() {
//                    binding.btnNewlandPrinter.isEnabled = true
//                    setResult("Printed")
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnNewlandPrinter.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//            })
    }

    private fun newlandNFC() {
//        NewlandSDKManager.instance.getNFC()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<String> {
//                override fun onSubscribe(d: Disposable) {
//                    compositeDisposable.add(d)
//                }
//
//                override fun onError(e: Throwable) {
//                    binding.btnNewlandNfc.isEnabled = true
//                    setResult(e.message ?: "")
//                }
//
//                override fun onSuccess(t: String) {
//                    binding.btnNewlandNfc.isEnabled = true
//                    setResult(t)
//                }
//            })
    }

    private fun setResult(data: String) {
        compositeDisposable.clear()
        binding.tvResult.text = data
    }
}