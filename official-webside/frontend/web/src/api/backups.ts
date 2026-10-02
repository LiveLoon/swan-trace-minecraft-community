import axios from 'axios'

export interface FileItem {
  name: string
  type: string
  mtime: string
  size: number
}

/**
 * 获取备份文件列表
 */

export const fetchBackupFiles = async (): Promise<FileItem[]> => {
  const response = await axios.get<FileItem[]>('/api/backup/list')
  return response.data
}

/**
 * 下载备份文件（支持进度回调）
 * @param fileName 文件名
 * @param onProgress 进度回调，参数为 0~100 的百分比
 * @returns Promise<Blob> 下载的文件 Blob
 */

export const downloadBackupFile = (
  fileName: string,
  onProgress?: (percent: number) => void
): Promise<Blob> => {
  return axios
    .get(`/api/backup/download/${encodeURIComponent(fileName)}`, {
      responseType: 'blob',
      onDownloadProgress: (progressEvent) => {
        console.log('downloadBackupFile::', progressEvent)
        if (progressEvent.total && onProgress) {
          const percent = Math.round((progressEvent.loaded * 100) / progressEvent.total)
          onProgress(percent)
        }
      }
    })
    .then((response) => response.data)
}
