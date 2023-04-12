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

  getFillingPriceGraph(id: number, maxResults: number): Observable<Graph[]>{
    return this.http.get<Graph[]>(API_URL + id + "/graphs/filling/price/" + maxResults, httpOptions)
  }
}