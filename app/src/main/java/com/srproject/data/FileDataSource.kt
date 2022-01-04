package com.srproject.data

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.srproject.data.models.FileDataModel
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product
import java.io.File
import java.io.FileOutputStream

private const val FILE_NAME = "sweaty_reety.txt"

class FileDataSource {

    fun writeToFile(
        context: Context,
        orders: List<Order>,
        orderPositions: List<OrderPosition>,
        products: List<Product>
    ) {
        val fileDataModel = FileDataModel(orders, orderPositions, products)
        val dataJson = Gson().toJson(fileDataModel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveFileUsingMediaStore(context, dataJson, FILE_NAME)
        } else {
            saveFileToExternalStorage(dataJson, FILE_NAME)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun readFromFile(context: Context, uri: Uri): String? {
        val resolver = context.contentResolver
        uri.run {
            resolver.openInputStream(uri).use {
                it?.readBytes()?.run {
                    val data = String(this)
                    return data
                }
            }
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileUsingMediaStore(context: Context, data: String, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            resolver.openOutputStream(uri, "w").use {
                it?.write(data.toByteArray())
            }
        }
    }

    private fun saveFileToExternalStorage(data: String, fileName: String) {
        val target = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        FileOutputStream(target, false).use {
            it.write(data.toByteArray())
        }
    }
}