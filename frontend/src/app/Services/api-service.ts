import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ApiService<T> {
    private baseUrl = 'http://localhost:5007/api';

    constructor(private http: HttpClient) { }

    getAll(endpoint: string): Observable<T[]> {
        return this.http.get<T[]>(`${this.baseUrl}/${endpoint}`);
    }
    getById(endpoint: string, id: any): Observable<T[]> {
        const url = `${this.baseUrl}/${endpoint}/${id}`;
        return this.http.get<T[]>(url);
    }
}