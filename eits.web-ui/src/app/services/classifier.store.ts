import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {MeasuresService} from './api/measures.service';

@Injectable({
  providedIn: 'root'
})
export class ClassifierStore {
  private versions = new BehaviorSubject<string[]>([]);
  versions$: Observable<string[]> = this.versions.asObservable();

  constructor(private systemService: MeasuresService) {
    this.loadVersions();
  }

  private loadVersions(): void {
    this.systemService.getVersions()
      .pipe(
        tap((data) => this.versions.next(data))
      )
      .subscribe();
  }
}
