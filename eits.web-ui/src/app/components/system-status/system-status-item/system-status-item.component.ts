import {Component, Input} from '@angular/core';
import {NgClass} from '@angular/common';
import {SystemStatus} from '../../../model/system.type';

@Component({
  selector: 'app-system-status-item',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './system-status-item.component.html',
  styleUrl: './system-status-item.component.css'
})
export class SystemStatusItemComponent {
  @Input() label: string = '';
  @Input() status: SystemStatus = SystemStatus.OK;
}
