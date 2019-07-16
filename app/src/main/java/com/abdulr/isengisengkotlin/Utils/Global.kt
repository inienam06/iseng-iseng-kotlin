package com.abdulr.isengisengkotlin.Utils

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Global {
    companion object {
        fun convertPhoneNumber(value: String): String {
            return value.replace("[^0-9\\+]".toRegex(), "")
        }

        fun number_format(value: Int): String {
            val formatter = NumberFormat.getInstance() as DecimalFormat
            val symbols = formatter.decimalFormatSymbols
            symbols.currencySymbol = "" // Don't use null.
            formatter.decimalFormatSymbols = symbols
            return formatter.format(value.toLong())
        }

        fun setNumberPhone(number: String, type: String): String? {
            var newNotelp: String? = null
            var start = ""
            var replace = ""

            when (type) {
                "08" -> {
                    start = "08"
                    replace = "628"
                }

                "628" -> {
                    start = "628"
                    replace = "08"
                }
            }

            if (number.startsWith(start)) {
                newNotelp = number.replaceFirst(start.toRegex(), replace)
            }

            return newNotelp
        }

        fun formatTime(time: Long): String {
            // income time
            val date = Calendar.getInstance()
            date.timeInMillis = time

            // current time
            val curDate = Calendar.getInstance()
            curDate.timeInMillis = java.lang.System.currentTimeMillis()

            var dateFormat: SimpleDateFormat? = null
            if (date.get(Calendar.YEAR) == curDate.get(Calendar.YEAR)) {
                if (date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR)) {
                    dateFormat = SimpleDateFormat("HH:mm", Locale.US)
                } else {
                    dateFormat = SimpleDateFormat("d MMM, HH:mm", Locale.US)
                }
            } else {
                dateFormat = SimpleDateFormat("MMM yyyy", Locale.US)
            }
            return dateFormat.format(time)
        }

        fun listPermission(code: Int): Array<String> {
            val PERMISSION_REQUEST: Array<String>
            val defaultPermission = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.USE_BIOMETRIC,
                Manifest.permission.USE_FINGERPRINT,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )

            when (code) {
                1 -> PERMISSION_REQUEST = arrayOf(defaultPermission[0])

                2 -> PERMISSION_REQUEST = arrayOf(defaultPermission[1], defaultPermission[2])

                3 -> PERMISSION_REQUEST = arrayOf(defaultPermission[3], defaultPermission[4])

                else -> PERMISSION_REQUEST = defaultPermission
            }

            return PERMISSION_REQUEST
        }

        fun isConnected(context: Context): Boolean {
            val connected: Boolean
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifi = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val mobile = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            connected =
                wifi || mobile
            return connected
        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, bundle: Bundle?, anims: Array<Int>?) {
            try {
                val transaction = fragmentManager.beginTransaction()

                if (bundle != null) {
                    fragment.arguments = bundle
                }

                if (anims != null && anims.size > 1) {
                    transaction.setCustomAnimations(anims[0], anims[1])
                }

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, bundle: Bundle) {
            try {
                val transaction = fragmentManager.beginTransaction()
                fragment.arguments = bundle

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        fun setFragment(fragmentManager: FragmentManager, id: Int, fragment: Fragment, TAG: String, anims: Array<Int>) {
            try {
                val transaction = fragmentManager.beginTransaction()

                if (anims.size > 1) {
                    transaction.setCustomAnimations(anims[0], anims[1])
                }

                transaction.replace(id, fragment, TAG).commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        fun customCode(tipe: String, value: String): Bitmap? {
            var bm: Bitmap?
            val matrix: BitMatrix

            try {
                if (tipe.toLowerCase() == "barcode") {
                    matrix = MultiFormatWriter().encode(value, BarcodeFormat.CODE_128, 500, 250)
                } else {
                    matrix = MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, 500, 500)
                }
                val encoder = BarcodeEncoder()
                bm = encoder.createBitmap(matrix)
            } catch (e: WriterException) {
                e.printStackTrace()
                bm = null
            }

            return bm
        }
    }
}