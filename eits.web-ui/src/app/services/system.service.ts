import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SystemService {
  private readonly baseApiUrl!: string;

  constructor(private http: HttpClient) {
    this.baseApiUrl = environment.baseApiUrl;
  }

  getHeartbeat(): Observable<string> {
    let url_ = this.baseApiUrl + "/api/system/heartbeat";
    return this.http.get(url_, { responseType: 'text' });
  }
}
