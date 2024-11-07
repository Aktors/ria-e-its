import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {MeasureDto } from '../../model/measure.type';

@Injectable({
  providedIn: 'root'
})
export class MeasuresService {
  private readonly baseApiUrl!: string;

  constructor(private http: HttpClient) {
    this.baseApiUrl = environment.baseApiUrl;
  }

  getByVersion(version: string): Observable<MeasureDto[]> {
    let url_ = this.baseApiUrl + "/api/measures/{version}";
    url_ = url_.replace("{version}", encodeURIComponent("" + version));
    url_ = url_.replace(/[?&]$/, "");

    return this.http.get<MeasureDto[]>(url_);
  }

  getVersions(): Observable<string[]> {
    let url_ = this.baseApiUrl + "/api/measures/versions";
    return this.http.get<string[]>(url_);
  }
}
