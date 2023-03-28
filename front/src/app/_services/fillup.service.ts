import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

const API_URL = 'http://localhost:8080/api/fillup/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root',
})
export class FillupService {
  constructor(private http: HttpClient) {}

  addFillup(form: FormGroup) {
    console.log("Adding a fillup!")
    this.http.post(API_URL + 'add', JSON.stringify(form.value), httpOptions).subscribe(
      response => {
        console.log(response)
      }
    );
  }
}