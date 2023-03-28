import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle } from '../_models/vehicle.model';

const API_URL = 'http://localhost:8080/api/user/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getVehiclesForUser(userId: number): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(API_URL + userId + "/vehicles")
  }

}