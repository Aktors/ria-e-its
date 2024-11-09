import {Component, OnInit} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {Router} from '@angular/router';

@Component({
  selector: 'app-system-booting',
  standalone: true,
  imports: [
    MatIcon
  ],
  templateUrl: './system-booting.page.html',
  styleUrl: './system-booting.page.css'
})
export class SystemBootingPage implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {
    setInterval(() => {
      this.router.navigate(['/']).catch(() => {
      });
    }, 3000);
  }
}
