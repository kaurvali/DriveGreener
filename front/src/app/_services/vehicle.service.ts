import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { VehicleStatistics } from '../_dto/vehicle.statistics.dto';
import { Vehicle } from '../_models/vehicle.model';
import { FillupStatistics } from '../_dto/fillup.statistics.dto';

const API_URL = 'http://localhost:8080/api/vehicle/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  constructor(private http: HttpClient) {}

  getVehicle(vehicleId: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(API_URL + vehicleId);
  }

  addVehicle(form: FormGroup) {
    return this.http.post(API_URL + 'add', JSON.stringify(form.value), httpOptions);
  }

  getFillupsForVehicle(vehicleId: number): Observable<FillupStatistics[]> {
    return this.http.get<FillupStatistics[]>(API_URL + vehicleId + "/fillups")
  }

  getStatisticsForVehicle(vehicleId: number): Observable<VehicleStatistics> {
    return this.http.get(API_URL + vehicleId + "/statistics")
  }
  getBasicStatisticsForVehicle(vehicleId: number): Observable<VehicleStatistics> {
    return this.http.get(API_URL + vehicleId + "/basic-stats");
  }
}