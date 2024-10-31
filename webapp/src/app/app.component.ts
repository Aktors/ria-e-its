import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {SystemStatusComponent} from './components/system-status/system-status.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SystemStatusComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'webapp';
}
