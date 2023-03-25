import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/vehicle/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  constructor(private http: HttpClient) {}

  getVehicle(vehicleId: number): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getVehiclesForUser(userId: number): Observable<any> {
    return this.http.get(API_URL + 'id', { responseType: 'text' });
  }
}