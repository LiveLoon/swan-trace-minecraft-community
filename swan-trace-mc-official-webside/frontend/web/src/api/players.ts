import axios from 'axios'

export interface Player {
  name: string
  uuid: string
  isOnline: boolean
  isBanned: boolean
  gamemode: 'adventure' | 'creative' | 'survival' | 'spectator'
  banReason?: string
  ping?: number
}

export interface PlayersResponse {
  players: Player[]
}

/**
 * 获取在线玩家列表
 */
export const fetchPlayers = async (): Promise<PlayersResponse> => {
  const response = await axios.get<PlayersResponse>('/api/player/list')
  return response.data
}
