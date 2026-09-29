
package com.example.swantracemc.utils

import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.buffer

/**
 * 带下载进度回调的 ResponseBody。
 *
 * 主要用于：
 *
 * OkHttp
 *   ↓
 * ResponseBody
 *   ↓
 * ProgressResponseBody
 *   ↓
 * DownloadPage
 *
 * 下载过程中会持续回调：
 *
 * onProgress(progress)
 *
 * 其中 progress 范围：
 *
 * 0.0f  -> 0%
 * 0.5f  -> 50%
 * 1.0f  -> 100%
 *
 * 如果服务器没有返回 Content-Length，
 * 则无法计算准确百分比。
 */
class ProgressResponseBody(
    private val responseBody: ResponseBody,
    private val onProgress: (Float) -> Unit
) : ResponseBody() {

    /**
     * 返回原始响应的 MIME 类型。
     */
    override fun contentType() =
        responseBody.contentType()

    /**
     * 返回原始响应的文件大小。
     *
     * 如果服务器没有提供 Content-Length，
     * 这里通常会返回 -1。
     */
    override fun contentLength(): Long =
        responseBody.contentLength()

    /**
     * 创建带进度监听的 BufferedSource。
     */
    override fun source(): BufferedSource {

        val source = responseBody.source()

        return object : ForwardingSource(source) {

            /**
             * 已读取的字节数。
             */
            private var bytesRead = 0L

            /**
             * 文件总大小。
             */
            private val totalBytes =
                responseBody.contentLength()

            override fun read(
                sink: Buffer,
                byteCount: Long
            ): Long {

                /*
                 * 从服务器读取数据。
                 */
                val read = super.read(
                    sink,
                    byteCount
                )

                /*
                 * read == -1 表示下载结束。
                 */
                if (read != -1L) {

                    bytesRead += read

                    /*
                     * Content-Length 有效时，
                     * 计算当前下载百分比。
                     */
                    if (totalBytes > 0L) {

                        val progress =
                            bytesRead.toFloat() /
                                    totalBytes.toFloat()

                        /*
                         * 防止由于某些特殊响应导致
                         * progress 超过 1.0。
                         */
                        onProgress(
                            progress.coerceIn(
                                0f,
                                1f
                            )
                        )
                    }
                }

                return read
            }
        }.buffer()
    }
}

