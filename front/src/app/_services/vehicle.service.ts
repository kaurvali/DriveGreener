import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { Fillup } from '../_models/fillup.model';
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

  /*getVehicle(vehicleId: number): Observable<any> {
    return this.http.get(API_URL + vehicleId, { responseType: 'text' });
  }*/

  addVehicle(form: FormGroup) {
    return this.http.post(API_URL + 'add', JSON.stringify(form.value), httpOptions);
  }

  getFillupsForVehicle(vehicleId: number): Observable<Fillup[]> {
    return this.http.get<Fillup[]>(API_URL + vehicleId + "/fillups")
  }

}