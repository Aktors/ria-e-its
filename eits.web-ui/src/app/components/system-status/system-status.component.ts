import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SystemService} from '../../services/api/system.service';
import {SystemStatusItemComponent} from './system-status-item/system-status-item.component';
import {SystemStatus} from '../../model/system.type';

@Component({
  selector: 'app-system-status',
  standalone: true,
  imports: [CommonModule, SystemStatusItemComponent],
  templateUrl: './system-status.component.html',
  styleUrl: './system-status.component.css'
})
export class SystemStatusComponent implements OnInit {
  statusMessage: string | null = null;
  statuses = [
    { label: 'E-ITS API', status: SystemStatus.OK },
    { label: 'Catalog cache', status: SystemStatus.Nok }
  ];

  constructor(private systemService: SystemService) {}

  ngOnInit(): void {
    console.log(this.systemService);
    this.systemService.getHeartbeat().subscribe({
      next: (response) => this.statusMessage = response,
      error: (_) => this.statusMessage = 'Error retrieving status'
    });
  }
}
