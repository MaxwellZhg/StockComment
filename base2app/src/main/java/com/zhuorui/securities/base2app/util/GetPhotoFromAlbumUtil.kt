package com.zhuorui.securities.base2app.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.zhuorui.securities.base2app.BaseApplication.Companion.context
import java.io.File

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-27 13:52
 *    desc   : 相册获取图片工具类
 */
object GetPhotoFromAlbumUtil {

    /**
     * 根据Uri获取图片的绝对路径
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val sdkVersion = Build.VERSION.SDK_INT
        return if (sdkVersion >= 19) { // api >= 19
            getRealPathFromUriAboveApi19(context, uri)
        } else { // api < 19
            getRealPathFromUriBelowAPI19(context, uri)
        }
    }


    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */

    private fun getRealPathFromUriBelowAPI19(context: Context, uri: Uri): String? {
        return getDataColumn(context, uri, null, null)
    }


    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */

    @SuppressLint("NewApi")
    private fun getRealPathFromUriAboveApi19(context: Context, uri: Uri): String? {
        var filePath: String? = null
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            val documentId = DocumentsContract.getDocumentId(uri)
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                val id = documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=?"
                val selectionArgs = arrayOf(id)
                filePath =
                    getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs)
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(documentId)
                )
                filePath = getDataColumn(context, contentUri, null, null)
            }
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null)
        } else if ("file" == uri.scheme) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.path
        }
        return filePath
    }


    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     */

    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                path = cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            cursor?.close()
        } finally {
            cursor?.close()
        }
        return path
    }


    /**
     * @param uri the Uri to check
     *
     * @return Whether the Uri authority is MediaProvider
     */

    private fun isMediaDocument(uri: Uri): Boolean {

        return "com.android.providers.media.documents" == uri.authority

    }


    /**
     * @param uri the Uri to check
     *
     * @return Whether the Uri authority is DownloadsProvider
     */

    private fun isDownloadsDocument(uri: Uri): Boolean {

        return "com.android.providers.downloads.documents" == uri.authority

    }

    fun getOutputMediaFileUri(cameraSavePath: String?,context: Context): Uri {
        var mediaFile :File = File(cameraSavePath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            var contentUri:Uri  = FileProvider.getUriForFile(context, "com.zhuorui.securities.openaccount.fileprovider",//与清单文件中android:authorities的值保持一致
                    mediaFile)//FileProvider方式或者ContentProvider。也可使用VmPolicy方式
            return contentUri
        } else {
            return Uri.fromFile(mediaFile)//或者 Uri.isPaise("file://"+file.toString()

        }
    }

    fun goCamera(fragment: Fragment, requestCode: Int, uri: Uri?) {
        //打开照相机
        val openCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        //Android7.0添加临时权限标记，此步千万别忘了
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        fragment.startActivityForResult(openCameraIntent, requestCode)
    }

    fun goAlbum(fragment: Fragment, requestCode: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        fragment.startActivityForResult(intent,requestCode)
    }

}