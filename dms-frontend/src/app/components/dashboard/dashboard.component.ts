import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartType } from 'chart.js';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardStats, FileExtensionStat, RecentDocument } from '../../models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats | null = null;
  loading = true;
  error: string | null = null;

  // Pie chart for file extensions
  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'right',
        labels: {
          color: '#666',
          font: {
            size: 12
          }
        }
      }
    }
  };

  public pieChartType: ChartType = 'pie';

  public pieChartData: ChartConfiguration['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        backgroundColor: [
          '#4CAF50',
          '#2196F3',
          '#FF9800',
          '#9C27B0',
          '#F44336',
          '#00BCD4',
          '#FFC107',
          '#E91E63'
        ]
      }
    ]
  };

  public pieChartLegend = true;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadDashboardStats();
  }

  loadDashboardStats(): void {
    this.loading = true;
    this.error = null;

    // For demo purposes, using mock data since backend might not be running
    // In production, uncomment the service call
    /*
    this.dashboardService.getDashboardStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.updateChartData(data.fileExtensionStats);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load dashboard data';
        this.loading = false;
      }
    });
    */

    // Mock data for demonstration
    setTimeout(() => {
      this.stats = {
        totalFiles: 156,
        totalApplications: 12,
        storageUsed: 5368709120, // 5GB
        storageLimit: 10737418240, // 10GB
        storagePercentage: 50,
        fileExtensionStats: [
          { extension: 'PDF', count: 45, percentage: 28.8 },
          { extension: 'DOCX', count: 38, percentage: 24.4 },
          { extension: 'XLSX', count: 32, percentage: 20.5 },
          { extension: 'JPG', count: 20, percentage: 12.8 },
          { extension: 'PNG', count: 12, percentage: 7.7 },
          { extension: 'Others', count: 9, percentage: 5.8 }
        ],
        recentDocuments: [
          { id: '1', fileName: 'Q4_Financial_Report.pdf', fileExtension: 'PDF', fileSize: 2048576, uploadedBy: 'john.doe', uploadedAt: new Date().toISOString() },
          { id: '2', fileName: 'Project_Proposal.docx', fileExtension: 'DOCX', fileSize: 1536000, uploadedBy: 'jane.smith', uploadedAt: new Date(Date.now() - 3600000).toISOString() },
          { id: '3', fileName: 'Budget_2024.xlsx', fileExtension: 'XLSX', fileSize: 3072000, uploadedBy: 'mike.johnson', uploadedAt: new Date(Date.now() - 7200000).toISOString() },
          { id: '4', fileName: 'Team_Photo.jpg', fileExtension: 'JPG', fileSize: 5120000, uploadedBy: 'sarah.wilson', uploadedAt: new Date(Date.now() - 86400000).toISOString() },
          { id: '5', fileName: 'Meeting_Notes.pdf', fileExtension: 'PDF', fileSize: 1024000, uploadedBy: 'john.doe', uploadedAt: new Date(Date.now() - 172800000).toISOString() }
        ]
      };
      this.updateChartData(this.stats.fileExtensionStats);
      this.loading = false;
    }, 500);
  }

  updateChartData(fileExtensionStats: FileExtensionStat[]): void {
    this.pieChartData.labels = fileExtensionStats.map(stat => stat.extension);
    this.pieChartData.datasets[0].data = fileExtensionStats.map(stat => stat.count);
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
      year: 'numeric', 
      month: 'short', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getFileIcon(extension: string): string {
    const icons: { [key: string]: string } = {
      'PDF': '📄',
      'DOCX': '📝',
      'DOC': '📝',
      'XLSX': '📊',
      'XLS': '📊',
      'JPG': '🖼️',
      'JPEG': '🖼️',
      'PNG': '🖼️',
      'GIF': '🖼️',
      'TXT': '📃'
    };
    return icons[extension.toUpperCase()] || '📁';
  }
}
