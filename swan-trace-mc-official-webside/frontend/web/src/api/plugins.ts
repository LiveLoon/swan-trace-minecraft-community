
import axios from 'axios'

// ===== 类型定义 =====

/** 服务器插件信息 */
export interface PluginInfo {
  loaded: boolean
  fileName: string
  website: string
  size: number
  name: string
  description: string
  version: string
  enabled: boolean
  authors: string[]
}

/** `/api/server/plugins` 响应结构 */
export interface PluginResponse {
  code: number
  plugins: PluginInfo[]
  error: string
}

// ===== API 函数 =====

/**
 * 获取服务器插件列表
 * GET /api/server/plugins
 */
export const fetchServerPlugins = async (): Promise<PluginResponse> => {
  const response = await axios.get<PluginResponse>(
    '/api/server/plugins'
  )

  return response.data
}
