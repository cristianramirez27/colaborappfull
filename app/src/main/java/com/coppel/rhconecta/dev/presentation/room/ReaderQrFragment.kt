package com.coppel.rhconecta.dev.presentation.room

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.views.activities.HomeActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ReaderQrFragment : DialogFragment(), ZXingScannerView.ResultHandler {

    companion object {
        fun newInstance() = ReaderQrFragment()
    }
    private lateinit var mScannerView : ZXingScannerView
    private lateinit var viewModel: ReaderQrViewModel
    private lateinit var parent : HomeActivity
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                initScan()
            } else {
                parent.onBackPressed()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parent = (activity as HomeActivity)
        setStyle(STYLE_NO_TITLE, R.style.FullScreen)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextWrapper = ContextThemeWrapper(requireActivity(), R.style.FullScreen)
        val localInflater = inflater.cloneInContext(contextWrapper)
        return localInflater.inflate(R.layout.fragment_reader_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mScannerView = view.findViewById(R.id.scanner)

        requestPermission()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReaderQrViewModel::class.java)
    }
    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) -> {
                initScan()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun initScan() {
        val formats: MutableList<BarcodeFormat> = ArrayList()
        formats.add(BarcodeFormat.QR_CODE)
        mScannerView.setResultHandler(this)
        mScannerView.isSoundEffectsEnabled = true
        //mScannerView.setAutoFocus(true)
//        mScannerView.setBorderLineLength(150)
//        mScannerView.setBorderStrokeWidth(20)
        mScannerView.setSquareViewFinder(true)
        mScannerView.setFormats(formats)
        mScannerView.startCamera()
    }


    override fun handleResult(rawResult: Result?) {
        Log.e("QRscanner", rawResult?.text ?: "null result")
        rawResult?.let {r ->
            val alf = '/'
          val index : Int? =  r.text.lastIndexOf(alf)
            index?.let {
                Log.e("Code",r.text.substring(it+1))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
//        parent.showToolbar(true)
    }
}