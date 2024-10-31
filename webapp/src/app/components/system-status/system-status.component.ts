import {Component, OnInit} from '@angular/core';
import {SystemService} from '../../services/system.service';
import {HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-system-status',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './system-status.component.html',
  styleUrl: './system-status.component.css'
})
export class SystemStatusComponent implements OnInit {
  statusMessage: string | null = null;

  constructor(private systemService: SystemService) {}

  ngOnInit(): void {
    console.log(this.systemService);
    this.systemService.getHeartbeat().subscribe({
      next: (response) => this.statusMessage = response,
      error: (error) => this.statusMessage = 'Error retrieving status'
    });
  }
}
