package com.zhuorui.securities.base2app.util

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.io.IOException
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/29 18:29
 *    desc   :
 */
object SignUtil {

    fun encryptBASE64(keyBytes: ByteArray): String {
        return BASE64Encoder().encode(keyBytes).replace("\n","")
    }

    fun decryptBASE64(privateKey: String): ByteArray? {
        var output: ByteArray? = null
        try {
            output = BASE64Decoder().decodeBuffer(privateKey)
            return output
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return output
    }

    @Throws(Exception::class)
    fun createSHA1Sign(content: String, privateKeyStr: String): String {
        val privateKey = getPrivateKeyByStr(privateKeyStr)
        val contentBytes = content.toByteArray(charset("utf-8"))
        val signature = Signature.getInstance("SHA1WithRSA")
        signature.initSign(privateKey)
        signature.update(contentBytes)
        val signs = signature.sign()
        return encryptBASE64(signs)
    }

    @Throws(Exception::class)
    fun getPrivateKeyByStr(privateKey: String): PrivateKey {
        val keyBytes = decryptBASE64(privateKey)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }
}