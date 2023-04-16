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

  getLineGraph(type: string, id: number, maxResults: number): Observable<Graph>{
    if (type=="price"){
      return this.getFillingPriceGraph(id, maxResults);
    }
    else if (type=="trip"){
      return this.getFillingDistanceGraph(id, maxResults);
    }
    else if (type=="unitprice"){
      return this.getUnitPriceGraph(id, maxResults);
    }
    else if (type=="city"){
      return this.getCityConsumptionGraph(id);
    }
    else{
      return this.getFillingConsumptionGraph(id, maxResults);
    }
  }
}