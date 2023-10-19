import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService<T> {
  private baseUrl = 'http://localhost:5007/api';

  constructor(private http: HttpClient) {}

  getAll(endpoint: string): Observable<T[]> {
    return this.http.get<T[]>(`${this.baseUrl}/${endpoint}`);
  }
  getById(endpoint: string, id: any): Observable<T[]> {
    const url = `${this.baseUrl}/${endpoint}/${id}`;
    return this.http.get<T[]>(url);
  }
  getByThreeIds(
    endpoint: string,
    id1: any,
    id2: any,
    id3: any
  ): Observable<T[]> {
    const url = `${this.baseUrl}/${endpoint}/${id1}/${id2}/${id3}`;
    return this.http.get<T[]>(url);
  }
  getByCriteria(
    endpoint: string,
    date: string,
    origin: string,
    destination: string
  ): Observable<T[]> {
    const url = `${this.baseUrl}/${endpoint}/GetExecutionsByCriteria?origin=${origin}&destination=${destination}&date=${date}`;
    return this.http.get<T[]>(url);
  }
}
