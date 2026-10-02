import axios from 'axios'

// ===== 类型定义 =====

/** 统计项元数据 */
export interface StatItem {
  key: string
  display: string
  category: string
  format: string
}

/** `/api/stats` 响应结构 */
export interface StatsResponse {
  stats: StatItem[]
}

/** 排行榜条目 */
export interface LeaderboardEntry {
  rank: number
  uuid: string
  name: string
  value: number
  value_human?: string
}

/** `/api/top/{stat}` 响应结构 */
export interface LeaderboardResponse {
  stat: string
  display: string
  format: string
  entries: LeaderboardEntry[]
}

// ===== API 函数 =====

/**
 * 获取所有统计项元数据
 * GET /api/stats
 */
export const fetchStats = async (): Promise<StatsResponse> => {
  const response = await axios.get<StatsResponse>('/api/stats')
  return response.data
}

/**
 * 获取指定统计项的排行榜
 * @param statKey 统计项 key (例如 'blocks_broken', 'playtime')
 * GET /api/top/{statKey}
 */
export const fetchLeaderboard = async (statKey: string): Promise<LeaderboardResponse> => {
  const response = await axios.get<LeaderboardResponse>(`/api/top/${statKey}`)
  return response.data
}