import axios from 'axios'

// ============================================================
// 服务器信息
// ============================================================

/** 服务器系统信息 */
export interface ServerSystemInfo {
  memory: number
  java: string
  os: string
  gpus: string[]
  arch: string
  cpuThread: number
  cpuName: string
  cpuCore: number
}

/** `/api/server/info` 响应结构 */
export interface ServerInfoResponse {
  motd: string
  maxPlayerCount: number
  system: ServerSystemInfo
  code: number
  port: number
  ingameTime: number
  whitelist: boolean
  error: string
  uptime: number
}

/**
 * 获取服务器信息
 * GET /api/server/info
 */
export const fetchServerInfo =
  async (): Promise<ServerInfoResponse> => {
    const response =
      await axios.get<ServerInfoResponse>(
        '/api/server/info'
      )

    return response.data
  }

// ============================================================
// 服务器实时监控
// ============================================================

/** `/api/server/monitor` 响应结构 */
export interface ServerMonitorResponse {
  memory: number
  code: number
  tps: number
  cpu: number
  error: string
}

/**
 * 获取服务器实时监控数据
 * GET /api/server/monitor
 */
export const fetchServerMonitor =
  async (): Promise<ServerMonitorResponse> => {
    const response =
      await axios.get<ServerMonitorResponse>(
        '/api/server/monitor'
      )

    return response.data
  }
