import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Graph } from '../_dto/graph.dto';

const API_URL = 'http://localhost:8080/api/fillup/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root',
})
export class FillupService {
  constructor(private http: HttpClient) {}

  addFillup(form: FormGroup): Observable<any> {
    console.log("Adding a fillup!")
    return this.http.post(API_URL + 'add', JSON.stringify(form.value), httpOptions);
  }

  getFillingDistanceGraph(id: number, maxResults: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/distance/" + maxResults, httpOptions)
  }

  getFillingConsumptionGraph(id: number, maxResults: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/consumption/" + maxResults, httpOptions)
  }

  getFillingPriceGraph(id: number, maxResults: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/price/" + maxResults, httpOptions)
  }

  getUnitPriceGraph(id: number, maxResults: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/unitprice/" + maxResults, httpOptions)
  }

  getCityConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/citydriving/", httpOptions)
  }

  getDrivingStyleConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/drivingstyle/", httpOptions)
  }

  getTireTypeConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/tiretype/", httpOptions)
  }

  getWeightTypeConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/loadtype/", httpOptions)
  }

  getFuelTypeConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/fueltype/", httpOptions)
  }

  getMonthlyConsumptionGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/monthlyconsumption/", httpOptions)
  }

  getMonthlyCostGraph(id: number): Observable<Graph>{
    return this.http.get<Graph>(API_URL + id + "/graphs/filling/monthlycost/", httpOptions)
  }

  getLineGraph(type: string, id: number, maxResults: number): Observable<Graph>{
    if (type=="price")
      return this.getFillingPriceGraph(id, maxResults);
    else if (type=="trip")
      return this.getFillingDistanceGraph(id, maxResults);
    else if (type=="unitprice")
      return this.getUnitPriceGraph(id, maxResults);
    else if (type=="city")
      return this.getCityConsumptionGraph(id);
    else if (type=="drivingstyle")
      return this.getDrivingStyleConsumptionGraph(id);
    else if (type=="tire")
      return this.getTireTypeConsumptionGraph(id);
    else if (type=="weight")
      return this.getWeightTypeConsumptionGraph(id);
    else if (type=="fuel")
      return this.getFuelTypeConsumptionGraph(id);
    else if (type=="monthlycost")
      return this.getMonthlyCostGraph(id);
    else if (type=="monthlyconsumption")
      return this.getMonthlyConsumptionGraph(id);
    else
      return this.getFillingConsumptionGraph(id, maxResults);
  }
}