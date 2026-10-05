/**
 * 把 Rust 发来的 payload 解析成统一结构。
 * 后端字段是 camelCase：logType / fileCount / file
 */
export function parseLogPayload(payload) {
  if (typeof payload === 'string') {
    return { message: payload, type: 'normal', file: '' }
  }

  if (payload && typeof payload === 'object') {
    return {
      message: String(
        payload.message ??
        payload.text ??
        payload.line ??
        JSON.stringify(payload)
      ),
      type: payload.logType || payload.type || 'normal',
      file: payload.file || ''
    }
  }

  return { message: String(payload ?? ''), type: 'normal', file: '' }
}

/**
 * 把常见 rsync 错误翻译成人话，识别不到返回 null。
 */
export function getFriendlyRsyncMessage(message) {
  const text = String(message).toLowerCase()

  if (
    text.includes('did not see server greeting') ||
    text.includes('error starting client-server protocol') ||
    text.includes('protocol (code 5)')
  ) {
    return '无法与 Rsync 服务端完成握手。请确认服务端 Rsync daemon 已启动、服务器地址和端口正确，并检查防火墙设置。'
  }
  if (text.includes('connection refused') || text.includes('actively refused')) {
    return '服务器拒绝了连接。请确认 Rsync 服务已启动，并检查服务器端口及防火墙设置。'
  }
  if (text.includes('connection timed out') || text.includes('timed out')) {
    return '连接服务器超时。请检查服务器地址、网络连接及防火墙设置。'
  }
  if (
    text.includes('@error') ||
    text.includes('unknown module') ||
    text.includes('module is read only')
  ) {
    return '服务端拒绝了本次请求。请检查同步模块名称及服务端模块配置。'
  }
  if (text.includes('no such file or directory') && text.includes('rsync')) {
    return '找不到内置 Rsync 程序。请检查应用资源文件是否完整。'
  }
  return null
}