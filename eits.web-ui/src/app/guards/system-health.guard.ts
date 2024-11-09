import {CanActivate, Router} from '@angular/router';
import { Injectable} from '@angular/core';
import {SystemService} from '../services/api/system.service';
import {catchError, Observable, of} from 'rxjs';
import {map} from 'rxjs/operators';
import {AppPaths} from '../app.routes';

@Injectable({
  providedIn: 'root'
})
export class SystemHealthGuard implements CanActivate  {
  constructor(private systemService: SystemService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.systemService.getHeartbeat().pipe(
      map(() => {
          return true;
      }),
      catchError(() => {
        this.router.navigate([AppPaths.system.booting]);
        return of(false); // Emit false to prevent navigation
      })
    );
  }
}
