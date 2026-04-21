export interface DashboardStats {
  totalFiles: number;
  totalApplications: number;
  storageUsed: number;
  storageLimit: number;
  storagePercentage: number;
  fileExtensionStats: FileExtensionStat[];
  recentDocuments: RecentDocument[];
}

export interface FileExtensionStat {
  extension: string;
  count: number;
  percentage: number;
}

export interface RecentDocument {
  id: string;
  fileName: string;
  fileExtension: string;
  fileSize: number;
  uploadedBy: string;
  uploadedAt: string;
}
